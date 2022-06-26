import java.util.Calendar;
import functions.*;

public class Main {
	public static void main(String[] args) {
		String line = "----------------------------------";
		System.out.println(line);

		//Tworzenie przykladowych koni
		Horse h1 = new Horse("Hirsa");
		Horse h2 = new Horse("Broadway");
		Horse h3 = new Horse("Juka");

		//Tworzenie przykladowych osob w rolach jezdeca lub trenera
		Person p1 = new Person("95050511111", "Arthur", "Penhaligon", PersonType.RIDER);
		Person p2 = new Person("97041223456", "Suzy", "Turquoise", PersonType.COACH);
		Person p3 = new Person("17121635353", "Arthur", "Clarke", PersonType.COACH);
		Person p4 = new Person("94010199999", "Artemis", "Fowl", PersonType.RIDER);
		Person p5 = new Person("90013175758", "Lucy", "Pevensie", PersonType.RIDER);

		//1. Ograniczenie dynamiczne atrybutu - poziom doswiadczenia jezdeca {expL >= P.expL}
		System.out.println(p1.toString() + ": poziom doswiadczenia = " + p1.getRider().getExperienceLevel());
		try{
			p1.getRider().setExperienceLevel(5);
			System.out.println(p1.toString() + ": poziom doswiadczenia = " + p1.getRider().getExperienceLevel());
			p1.getRider().setExperienceLevel(2);
			System.out.println(p1.toString() + ": poziom doswiadczenia = " + p1.getRider().getExperienceLevel());
		} catch (Exception e){
			System.out.println(e);
		}

		System.out.println(line);

		//Tworzenie przykladowych grup
		Group g1 = new Group("Blakitna");
		Group g2 = new Group("Szmaragdowa");

		//2. Ograniczenie {unique} - nazwa grupy musi byc unikalna
		try{
			Group g3 = new Group("Blakitna");
		} catch(NotUniqueValueException e){
			System.out.println("Group g3: Nazwa grupy nie jest unikalna!");
		}

		System.out.println(line);

		//Asocjacja grupa - czlonek grupy i asocjacja grupa - lider grupy
		p1.getRider().addToGroup(g1);
		p4.getRider().addToGroup(g1);
		g2.addRider(p4.getRider());

		try{
			g1.setLeader(p4.getRider());
		} catch(Exception e){
			e.printStackTrace();
		}

		//3. Ograniczenie {subset} - lider grupy musi nalezec do tej grupy
		try{
			g1.setLeader(p5.getRider());
		} catch(Exception e){
			System.out.println(e);
		}

		System.out.println(line);

		//Asocjacja jezdziec - konie, na ktorych jezdzic
		p1.getRider().addHorse(h2);
		p1.getRider().addHorse(h1);
		p4.getRider().addHorse(h3);
		p5.getRider().addHorse(h1);

		//4. Ograniczenie {ordered} - lista koni w kolejnosci dodania {h2, h1 -> Broadway, Hirsa}
		System.out.println(p1.toString());
		for(Horse h : p1.getRider().getHorsesRidden())
			System.out.println(h.getName());

		System.out.println(line);

		//Asocjacja z atrybutem trener - grupa
		g1.addCoach(p2.getCoach());
		p3.getCoach().addGroup(g2);

		//5. Ograniczenie {bag} - wiele powiazan tego samego trenera z ta sama grupa w innych terminach
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, 8);
		g2.addCoach(p3.getCoach(), c);

		//6. ograniczenie {xor} - osoba jest albo jezdecem, albo trenerem - Enum PersonType
		try{
			Person p6 = new Person("97041223498", "Cho", "Kyuhyun", PersonType.RIDER_COACH);
		} catch(WrongPersonTypeException e){
			System.out.println("Person p6: Osoba moze byc lub riderom, lub trenerom!");
		}

		System.out.println(line);

		//7. Ograniczenie atrybutu wlasne - pesel musi miec dlugosc 11 znakow
		try{
			Person p7 = new Person("94033187", "Cho", "Kyuhyun", PersonType.RIDER);
		} catch(WrongFormatException e){
			System.out.println("Person p7: Zly format peselu!");
		}
	}
}
