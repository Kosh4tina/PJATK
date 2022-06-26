using System;
using System.Text.Json.Serialization;

namespace cw2
{
    class Student
    {
        [JsonPropertyName("indexNumber")]
        public string index { get; set; }
        [JsonPropertyName("fname")]
        public string firstName { get; set; }
        [JsonPropertyName("lname")]
        public string lastName { get; set; }
        [JsonPropertyName("birthdate")]
        public DateTime birth { get; set; }
        [JsonPropertyName("email")]
        public string email { get; set; }
        [JsonPropertyName("mothersName")]
        public string mother { get; set; }
        [JsonPropertyName("fathersName")]
        public string father { get; set; }
        public string studies { get; set; }
        public string mode { get; set; }

        public string ToString()
        {
            return "index: " + index
                + " Imie " + firstName
                + " Nazwisko " + lastName
                + " Data " + birth
                + " mail " + email
                + " mother " + mother
                + " father " + father
                + " studies " + studies
                + " mode " + mode;
        }
    }
}
