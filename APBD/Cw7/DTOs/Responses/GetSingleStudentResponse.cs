using System;

namespace Cw7.DTOs.Responses
{
    public class GetSingleStudentResponse
    {
        public string IndexNumber { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public DateTime BirthDate { get; set; }
        public string Name { get; set; }
        public int Semester { get; set; }
        public DateTime StartDate { get; set; }
    }
}