using cw3.Models;

namespace cw3.DAL
{
    public interface MockDbService : IDbService<Student, string>
    {
        public Studing GetStartedStudies(string indexNumber);
    }
}
