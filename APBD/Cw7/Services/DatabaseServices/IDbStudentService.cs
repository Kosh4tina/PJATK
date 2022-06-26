using System.Collections.Generic;
using Cw7.DTOs.Requests;
using Cw7.DTOs.Responses;
using Cw7.DTOs.ResultContainers;
using Cw7.Models;

namespace Cw7.Services.DatabaseServices
{
    public interface IDbStudentService
    {
        public IEnumerable<Student> GetAllStudents();
        public GetSingleStudentResponse GetStudent(string indexNumber);
        public EnrollmentResult EnrollStudent(EnrollStudentRequest newStudent);
        public Enrollment PromoteStudents(PromoteStudentsRequest promoteStudentsRequest);
        public SingleStudentAuthenticationData GetStudentsAuthenticationData(string indexNumber);
        public bool UpdateRefreshToken(string username, string refreshToken);
    }
}