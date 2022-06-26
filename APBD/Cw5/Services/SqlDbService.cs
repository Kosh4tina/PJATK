using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using Cw5.DTOs.Requests;
using Cw5.DTOs.Responses;
using Cw5.DTOs.ResultContainers;
using Cw5.Models;

namespace Cw5.Services
{
    public class SqlDbService : IStudentDbService
    {
        private const string ConnectionString = "Data Source=db-mssql;Initial Catalog=s19575;Integrated Security=True";

        public IEnumerable<Student> GetAllStudents()
        {
            using var sqlConnection = new SqlConnection(ConnectionString);
            using var sqlCommand = new SqlCommand {Connection = sqlConnection};
            sqlConnection.Open();
            return new SqlDbGetService(sqlCommand).GetAllStudents();
        }

        public GetSingleStudentResponse GetStudent(string indexNumber)
        {
            using var sqlConnection = new SqlConnection(ConnectionString);
            using var sqlCommand = new SqlCommand {Connection = sqlConnection};
            sqlConnection.Open();
            return new SqlDbGetService(sqlCommand).GetStudent(indexNumber);
        }

        public EnrollmentResult EnrollStudent(EnrollStudentRequest newStudent)
        {
            using var sqlConnection = new SqlConnection(ConnectionString);
            using var sqlCommand = new SqlCommand {Connection = sqlConnection};
            sqlConnection.Open();
            sqlCommand.Transaction = sqlConnection.BeginTransaction();
            try
            {
                var enrollmentResult = new SqlDbEnrollmentService(sqlCommand, newStudent).Enroll();
                if (enrollmentResult.Successful) sqlCommand.Transaction.Commit();
                else sqlCommand.Transaction.Rollback();
                return enrollmentResult;
            }
            catch (Exception exception)
            {
                sqlCommand.Transaction.Rollback();
                return new EnrollmentResult {Error = $"Wystapil blad podczas dodawania studenta - {exception}!"};
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
    }
}