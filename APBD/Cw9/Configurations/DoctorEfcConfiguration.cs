using Cw11.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace Cw11.Configurations
{
    public class DoctorEfcConfiguration : IEntityTypeConfiguration<Doctor>
    {
        public void Configure(EntityTypeBuilder<Doctor> builder)
        {
            builder.ToTable("Doctor");
            builder.HasKey(doctor => doctor.IdDoctor);
            builder.Property(doctor => doctor.FirstName).IsRequired().HasMaxLength(100);
            builder.Property(doctor => doctor.LastName).IsRequired().HasMaxLength(100);
            builder.Property(doctor => doctor.Email).IsRequired().HasMaxLength(100);
        }
    }
}