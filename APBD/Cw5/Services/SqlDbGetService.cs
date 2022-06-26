using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using Cw5.DTOs.Responses;
using Cw5.Models;

namespace Cw5.Services
{
    internal class SqlDbGetService
    {
        private const string SelectQuery =
            "SELECT IndexNumber, FirstName, LastName, BirthDate, IdEnrollment FROM Student";

        private const string StartedStudiesQuery =
            "SELECT IndexNumber, FirstName, LastName, BirthDate, Name, Semester, StartDate FROM Student " +
            "INNER JOIN Enrollment on Student.IdEnrollment = Enrollment.IdEnrollment INNER JOIN " +
            "Studies on Studies.IdStudy = Enrollment.IdStudy WHERE IndexNumber = @IndexNumber";

        private readonly SqlCommand _sqlCommand;

        public SqlDbGetService(SqlCommand sqlCommand)
        {
            _sqlCommand = sqlCommand;
        }

        public IEnumerable<Student> GetAllStudents()
        {
            _sqlCommand.CommandText = SelectQuery;
            var sqlDataReader = _sqlCommand.ExecuteReader();
            var students = new List<Student>();
            while (sqlDataReader.Read()) students.Add(SqlDataReaderToStudent(sqlDataReader));
            return students;
        }

        public GetSingleStudentResponse GetStudent(string indexNumber)
        {
            _sqlCommand.CommandText = StartedStudiesQuery;
            _sqlCommand.Parameters.AddWithValue("indexNumber", indexNumber);
            var sqlDataReader = _sqlCommand.ExecuteReader();
            if (!sqlDataReader.Read()) return null;
            return new GetSingleStudentResponse
            {
                IndexNumber = sqlDataReader["IndexNumber"].ToString(),
                FirstName = sqlDataReader["FirstName"].ToString(),
                LastName = sqlDataReader["LastName"].ToString(),
                BirthDate = DateTime.Parse(sqlDataReader["BirthDate"].ToString()!),
                Name = sqlDataReader["Name"].ToString(),
                Semester = (int) sqlDataReader["Semester"],
                StartDate = DateTime.Parse(sqlDataReader["StartDate"].ToString()!)
            };
        }

        private static Student SqlDataReaderToStudent(IDataRecord sqlDataReader)
        {
            return new Student
            {
                IndexNumber = sqlDataReader["IndexNumber"].ToString(),
                FirstName = sqlDataReader["FirstName"].ToString(),
                LastName = sqlDataReader["LastName"].ToString(),
                BirthDate = DateTime.Parse(sqlDataReader["BirthDate"].ToString()!),
                IdEnrollment = (int) sqlDataReader["IdEnrollment"]
            };
        }
    }
}