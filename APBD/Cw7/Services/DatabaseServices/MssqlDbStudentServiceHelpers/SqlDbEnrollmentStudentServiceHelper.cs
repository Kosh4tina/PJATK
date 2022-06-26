using System;
using System.Data;
using System.Data.SqlClient;
using Cw7.DTOs.Requests;
using Cw7.DTOs.ResultContainers;
using Cw7.Exceptions;
using Cw7.Models;

namespace Cw7.Services.DatabaseServices.MssqlDbStudentServiceHelpers
{
    internal class SqlDbEnrollmentStudentServiceHelper
    {
        private const string StudiesFilterNameQuery =
            "SELECT IdStudy, Name FROM Studies WHERE Name = @StudiesName";

        private const string StudentFilterIndexNumberQuery =
            "SELECT IndexNumber FROM Student WHERE IndexNumber = @IndexNumber";

        private const string FirstSemesterEnrollmentForStudiesQuery =
            "SELECT IdEnrollment, Semester, Enrollment.IdStudy, StartDate FROM Enrollment " +
            "INNER JOIN Studies on Studies.IdStudy = Enrollment.IdStudy " +
            "WHERE Studies.Name = @StudiesName AND Semester = 1";

        private const string SelectLastAddedEnrollment =
            "SELECT IdEnrollment, Semester, Enrollment.IdStudy, StartDate FROM Enrollment " +
            "INNER JOIN Studies on Studies.IdStudy = Enrollment.IdStudy " +
            "WHERE Studies.Name = @StudiesName AND Semester = 1 AND StartDate = @EnrollmentDate";

        private const string InsertFirstSemesterEnrollmentForStudiesQuery =
            "INSERT INTO Enrollment (Semester, IdStudy, StartDate) SELECT 1, IdStudy, @EnrollmentDate FROM Studies " +
            "WHERE Studies.Name = @StudiesName";

        private const string InsertStudentQuery =
            "INSERT INTO Student(IndexNumber, FirstName, LastName, BirthDate, IdEnrollment, SaltPasswordHash) " +
            "VALUES (@IndexNumber, @FirstName, @LastName, @BirthDate, @IdEnrollment, @SaltPasswordHash)";

        private readonly Func<string, string> _encryptFunction;

        private readonly EnrollStudentRequest _enrollRequest;
        private readonly SqlCommand _sqlCommand;
        private string _enrollmentDate;

        public SqlDbEnrollmentStudentServiceHelper(SqlCommand sqlCommand, EnrollStudentRequest enrollRequest,
            Func<string, string> encryptFunction)
        {
            _sqlCommand = sqlCommand;
            _enrollRequest = enrollRequest;
            _encryptFunction = encryptFunction;
        }

        public EnrollmentResult Enroll()
        {
            _enrollmentDate = DateTime.Now.ToShortDateString();
            var studies = GetStudies();
            if (studies == null)
                return new EnrollmentResult {Error = $"Kierunek {_enrollRequest.Studies} nie istnieje!"};
            if (IndexNumberExistsInDatabase())
                return new EnrollmentResult {Error = $"Numer indeksu {_enrollRequest.IndexNumber} już istnieje!"};
            var enrollment = GetEnrollmentForNewStudent(studies);
            var newStudent = new Student
            {
                IndexNumber = _enrollRequest.IndexNumber,
                FirstName = _enrollRequest.FirstName,
                LastName = _enrollRequest.LastName,
                BirthDate = _enrollRequest.BirthDate,
                IdEnrollment = enrollment.IdEnrollment
            };
            InsertNewStudentIntoDb(newStudent);
            return new EnrollmentResult
            {
                Successful = true,
                Student = newStudent,
                Enrollment = enrollment
            };
        }

        private Studies GetStudies()
        {
            _sqlCommand.CommandText = StudiesFilterNameQuery;
            _sqlCommand.Parameters.AddWithValue("StudiesName", _enrollRequest.Studies);
            using var sqlDataReader = _sqlCommand.ExecuteReader();
            return sqlDataReader.Read()
                ? new Studies {IdStudy = (int) sqlDataReader["IdStudy"], Name = sqlDataReader["Name"].ToString()}
                : null;
        }

        private bool IndexNumberExistsInDatabase()
        {
            _sqlCommand.CommandText = StudentFilterIndexNumberQuery;
            _sqlCommand.Parameters.AddWithValue("IndexNumber", _enrollRequest.IndexNumber);
            using var sqlDataReader = _sqlCommand.ExecuteReader();
            return sqlDataReader.Read();
        }

        private Enrollment GetEnrollmentForNewStudent(Studies studies)
        {
            _sqlCommand.CommandText = FirstSemesterEnrollmentForStudiesQuery;
            using var sqlDataReader = _sqlCommand.ExecuteReader();
            if (sqlDataReader.Read())
                return SqlDataReaderToEnrollment(sqlDataReader, _enrollRequest.Studies);

            sqlDataReader.Close();
            return PrepareNewEnrollment(studies);
        }

        private Enrollment PrepareNewEnrollment(Studies studies)
        {
            _sqlCommand.CommandText = InsertFirstSemesterEnrollmentForStudiesQuery;
            _sqlCommand.Parameters.AddWithValue("EnrollmentDate", _enrollmentDate);
            if (_sqlCommand.ExecuteNonQuery() == 0)
                throw new SqlInsertException("Blad podczas tworzenia nowego wpisu w tablicy \"Enrollment\"!");
            return InsertedEnrollment(studies.Name);
        }

        private Enrollment InsertedEnrollment(string studiesName)
        {
            _sqlCommand.CommandText = SelectLastAddedEnrollment;
            using var sqlDataReader = _sqlCommand.ExecuteReader();
            sqlDataReader.Read();
            return SqlDataReaderToEnrollment(sqlDataReader, studiesName);
        }

        private static Enrollment SqlDataReaderToEnrollment(IDataRecord sqlDataReader, string studiesName)
        {
            return new Enrollment
            {
                IdEnrollment = (int) sqlDataReader["IdEnrollment"],
                Semester = (int) sqlDataReader["Semester"],
                IdStudy = (int) sqlDataReader["IdStudy"],
                StartDate = DateTime.Parse(sqlDataReader["StartDate"].ToString()!),
                StudiesName = studiesName
            };
        }

        private void InsertNewStudentIntoDb(Student newStudent)
        {
            _sqlCommand.CommandText = InsertStudentQuery;
            _sqlCommand.Parameters.AddWithValue("FirstName", _enrollRequest.FirstName);
            _sqlCommand.Parameters.AddWithValue("LastName", _enrollRequest.LastName);
            _sqlCommand.Parameters.AddWithValue("BirthDate", _enrollRequest.BirthDate);
            _sqlCommand.Parameters.AddWithValue("SaltPasswordHash", _encryptFunction(_enrollRequest.Password));
            if (!_sqlCommand.Parameters.Contains("IdEnrollment"))
                _sqlCommand.Parameters.AddWithValue("IdEnrollment", newStudent.IdEnrollment);
            if (_sqlCommand.ExecuteNonQuery() == 0)
                throw new SqlInsertException("Blad podczas tworzenia nowego wpisu w tablicy \"Students\"!");
        }
    }
}