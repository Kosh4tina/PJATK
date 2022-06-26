using System;
using System.ComponentModel.DataAnnotations;

namespace Cw6.DTOs.Requests
{
    public class EnrollStudentRequest
    {
        [Required(ErrorMessage = "Numer indeksu jest wymagany!")]
        [MaxLength(100)]
        [RegularExpression("^s[\\d]+$")]
        public string IndexNumber { get; set; }

        [Required(ErrorMessage = "Imię jest wymagane podczas!")]
        [MaxLength(100)]
        public string FirstName { get; set; }

        [Required(ErrorMessage = "Nazwisko jest wymagane!")]
        [MaxLength(100)]
        public string LastName { get; set; }

        [Required(ErrorMessage = "Data urodzenia jest wymagana!")]
        public DateTime BirthDate { get; set; }

        [Required(ErrorMessage = "Kierunek studiów jest wymagany!")]
        [MaxLength(100)]
        public string Studies { get; set; }
    }
}