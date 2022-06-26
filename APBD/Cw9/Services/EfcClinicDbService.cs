using System.Collections.Generic;
using System.Linq;
using Cw11.DTOs;
using Cw11.Models;
using Microsoft.EntityFrameworkCore;

namespace Cw11.Services
{
    public class EfcClinicDbService : IClinicDbService
    {
        private readonly ClinicDbContext _context;

        public EfcClinicDbService(ClinicDbContext context)
        {
            _context = context;
        }

        public IEnumerable<Doctor> GetAllDoctors()
        {
            return _context.Doctors
                .Include(doctor => doctor.Prescriptions)
                .ToList();
        }

        public Doctor GetDoctor(int id)
        {
            return _context.Doctors.Include(doctor => doctor.Prescriptions)
                .FirstOrDefault(doctor => doctor.IdDoctor == id);
        }

        public int AddDoctor(NewDoctorRequest newNewDoctorRequest)
        {
            var newDoctor = new Doctor
            {
                FirstName = newNewDoctorRequest.FirstName,
                LastName = newNewDoctorRequest.LastName,
                Email = newNewDoctorRequest.Email
            };
            _context.Add(newDoctor);
            return _context.SaveChanges();
        }

        public int UpdateDoctor(int id, UpdateDoctorRequest newDoctorRequest)
        {
            var doctor = GetDoctor(id);
            if (doctor == null) return 0;
            doctor.FirstName = newDoctorRequest.FirstName ?? doctor.FirstName;
            doctor.LastName = newDoctorRequest.LastName ?? doctor.LastName;
            doctor.Email = newDoctorRequest.Email ?? doctor.Email;
            return _context.SaveChanges();
        }

        public int RemoveDoctor(int id)
        {
            var doctor = GetDoctor(id);
            if (doctor == null) return 0;
            _context.Remove(doctor);
            return _context.SaveChanges();
        }
    }
}