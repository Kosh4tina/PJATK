using Cw11.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace Cw11.Configurations
{
    public class PrescriptionMedicamentEfcConfiguration : IEntityTypeConfiguration<PrescriptionMedicament>
    {
        public void Configure(EntityTypeBuilder<PrescriptionMedicament> builder)
        {
            builder.ToTable("Prescription_Medicament");
            builder.HasKey(prescriptionMedicament => new
            {
                prescriptionMedicament.IdPrescription,
                prescriptionMedicament.IdMedicament
            });
            builder.HasOne(prescriptionMedicament => prescriptionMedicament.Prescription)
                .WithMany(prescription => prescription.PrescriptionMedicaments)
                .HasForeignKey(prescription => prescription.IdPrescription)
                .OnDelete(DeleteBehavior.Cascade)
                .IsRequired();
            builder.HasOne(prescriptionMedicament => prescriptionMedicament.Medicament)
                .WithMany(p => p.PrescriptionMedicaments)
                .HasForeignKey(prescriptionMedicament => prescriptionMedicament.IdMedicament)
                .OnDelete(DeleteBehavior.Cascade)
                .IsRequired();
            builder.Property(prescriptionMedicament => prescriptionMedicament.Details).IsRequired().HasMaxLength(100);
        }
    }
}