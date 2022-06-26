using cw3.DAL;
using cw3.Models;
using Microsoft.AspNetCore.Mvc;
namespace cw3.Controllers
{
    [ApiController]
    [Route("api/students")]
    public class StudentsController : ControllerBase
    {
        private readonly SqlIDbService _dbService;

        public StudentsController(SqlIDbService dbService)
        {
            _dbService = dbService;
        }

        [HttpGet("{idStudent}")]
        public IActionResult GetStudent([FromRoute] string idStudent)
        {
            var student = _dbService.GetStartedStudies(idStudent);
            if (student == null) return NotFound($"Nie odnaleziono studenta o id: {idStudent}");
            return Ok(student);
        }

        [HttpPost]
        public IActionResult CreateStudent([FromBody] Student student)
        {
            var affectedRows = _dbService.AddStudent(student);
            return Ok($"Zmodyfikowano {affectedRows} wiersz(y) w bazie danych.");
        }

        [HttpPut("{idStudent}")]
        public IActionResult PutStudent([FromRoute] string idStudent, [FromBody] Student newStudent)
        {
            newStudent.IndexNumber = idStudent;
            var affectedRows = _dbService.UpdateStudent(newStudent);
            return affectedRows == 0
                ? (IActionResult)NotFound($"Nie znaleziono studenta o numerze indeksu: {idStudent}!")
                : Ok($"Zmodyfikowano {affectedRows} wiersz(y) w bazie danych.");
        }

        [HttpDelete("{idStudent}")]
        public IActionResult DeleteStudent([FromRoute] string idStudent)
        {
            var affectedRows = _dbService.RemoveStudent(idStudent);
            return affectedRows == 0
                ? (IActionResult)NotFound($"Nie znaleziono studenta o numerze indeksu: {idStudent}!")
                : Ok($"Zmodyfikowano {affectedRows} wiersz(y) w bazie danych.");
        }
    }
}
