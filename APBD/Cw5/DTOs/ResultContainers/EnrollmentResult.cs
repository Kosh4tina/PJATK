using Cw5.Models;

namespace Cw5.DTOs.ResultContainers
{
    public class EnrollmentResult
    {
        public bool Successful { get; set; }
        public Student Student { get; set; }
        public Enrollment Enrollment { get; set; }
        public string Error { get; set; }
    }
}