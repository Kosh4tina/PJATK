using System.Collections.Generic;

namespace cw3.DAL
{
    public interface IDbService<T>
    {
        public IEnumerable<T> GetStudents();
        public T GetStudents(int id);
        public int NextId();
        public void AddStudent(T entryToAdd);
        public void RemoveStudent(T entryToRemove);
    }
}
