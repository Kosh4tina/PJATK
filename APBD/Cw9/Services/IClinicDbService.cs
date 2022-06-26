using System.Collections.Generic;
using Cw11.DTOs;
using Cw11.Models;

namespace Cw11.Services
{
    public interface IClinicDbService
    {
        IEnumerable<Doctor> GetAllDoctors();
        Doctor GetDoctor(int id);
        int AddDoctor(NewDoctorRequest newNewDoctorRequest);
        int UpdateDoctor(int id, UpdateDoctorRequest newDoctorRequest);
        int RemoveDoctor(int id);
    }
}