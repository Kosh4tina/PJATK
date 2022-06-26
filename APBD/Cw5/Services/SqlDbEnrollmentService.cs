using System;
using System.Data.SqlClient;
using Cw5.DTOs.Requests;
using Cw5.DTOs.ResultContainers;
using Cw5.Models;

namespace Cw5.Services
{
    internal class SqlDbEnrollmentService
    {
        private const string StudiesFilterNameQuery =
            "SELECT IdStudy, Name FROM Studies WHERE Name = @StudiesName";

        private const string StudentFilterIndexNumberQuery =
            "SELECT IndexNumber FROM Student WHERE IndexNumber = @IndexNumber";

        private const string FirstSemesterEnrollmentForStudiesQuery =
            "SELECT IdEnrollment, Semester, Enrollment.IdStudy, StartDate FROM Enrollment " +
            "INNER JOIN Studies on Studies.IdStudy = Enrollment.IdStudy " +
            "WHERE Studies.Name = @StudiesName AND Semester = 1";

        private const string SelectLastIdEnrollment =
            "SELECT TOP 1 IdEnrollment FROM Enrollment ORDER BY IdEnrollment DESC;";

        private const string InsertFirstSemesterEnrollmentForStudiesQuery =
            "INSERT INTO Enrollment (IdEnrollment, Semester, IdStudy, StartDate) " +
            "SELECT @IdEnrollment, 1, IdStudy, @EnrollmentDate FROM Studies WHERE Studies.Name = @StudiesName";

        private const string InsertStudentQuery =
            "INSERT INTO Student(IndexNumber, FirstName, LastName, BirthDate, IdEnrollment) " +
            "VALUES (@IndexNumber, @FirstName, @LastName, @BirthDate, @IdEnrollment)";

        private readonly EnrollStudentRequest _enrollRequest;
        private readonly SqlCommand _sqlCommand;
        private string _enrollmentDate;

        public SqlDbEnrollmentService(SqlCommand sqlCommand, EnrollStudentRequest enrollRequest)
        {
            _sqlCommand = sqlCommand;
            _enrollRequest = enrollRequest;
        }

        public EnrollmentResult Enroll()
        {
            _enrollmentDate = DateTime.Now.ToLongDateString();
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
                return new Enrollment
                {
                    IdEnrollment = (int) sqlDataReader["IdEnrollment"],
                    Semester = (int) sqlDataReader["Semester"],
                    IdStudy = (int) sqlDataReader["IdStudy"],
                    StudiesName = _enrollRequest.Studies,
                    StartDate = DateTime.Parse(sqlDataReader["StartDate"].ToString()!)
                };
            return PrepareNewEnrollment(studies);
        }

        private Enrollment PrepareNewEnrollment(Studies studies)
        {
            _sqlCommand.CommandText = InsertFirstSemesterEnrollmentForStudiesQuery;
            var nextIdEnrollment = NextEnrollmentId();
            _sqlCommand.Parameters.AddWithValue("IdEnrollment", nextIdEnrollment);
            _sqlCommand.Parameters.AddWithValue("EnrollmentDate", _enrollmentDate);
            if (_sqlCommand.ExecuteNonQuery() == 0)
                throw new SqlInsertException("Blad podczas tworzenia nowego wpisu w tablicy \"Enrollment\"!");
            return new Enrollment
            {
                IdEnrollment = nextIdEnrollment,
                Semester = 1,
                IdStudy = studies.IdStudy,
                StartDate = DateTime.Parse(_enrollmentDate)
            };
        }

        private int NextEnrollmentId()
        {
            _sqlCommand.CommandText = SelectLastIdEnrollment;
            using var sqlDataReader = _sqlCommand.ExecuteReader();
            return sqlDataReader.Read() ? (int) sqlDataReader["IdEnrollment"] + 1 : 1;
        }

        private void InsertNewStudentIntoDb(Student newStudent)
        {
            _sqlCommand.CommandText = InsertStudentQuery;
            _sqlCommand.Parameters.AddWithValue("FirstName", _enrollRequest.FirstName);
            _sqlCommand.Parameters.AddWithValue("LastName", _enrollRequest.LastName);
            _sqlCommand.Parameters.AddWithValue("BirthDate", _enrollRequest.BirthDate);
            if (!_sqlCommand.Parameters.Contains("IdEnrollment"))
                _sqlCommand.Parameters.AddWithValue("IdEnrollment", newStudent.IdEnrollment);
            if (_sqlCommand.ExecuteNonQuery() == 0)
                throw new SqlInsertException("Blad podczas tworzenia nowego wpisu w tablicy \"Students\"!");
        }
    }

    internal class SqlInsertException : Exception
    {
        public SqlInsertException() { }
        public SqlInsertException(string message) : base(message) { }
    }
}