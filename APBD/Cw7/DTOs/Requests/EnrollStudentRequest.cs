using System;
using System.ComponentModel.DataAnnotations;

namespace Cw7.DTOs.Requests
{
    public class EnrollStudentRequest
    {
        [Required(ErrorMessage = "Numer indeksu jest wymagany!")]
        [RegularExpression("^s[\\d]+$")]
        public string IndexNumber { get; set; }

        [Required(ErrorMessage = "Imię jest wymagane podczas!")]
        public string FirstName { get; set; }

        [Required(ErrorMessage = "Nazwisko jest wymagane!")]
        public string LastName { get; set; }

        [Required(ErrorMessage = "Data urodzenia jest wymagana!")]
        public DateTime BirthDate { get; set; }

        [Required(ErrorMessage = "Kierunek studiów jest wymagany!")]
        public string Studies { get; set; }

        [Required(ErrorMessage = "Hasło użytkownika jest wymagane!")]
        public string Password { get; set; }
    }
}