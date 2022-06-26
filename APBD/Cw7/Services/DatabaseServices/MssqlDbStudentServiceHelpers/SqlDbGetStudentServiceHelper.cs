using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using Cw7.DTOs.Responses;
using Cw7.Models;

namespace Cw7.Services.DatabaseServices.MssqlDbStudentServiceHelpers
{
    internal class SqlDbGetStudentServiceHelper
    {
        private const string SelectQuery =
            "SELECT IndexNumber, FirstName, LastName, BirthDate, IdEnrollment FROM Student";

        private const string StartedStudiesQuery =
            "SELECT IndexNumber, FirstName, LastName, BirthDate, Name, Semester, StartDate " +
            "FROM Student INNER JOIN Enrollment on Student.IdEnrollment = Enrollment.IdEnrollment INNER JOIN " +
            "Studies on Studies.IdStudy = Enrollment.IdStudy WHERE IndexNumber = @IndexNumber";

        private const string StudentAuthenticationData =
            "SELECT IndexNumber, FirstName, LastName, SaltPasswordHash, RefreshToken, RoleName FROM Student " +
            "FULL JOIN RoleStudent on Student.IdStudent = RoleStudent.IdStudent " +
            "FULL JOIN Role on RoleStudent.IdRole = Role.IdRole " +
            "WHERE IndexNumber = @IndexNumber";

        private readonly SqlCommand _sqlCommand;

        public SqlDbGetStudentServiceHelper(SqlCommand sqlCommand)
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

        public SingleStudentAuthenticationData GetStudentsAuthenticationData(string indexNumber)
        {
            _sqlCommand.CommandText = StudentAuthenticationData;
            _sqlCommand.Parameters.AddWithValue("indexNumber", indexNumber);
            var sqlDataReader = _sqlCommand.ExecuteReader();
            if (!sqlDataReader.Read()) return null;
            var authenticationData = new SingleStudentAuthenticationData
            {
                IndexNumber = sqlDataReader["IndexNumber"].ToString(),
                FirstName = sqlDataReader["FirstName"].ToString(),
                LastName = sqlDataReader["LastName"].ToString(),
                SaltPasswordHash = sqlDataReader["SaltPasswordHash"].ToString(),
                RefreshToken = sqlDataReader["RefreshToken"]?.ToString()
            };
            var roles = new List<string>();
            do
            {
                var roleName = sqlDataReader["RoleName"]?.ToString();
                if (!string.IsNullOrWhiteSpace(roleName)) roles.Add(roleName);
            } while (sqlDataReader.Read());

            authenticationData.Roles = roles.ToArray();
            return authenticationData;
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