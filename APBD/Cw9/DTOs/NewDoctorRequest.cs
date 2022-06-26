using System.ComponentModel.DataAnnotations;

namespace Cw11.DTOs
{
    public class NewDoctorRequest
    {
        [Required(ErrorMessage = "First name of a doctor is required!", AllowEmptyStrings = false)]
        [MaxLength(100)]
        public string FirstName { get; set; }

        [Required(ErrorMessage = "Last name of a doctor is required!", AllowEmptyStrings = false)]
        [MaxLength(100)]
        public string LastName { get; set; }

        [Required(ErrorMessage = "Email of a doctor is required!", AllowEmptyStrings = false)]
        [MaxLength(100)]
        public string Email { get; set; }
    }
}