using Cw11.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace Cw11.Configurations
{
    public class MedicamentEfcConfiguration : IEntityTypeConfiguration<Medicament>
    {
        public void Configure(EntityTypeBuilder<Medicament> builder)
        {
            builder.ToTable("Medicament");
            builder.HasKey(medicament => medicament.IdMedicament);
            builder.Property(medicament => medicament.Name).IsRequired().HasMaxLength(100);
            builder.Property(medicament => medicament.Description).IsRequired().HasMaxLength(100);
            builder.Property(medicament => medicament.Type).IsRequired().HasMaxLength(100);
        }
    }
}