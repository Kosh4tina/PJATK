using System.Collections.Generic;

namespace cw3.DAL
{
    public interface IDbService<T, in TI>
    {
        public IEnumerable<T> GetStudents();
        public T GetStudent(TI indexNumber);
        public int AddStudent(T StToAdd);
        public int UpdateStudent(T StToUpdate);
        public int RemoveStudent(TI StToRemove);
    }
}
