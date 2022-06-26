public class FileMessage {
    long id;
    String data;
    Person intruder;
    String body;
    Mieszkanie mieszkanie;

    public FileMessage(long id, String data, Person intruder, Mieszkanie mieszkanie, String body){
        setId(id);
        setData(data);
        setIntruder(intruder);
        setMieszkanie(mieszkanie);
        setBody(body);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setIntruder(Person intruder) {
        this.intruder = intruder;
    }

    public void setMieszkanie(Mieszkanie mieszkanie){
        this.mieszkanie = mieszkanie;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public Person getIntruder() {
        return intruder;
    }

    public String getMieszkanie() {
        return mieszkanie.getNazwa();
    }

    @Override
    public String toString() {
        return "\nNumber: " + id + "\nData: " + data + "\nDescription = " + body;
    }
}
