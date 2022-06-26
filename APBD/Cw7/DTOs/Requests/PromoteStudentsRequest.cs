using System.ComponentModel.DataAnnotations;

namespace Cw7.DTOs.Requests
{
    public class PromoteStudentsRequest
    {
        [Required(ErrorMessage = "Nazwa kierunku jest wymagana!")]
        public string Studies { get; set; }

        [Required(ErrorMessage = "Numer semestru jest wymagany!")]
        public int Semester { get; set; }
    }
}