using Cw11.DTOs;
using Cw11.Services;
using Microsoft.AspNetCore.Mvc;

namespace Cw11.Controllers
{
    [ApiController]
    [Route("api/doctors")]
    public class DoctorsController : ControllerBase
    {
        private readonly IClinicDbService _dbService;

        public DoctorsController(IClinicDbService dbService)
        {
            _dbService = dbService;
        }

        [HttpGet]
        public IActionResult GetAllDoctors()
        {
            return Ok(_dbService.GetAllDoctors());
        }

        [HttpGet("{id}")]
        public IActionResult GetDoctor([FromRoute] int id)
        {
            var doctor = _dbService.GetDoctor(id);
            if (doctor == null) return BadRequest("Doctor doesn't exist!");
            return Ok(doctor);
        }

        [HttpPost]
        public IActionResult AddDoctor([FromBody] NewDoctorRequest newDoctorRequest)
        {
            var modifiedEntries = _dbService.AddDoctor(newDoctorRequest);
            if (modifiedEntries == 0) return BadRequest("No entries were modified");
            return Ok($"{modifiedEntries} entries changed.");
        }

        [HttpPut("{id}")]
        public IActionResult UpdateDoctor([FromRoute] int id, [FromBody] UpdateDoctorRequest newDoctorRequest)
        {
            var modifiedEntries = _dbService.UpdateDoctor(id, newDoctorRequest);
            if (modifiedEntries == 0) return BadRequest("No entries were modified");
            return Ok($"{modifiedEntries} entries changed.");
        }

        [HttpDelete("{id}")]
        public IActionResult UpdateDoctor([FromRoute] int id)
        {
            var modifiedEntries = _dbService.RemoveDoctor(id);
            if (modifiedEntries == 0) return BadRequest("No entries were modified");
            return Ok($"{modifiedEntries} entries changed.");
        }
    }
}