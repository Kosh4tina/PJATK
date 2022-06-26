using cw3.Models;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;

namespace cw3.DAL
{
    public class SqlIDbService : MockDbService
    {
        private const string ConnectionString = "Data Source=db-mssql; Initial Catalog=s19575; Integrated Security=True";

        private const string SelectQuery = "SELECT * FROM Student";

        private const string InsertQuery = "INSERT INTO Student(IndexNumber, FirstName, LastName, BirthDate, " +
                                           "IdEnrollment) VALUES (@IndexNumber, @FirstName, @LastName, @BirthDate, " +
                                           "@IdEnrollment)";

        private const string UpdateQuery = "UPDATE Student SET FirstName = @FirstName, LastName = @LastName, " +
                                           "BirthDate = @BirthDate, IdEnrollment = @IdEnrollment WHERE " +
                                           "IndexNumber = @IndexNumber";

        private const string DeleteQuery = "DELETE FROM Student WHERE IndexNumber = @IndexNumber";

        private const string StartedStudiesQuery = "SELECT IndexNumber, FirstName, LastName, BirthDate, Name, " +
                                                   "Semester, StartDate FROM Student INNER JOIN Enrollment on " +
                                                   "Student.IdEnrollment = Enrollment.IdEnrollment INNER JOIN " +
                                                   "Studies on Studies.IdStudy = Enrollment.IdStudy " +
                                                   "WHERE IndexNumber = @IndexNumber";

        public IEnumerable<Student> GetStudents()
        {
            var students = new List<Student>();
            using var sqlConnection = new SqlConnection(ConnectionString);
            using var sqlCommand = new SqlCommand { Connection = sqlConnection, CommandText = SelectQuery };
            Console.WriteLine(sqlCommand.CommandText);
            sqlConnection.Open();
            var sqlDataReader = sqlCommand.ExecuteReader();
            while (sqlDataReader.Read()) students.Add(SqlDataReaderToStudent(sqlDataReader));
            return students;
        }

        public Student GetStudent(string indexNumber)
        {
            using var sqlConnection = new SqlConnection(ConnectionString);
            using var sqlCommand = new SqlCommand
            { Connection = sqlConnection, CommandText = $"{SelectQuery} WHERE IndexNumber = @IndexNumber" };
            sqlCommand.Parameters.AddWithValue("IndexNumber", indexNumber);
            sqlConnection.Open();
            var sqlDataReader = sqlCommand.ExecuteReader();
            return !sqlDataReader.Read() ? null : SqlDataReaderToStudent(sqlDataReader);
        }

        public int AddStudent(Student StToAdd)
        {
            using var sqlConnection = new SqlConnection(ConnectionString);
            using var sqlCommand = new SqlCommand { Connection = sqlConnection, CommandText = InsertQuery };
            StudentSqlParameters(StToAdd, sqlCommand);
            sqlConnection.Open();
            return sqlCommand.ExecuteNonQuery();
        }

        public int UpdateStudent(Student StToUpdate)
        {
            using var sqlConnection = new SqlConnection(ConnectionString);
            using var sqlCommand = new SqlCommand { Connection = sqlConnection, CommandText = UpdateQuery };
            StudentSqlParameters(StToUpdate, sqlCommand);
            sqlConnection.Open();
            return sqlCommand.ExecuteNonQuery();
        }

        public int RemoveStudent(string idToRemove)
        {
            using var sqlConnection = new SqlConnection(ConnectionString);
            using var sqlCommand = new SqlCommand { Connection = sqlConnection, CommandText = DeleteQuery };
            sqlCommand.Parameters.AddWithValue("IndexNumber", idToRemove);
            sqlConnection.Open();
            return sqlCommand.ExecuteNonQuery();
        }

        public Studing GetStartedStudies(string indexNumber)
        {
            using var sqlConnection = new SqlConnection(ConnectionString);
            using var sqlCommand = new SqlCommand { Connection = sqlConnection, CommandText = StartedStudiesQuery };
            sqlCommand.Parameters.AddWithValue("indexNumber", indexNumber);
            sqlConnection.Open();
            var sqlDataReader = sqlCommand.ExecuteReader();
            if (!sqlDataReader.Read()) return null;
            return new Studing
            {
                IndexNumber = sqlDataReader["IndexNumber"].ToString(),
                FirstName = sqlDataReader["FirstName"].ToString(),
                LastName = sqlDataReader["LastName"].ToString(),
                BirthDate = DateTime.Parse(sqlDataReader["BirthDate"].ToString()!),
                Name = sqlDataReader["Name"].ToString(),
                Semester = int.Parse(sqlDataReader["Semester"].ToString()!),
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
                IdEnrollment = int.Parse(sqlDataReader["IdEnrollment"].ToString()!)
            };
        }

        private static void StudentSqlParameters(Student student, SqlCommand sqlCommand)
        {
            var sqlParameters = sqlCommand.Parameters;
            sqlParameters.AddWithValue("IndexNumber", student.IndexNumber);
            sqlParameters.AddWithValue("FirstName", student.FirstName);
            sqlParameters.AddWithValue("LastName", student.LastName);
            sqlParameters.AddWithValue("BirthDate", student.BirthDate);
            sqlParameters.AddWithValue("IdEnrollment", student.IdEnrollment);
        }
    }
}
