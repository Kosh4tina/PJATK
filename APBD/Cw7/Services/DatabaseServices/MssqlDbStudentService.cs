using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using Cw7.DTOs.Requests;
using Cw7.DTOs.Responses;
using Cw7.DTOs.ResultContainers;
using Cw7.Models;
using Cw7.Services.DatabaseServices.MssqlDbStudentServiceHelpers;
using Cw7.Services.EncryptionServices;

namespace Cw7.Services.DatabaseServices
{
    public class MssqlDbStudentService : IDbStudentService
    {
        private const string ConnectionString = "Data Source=db-mssql;Initial Catalog=s19575;Integrated Security=True";
        private readonly IEncryptionService _encryptionService;

        public MssqlDbStudentService(IEncryptionService encryptionService)
        {
            _encryptionService = encryptionService;
        }

        public IEnumerable<Student> GetAllStudents()
        {
            using var sqlConnection = new SqlConnection(ConnectionString);
            using var sqlCommand = new SqlCommand {Connection = sqlConnection};
            sqlConnection.Open();
            return new SqlDbGetStudentServiceHelper(sqlCommand).GetAllStudents();
        }

        public GetSingleStudentResponse GetStudent(string indexNumber)
        {
            using var sqlConnection = new SqlConnection(ConnectionString);
            using var sqlCommand = new SqlCommand {Connection = sqlConnection};
            sqlConnection.Open();
            return new SqlDbGetStudentServiceHelper(sqlCommand).GetStudent(indexNumber);
        }

        public EnrollmentResult EnrollStudent(EnrollStudentRequest newStudent)
        {
            using var sqlConnection = new SqlConnection(ConnectionString);
            using var sqlCommand = new SqlCommand {Connection = sqlConnection};
            sqlConnection.Open();
            sqlCommand.Transaction = sqlConnection.BeginTransaction();
            try
            {
                var enrollmentResult = new SqlDbEnrollmentStudentServiceHelper(sqlCommand, newStudent,
                    _encryptionService.Encrypt).Enroll();
                if (enrollmentResult.Successful) sqlCommand.Transaction.Commit();
                else sqlCommand.Transaction.Rollback();
                return enrollmentResult;
            }
            catch (Exception exception)
            {
                sqlCommand.Transaction.Rollback();
                return new EnrollmentResult {Error = $"Napotkano wyjatek podczas dodawania studenta - {exception}!"};
            }
        }

        public Enrollment PromoteStudents(PromoteStudentsRequest promoteStudentsRequest)
        {
            using var sqlConnection = new SqlConnection(ConnectionString);
            using var sqlCommand = new SqlCommand("PromoteStudents", sqlConnection)
                {CommandType = CommandType.StoredProcedure};
            sqlCommand.Parameters.AddWithValue("@Studies", promoteStudentsRequest.Studies);
            sqlCommand.Parameters.AddWithValue("@Semester", promoteStudentsRequest.Semester);
            sqlConnection.Open();
            var sqlDataReader = sqlCommand.ExecuteReader();
            if (!sqlDataReader.Read()) return null;
            return new Enrollment
            {
                IdEnrollment = (int) sqlDataReader["IdEnrollment"],
                Semester = (int) sqlDataReader["Semester"],
                IdStudy = (int) sqlDataReader["IdStudy"],
                StudiesName = sqlDataReader["Name"].ToString(),
                StartDate = DateTime.Parse(sqlDataReader["StartDate"].ToString()!)
            };
        }

        public SingleStudentAuthenticationData GetStudentsAuthenticationData(string indexNumber)
        {
            using var sqlConnection = new SqlConnection(ConnectionString);
            using var sqlCommand = new SqlCommand {Connection = sqlConnection};
            sqlConnection.Open();
            return new SqlDbGetStudentServiceHelper(sqlCommand).GetStudentsAuthenticationData(indexNumber);
        }

        public bool UpdateRefreshToken(string username, string refreshToken)
        {
            using var sqlConnection = new SqlConnection(ConnectionString);
            using var sqlCommand = new SqlCommand
            {
                Connection = sqlConnection,
                CommandText = "UPDATE Student SET RefreshToken = @RefreshToken WHERE IndexNumber = @Username"
            };
            sqlCommand.Parameters.AddWithValue("@RefreshToken", refreshToken);
            sqlCommand.Parameters.AddWithValue("@Username", username);
            sqlConnection.Open();
            return sqlCommand.ExecuteNonQuery() != 0;
        }
    }
}