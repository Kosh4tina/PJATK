import functions.ItemsType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class MyThread extends Thread{
    private boolean isActive;
    String dataCur;
    String dataDo;
    Person currentPerson;
    Mieszkanie currentMieszkanie;
    MiejsceParkingowe currentMiejsceParkingowe;

    Date todayD = new Date();
    String today = new SimpleDateFormat("yyyy-MM-dd").format(todayD);

    public MyThread(Person person, Mieszkanie mieszkanie, MiejsceParkingowe miejsceParkingowe){
        this.dataCur = mieszkanie.getDataOd();
        this.dataDo = mieszkanie.getDataDo();
        this.currentPerson = person;
        this.currentMieszkanie = mieszkanie;
        this.currentMiejsceParkingowe = miejsceParkingowe;
        isActive = true;
    }

    void disable(){
        isActive=false;
    }

    @Override
    public void run() {
        while (isActive) {
            dataCur = addOneDay(dataCur);
            System.out.println("Today: " + dataCur);
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dataCur = addOneDay(dataCur);
            System.out.println("Today: " + dataCur);
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            checkPayment();
        }
        System.out.println("The simulation is over ");
    }

    public void checkPayment(){
        System.out.println("Checking the payment for the apartment!");
        try {
            Date dateOd = new SimpleDateFormat("yyyy-MM-dd").parse(this.dataCur);
            Date dateDo = new SimpleDateFormat("yyyy-MM-dd").parse(this.dataDo);

            if(dateDo.after(dateOd)){
                System.out.println("The apartment does not need payment!");
                isActive = true;
            }else{
                System.out.println("Today: " + dataCur);
                System.out.println("You need to pay for the apartment !");
                System.out.println("You receive one warning, if you pay within 30 days, the warning will be removed ");
                String body = "Violation for non-payment of an apartment  " + currentMieszkanie.getNazwa();
                currentPerson.addFileM(currentMieszkanie, body);
                sleep(5000);
                dataCur = LocalDate.parse(dataCur).plusDays(30).toString();
                System.out.println("30 days later");
                System.out.println("Today: " + dataCur);
                System.out.println("We have not received payment for the apartment, unfortunately, you can no longer remove the warning!");
                System.out.println("Property check in progress...");
                sleep(2000);
                ArrayList<Items> items = currentMiejsceParkingowe.getItems();
                Items arrestedItem = null;
                for (Items item : items) {
                    if (item.wlascicel == currentPerson && item.getTyp() != ItemsType.RANDOM_ITEM) {
                        arrestedItem = item;
                        currentMiejsceParkingowe.removeItem(item);
                        currentPerson.removeItem(item);
                        dataCur = LocalDate.parse(dataCur).plusDays(60).toString();
                        break;
                    }
                }

                if (arrestedItem != null) {
                    System.out.println("As a result of the check, it was found in your parking space: " + arrestedItem.getNazwa());
                    System.out.println("The item was seized as debt repayment ");
                    System.out.println("Apartment rent increased by 2 months ");
                }else{
                    System.out.println("As a result of the check in the parking space, no items were found that can be removed ");
                    System.out.println("You and your neighbors will be removed from the apartment within 5 seconds ");
                    System.out.println("All your belongings will be seized");
                    System.out.println("Removal process...");
                    sleep(5000);
                    for (Items item : items) {
                        if (item.wlascicel == currentPerson) {
                            item.wlascicel = null;
                            currentPerson.removeItem(item);
                            currentMiejsceParkingowe.removeItem(item);
                            currentMieszkanie.removeItem(item);
                        }
                    }
                    currentPerson.removeMieszkanie(currentMieszkanie);
                    isActive = false;
                }
            }
        } catch (ParseException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    static public String addOneDay(String date) {
        return LocalDate.parse(date).plusDays(1).toString();
    }

    static public String addSomeDays(String date, int equal) {
        return LocalDate.parse(date).plusDays(equal).toString();
    }
}