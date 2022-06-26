using Cw11.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace Cw11.Configurations
{
    public class PrescriptionEfcConfiguration : IEntityTypeConfiguration<Prescription>
    {
        public void Configure(EntityTypeBuilder<Prescription> builder)
        {
            builder.ToTable("Prescription");
            builder.HasKey(prescription => prescription.IdPrescription);
            builder.HasOne(prescription => prescription.Doctor)
                .WithMany(doctor => doctor.Prescriptions)
                .HasForeignKey(prescription => prescription.IdDoctor)
                .OnDelete(DeleteBehavior.Cascade)
                .IsRequired();
            builder.HasOne(p => p.Patient)
                .WithMany(p => p.Prescriptions)
                .HasForeignKey(p => p.IdPatient)
                .OnDelete(DeleteBehavior.Cascade)
                .IsRequired();
            builder.Property(prescription => prescription.Date).IsRequired().HasColumnType("date");
            builder.Property(prescription => prescription.DueDate).IsRequired().HasColumnType("date");
        }
    }
}