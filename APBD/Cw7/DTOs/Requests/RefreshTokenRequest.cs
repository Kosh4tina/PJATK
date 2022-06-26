using System.ComponentModel.DataAnnotations;

namespace Cw7.DTOs.Requests
{
    public class RefreshTokenRequest
    {
        [Required(ErrorMessage = "RefreshToken jest wymagany!")]
        public string RefreshToken { get; set; }
    }
}