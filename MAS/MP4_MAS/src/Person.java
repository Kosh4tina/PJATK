import functions.*;
import java.util.*;

public class Person {
	private String pesel;
	private String name;
	private String surname;
	private Rider rider;
	private Coach coach;
	
	private static Set<String> pesels = new TreeSet<>();

	public Person(String pesel, String name, String surname, PersonType type) throws RuntimeException{
		setPesel(pesel);
		setName(name);
		setSurname(surname);
		if(type == PersonType.RIDER){
			this.rider = new Rider(this);
			this.coach = null;
		}
		else if(type == PersonType.COACH){
			this.rider = null;
			this.coach = new Coach(this);
		}
		else if(type == PersonType.RIDER_COACH){
			throw new WrongPersonTypeException();
		}
	}

	public String getPesel() {
		return pesel;
	}

	private void setPesel(String pesel) throws NotUniqueValueException, WrongFormatException {
		if(peselUsed(pesel))
			throw new NotUniqueValueException();
		else if(!pesel.matches("\\d{11}")) //7. Ograniczenie wlasne
			throw new WrongFormatException();
		else
			this.pesel = pesel;
			
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Rider getRider(){
		return this.rider;
	}
	public Coach getCoach(){
		return this.coach;
	}
	
	
	private static boolean peselUsed(String pesel){
		return pesels.contains(pesel);
	}
	
	public String toString(){
		if(this.rider != null)
			return this.getRider().toString();
		else
			return this.getCoach().toString();
	}
}
