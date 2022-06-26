import Functions.CarFuel;
import Functions.IDrift;
import Functions.OsobaType;

public class Main {
	public static void main(String[] args) {

		/**Dziedziczenie overlapping i dynamic
		Osoba <---- Driver
		Person <---- Mechanic
		Implementacja z wykorzystaniem kompozycji
		**/

		Osoba p1 = new Osoba("Max", "Boston", OsobaType.DRIVER);
		Osoba p2 = new Osoba("Arthur", "Doil", OsobaType.DRIVER_MECHANIC); //overlapping
		p1.becomeMechanic(); //dynamic
		p2.stopBeingMechanic(); //dynamic
		Osoba p3 = new Osoba("Demi", "Boorbon", OsobaType.MECHANIC);

		/** Dziedziczenie wieloaspektowe:
		Car <--Fuel-- {Petrol, Diesel}
		Car <--sportCar-- {speedCar, driftCar}
		Dziedziczenie wielokrotne:
		driftCar <---- SportCar (implementacja przy pomocy interfejsu IDrift)
		speedHorse <---- SportCar (mechanizm dziedziczenia jezyka Java)
		Klasa abstrakcyjna: Fuel
		Polimorficzne wolanie metody:
		repair() w klasach Car, DriftCar, SpeedCar, SportCar
		**/

		Car h1 = new Car("Melkus", CarFuel.DIESEL);
		IDrift h2 = new DriftCar("Pagani", CarFuel.DIESEL);
		Car h3 = new SpeedCar("Mosler", CarFuel.PETROL);
		IDrift h4 = new SportCar("Maserati", CarFuel.PETROL);

		h1.repair();
		h2.repair();
		h3.repair();
		h4.repair();

		p1.addCarToDrive(h1);
		p1.addCarToRepair(h3);
		p1.driveCar(h1);
		p1.repairCar(h3);
	}
}
