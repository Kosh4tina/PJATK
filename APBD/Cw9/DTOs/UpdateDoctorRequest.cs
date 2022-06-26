using System.ComponentModel.DataAnnotations;

namespace Cw11.DTOs
{
    public class UpdateDoctorRequest
    {
        [MaxLength(100)] public string FirstName { get; set; }

        [MaxLength(100)] public string LastName { get; set; }

        [MaxLength(100)] public string Email { get; set; }
    }
}