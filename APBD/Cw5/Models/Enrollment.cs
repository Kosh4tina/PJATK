using System;

namespace Cw5.Models
{
    public class Enrollment
    {
        public int IdEnrollment { get; set; }
        public int Semester { get; set; }
        public int IdStudy { get; set; }
        public string StudiesName { get; set; }
        public DateTime StartDate { get; set; }
    }
}