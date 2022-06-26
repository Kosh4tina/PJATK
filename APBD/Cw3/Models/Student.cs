namespace cw3.Models
{
    public class Student
    {
        private int _idStudent;

        public int IdStudent
        {
            get => _idStudent;
            set
            {
                _idStudent = value;
                IndexNumber = $"s{value}";
            }
        }

        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string IndexNumber { get; private set; }

        public override string ToString()
        {
            return $"{IdStudent}, {FirstName} {LastName}, {IndexNumber}";
        }
    }
}
