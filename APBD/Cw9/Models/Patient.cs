using System;
using System.Collections.Generic;

namespace Cw11.Models
{
    public class Patient
    {
        public int IdPatient { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public DateTime Birthdate { get; set; }
        public ICollection<Prescription> Prescriptions { get; set; }
    }
}