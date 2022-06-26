using Cw5.DTOs.Requests;
using Cw5.Services;
using Microsoft.AspNetCore.Mvc;

namespace Cw5.Controllers
{
    [ApiController]
    [Route("api/enrollments")]
    public class EnrollmentsController : ControllerBase
    {
        private readonly IStudentDbService _dbService;

        public EnrollmentsController(IStudentDbService dbService)
        {
            _dbService = dbService;
        }

        [HttpPost]
        public IActionResult Enroll(EnrollStudentRequest newStudentRequest)
        {
            var enrollmentResult = _dbService.EnrollStudent(newStudentRequest);
            if (enrollmentResult.Successful)
                return Created($"/api/students/{newStudentRequest.IndexNumber}", enrollmentResult.Enrollment);
            return BadRequest(enrollmentResult.Error);
        }
    }
}