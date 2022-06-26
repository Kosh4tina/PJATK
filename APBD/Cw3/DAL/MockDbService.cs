using cw3.Models;
using System.Collections.Generic;
using System.Data;
using System.Linq;

namespace cw3.DAL
{
    public class MockDbService : IDbService<Student>
    {
        private static readonly ICollection<Student> Students = new List<Student>
        {
            new Student {IdStudent = 1, FirstName = "Jan", LastName = "Kowalski"},
            new Student {IdStudent = 2, FirstName = "Anna", LastName = "Malewska"},
            new Student {IdStudent = 3, FirstName = "Andrzej", LastName = "Andrzejewicz"}
        };

        public IEnumerable<Student> GetStudents()
        {
            return Students;
        }

        public Student GetStudents(int id)
        {
            var studentsWithId = Students.Where(student => student.IdStudent == id).ToList();
            if (studentsWithId.Count == 0) return null;
            if (studentsWithId.Count > 1)
                throw new DataException($"Istnieje {studentsWithId.Count} studentow o podanym id");
            return studentsWithId.First();
        }

        public int NextId()
        {
            var ids = Students.Select(student => student.IdStudent).ToList();
            if (ids.Count == 0) return 1;
            ids.Sort();
            return ids.Last() + 1;
        }

        public void AddStudent(Student StToAdd)
        {
            if (Students.Count(student => student.IdStudent == StToAdd.IdStudent) > 0)
                throw new DataException($"Student o id: {StToAdd.IdStudent} już istnieje");
            Students.Add(StToAdd);
        }

        public void RemoveStudent(Student StToRemove)
        {
            Students.Remove(StToRemove);
        }
    }
}
