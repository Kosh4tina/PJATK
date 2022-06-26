using Cw11.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace Cw11.Configurations
{
    public class PatientEfcConfiguration : IEntityTypeConfiguration<Patient>
    {
        public void Configure(EntityTypeBuilder<Patient> builder)
        {
            builder.ToTable("Patient");
            builder.HasKey(patient => patient.IdPatient);
            builder.Property(patient => patient.FirstName).IsRequired().HasMaxLength(100);
            builder.Property(patient => patient.LastName).IsRequired().HasMaxLength(100);
            builder.Property(patient => patient.Birthdate).IsRequired().HasColumnType("date");
        }
    }
}