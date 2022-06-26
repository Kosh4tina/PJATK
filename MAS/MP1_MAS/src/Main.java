import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws Exception {

        //wczytuje to co bylo ostatnio zapisane
        if(new File("data/Database").isFile())
        {
            try
            {
                //miejsce docelowe pliku w workspace
                FileInputStream fileInput = new FileInputStream("data/Database");
                ObjectInputStream streamInput = new ObjectInputStream(fileInput);
                //odczyt z dysku do pliku "Database"
                Extension.readExtension(streamInput);
                streamInput.close();
                fileInput.close();
            }
            catch(IOException i){
                i.printStackTrace();
                return;
            }
            catch(ClassNotFoundException c){
                System.out.println("Class not found.");
                c.printStackTrace();
                return;
            }
        }

        //utworzenie schematu daty i daty urodzenia
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = dateFormat.parse("1978/12/23");

        //utworzenie nowego chefa
        Сhef cukernik = new Сhef("Macej","Drobnik",date,null, "+48 113 224 112","Męska", 6500);
        //przeciążenie metody string
        cukernik.addCertificate("Cukiernik");
        //przeciążenie metody int
        cukernik.addCertificate(451781);
        // pokazuje wszystkich Chefow którzy istnieją
        Extension.showExtension(Сhef.class);
        System.out.println("Pensia sriednia: " + Pracownik.getAverageSalary());

        //zapis ekstenscji do pliku
        //trwanie Ekstensji

        try
        {
            //miejsce docelowe pliku w workspace
            //zapis z dysku do pliku "database"
            FileOutputStream fileOutput = new FileOutputStream("data/Database");
            ObjectOutputStream StreamOutput = new ObjectOutputStream(fileOutput);
            Extension.saveExtension(Str eamOutput);
            StreamOutput.close();
            fileOutput.close();
        }
        catch(IOException i)
        {
            i.printStackTrace();
        }

    }

}
