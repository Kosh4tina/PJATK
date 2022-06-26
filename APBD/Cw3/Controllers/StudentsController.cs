using cw3.DAL;
using cw3.Models;
using Microsoft.AspNetCore.Mvc;
using System.Linq;

namespace cw3.Controllers
{
    [ApiController]
    [Route("api/students")]
    public class StudentsController : ControllerBase
    {
        private readonly IDbService<Student> _dbService;

        public StudentsController(IDbService<Student> dbService)
        {
            _dbService = dbService;
        }

        //Post
        [HttpPost]
        public IActionResult CreateStudent(Student student)
        {
            student.IdStudent = _dbService.NextId();
            _dbService.AddStudent(student);
            return Ok($"Utworzono studenta: {student}.");
        }

        //PUT
        [HttpPut("{idStudent}")]
        public IActionResult PutStudent([FromRoute] int idStudent, [FromBody] Student newStudent)
        {
            var student = _dbService.GetStudents(idStudent);
            if (student == null) return CreateStudent(newStudent);
            student.FirstName = newStudent.FirstName;
            student.LastName = newStudent.LastName;
            return Ok($" Ąktualizacja dla {student} dokończona");
        }
        //DELETE
        [HttpDelete("{idStudent}")]
        public IActionResult DeleteStudent([FromRoute] int idStudent)
        {
            var student = _dbService.GetStudents(idStudent);
            if (student == null) return NotFound($"Nie odnaleziono studenta o id: {idStudent}!");
            _dbService.RemoveStudent(student);
            return Ok($"Usuwanie {student} ukończone");
        }

        // jako segment adresu URL
        [HttpGet("{idStudent}")]
        public IActionResult GetStudent([FromRoute] int idStudent)
        {
            var student = _dbService.GetStudents(idStudent);
            if (student == null) return NotFound($"Nie odnaleziono studenta o id: {idStudent}");
            return Ok(student);
        }

        //Query-string
        [HttpGet]
        public IActionResult GetStudents([FromQuery] string orderBy)
        {
            var orderByToUse = orderBy ?? "IdStudent";
            var orderedEnumerable = _dbService.GetStudents();
            return orderByToUse.ToLower() switch
            {
                "firstname" => Ok(orderedEnumerable.OrderBy(student => student.FirstName)),
                "lastname" => Ok(orderedEnumerable.OrderBy(student => student.LastName)),
                "indexnumber" => Ok(orderedEnumerable.OrderBy(student => student.IndexNumber)),
                _ => Ok(orderedEnumerable.OrderBy(student => student.IdStudent))
            };
        }
    }
}
