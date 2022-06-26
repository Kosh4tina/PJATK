using System.Collections.Generic;
using Cw6.DTOs.Requests;
using Cw6.DTOs.Responses;
using Cw6.DTOs.ResultContainers;
using Cw6.Models;

namespace Cw6.Services
{
    public interface IStudentDbService
    {
        public IEnumerable<Student> GetAllStudents();
        public GetSingleStudentResponse GetStudent(string indexNumber);
        public EnrollmentResult EnrollStudent(EnrollStudentRequest newStudent);
        public Enrollment PromoteStudents(PromoteStudentsRequest promoteStudentsRequest);
    }
}