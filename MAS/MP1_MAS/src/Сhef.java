import java.util.Date;
import java.util.Vector;

public class Сhef extends Pracownik {

    private static final long serialVersionUID = 1L;

    // atrybut prosty, powtarzalny, obiektu
    private Vector<String> Certificate = new Vector<>();

    Сhef(String imieOsoby, String nazwiskoOsoby, Date dataUrodzeniaOsoby, String adresOsoby, String nrTelefonuOsoby, String plecOsoby, int pensiaOsoby)
    {
        //dziedziczenie poprzez super z klasy Pracownik
        super(imieOsoby,nazwiskoOsoby,dataUrodzeniaOsoby,adresOsoby, nrTelefonuOsoby, plecOsoby, pensiaOsoby);
    }
    //przeslanianie metody
    public String getName()
    {
        return "Chef " + this.imie;
    }

    //przeciążenie metody String / int
    public void addCertificate(String CertificateName) throws Exception
    {
        if(CertificateName == null)
        {
            throw new Exception("The license must have a name.");
        }
        Certificate.add(CertificateName);
    }

    //przeciążenie metody String / int
    public void addCertificate(int CertificateNumber) throws Exception
    {
        Certificate.add(CertificateNumber + "");
    }

    public String toString()
    {
        //Do opisu Chefa dodajem poprzez użycie super elementów z klasy pracownik
        String join = super.toString();
        join += "\nCertificate: ";

        if(Certificate.size() > 0)
        {
            for(String CertificateName : Certificate)
            {
                join += CertificateName + ", ";
            }
        }
        else
        {
            join += "Certificate does not exist.";
        }
        return join;
    }
}
