using System.ComponentModel.DataAnnotations;

namespace Cw5.DTOs.Requests
{
    public class PromoteStudentsRequest
    {
        [Required(ErrorMessage = "Nazwa kierunku jest wymagana!")]
        [MaxLength(100)]
        public string Studies { get; set; }

        [Required(ErrorMessage = "Numer semestru jest wymagany!")]
        public int Semester { get; set; }
    }
}