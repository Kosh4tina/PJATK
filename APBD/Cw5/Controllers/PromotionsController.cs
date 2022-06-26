using Cw5.DTOs.Requests;
using Cw5.Services;
using Microsoft.AspNetCore.Mvc;

namespace Cw5.Controllers
{
    [ApiController]
    [Route("api/enrollments/promotions")]
    public class PromotionsController : ControllerBase
    {
        private readonly IStudentDbService _dbService;

        public PromotionsController(IStudentDbService dbService)
        {
            _dbService = dbService;
        }

        [HttpPost]
        public IActionResult PromoteStudents(PromoteStudentsRequest promoteStudentsRequest)
        {
            var newEnrollment = _dbService.PromoteStudents(promoteStudentsRequest);
            if (newEnrollment != null) return Created("/api/students", newEnrollment);
            return BadRequest($"Nie istnieje w bazie danych wpisu o kierunku '{promoteStudentsRequest.Studies}' " +
                              $"i semestrze {promoteStudentsRequest.Semester}");
        }
    }
}