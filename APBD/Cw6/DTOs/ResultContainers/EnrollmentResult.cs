using Cw6.Models;

namespace Cw6.DTOs.ResultContainers
{
    public class EnrollmentResult
    {
        public bool Successful { get; set; }
        public Student Student { get; set; }
        public Enrollment Enrollment { get; set; }
        public string Error { get; set; }
    }
}