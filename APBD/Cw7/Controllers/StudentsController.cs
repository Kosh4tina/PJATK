using Cw7.Services.DatabaseServices;
using Microsoft.AspNetCore.Mvc;

namespace Cw7.Controllers
{
    [ApiController]
    [Route("api/students")]
    public class StudentsController : ControllerBase
    {
        private static IDbStudentService _dbService;

        public StudentsController(IDbStudentService dbService)
        {
            _dbService = dbService;
        }

        [HttpGet]
        public IActionResult GetStudents()
        {
            return Ok(_dbService.GetAllStudents());
        }

        [HttpGet("{idStudent}")]
        public IActionResult GetStudent([FromRoute] string idStudent)
        {
            var student = _dbService.GetStudent(idStudent);
            if (student == null) return NotFound($"Nie odnaleziono studenta o id: {idStudent}!");
            return Ok(student);
        }
    }
}