import Functions.WithoutTheWhole;
import java.util.Vector;

public class Mechanic {
	private final Vector<Car> car;
	private final Osoba osoba;
	
	public Mechanic(Osoba osoba) {
		if(osoba == null)
			throw new WithoutTheWhole();
		this.osoba = osoba;
		this.car = new Vector<>();
	}

	public Vector<Car> getCar(){
		return this.car;
	}
	public void addCar(Car car){
		this.car.add(car);
	}
	public void removeCar(Car car){
		this.car.remove(car);
	}
	public boolean repairsCar(Car car){
		return this.car.contains(car);
	}
		
	public boolean repairCar(Car car){
		if(car == null){
			System.out.println("Nie podano auto.");
			return false;
		}
		StringBuffer sb = new StringBuffer("Mechanic ");
		sb.append(this.osoba.getName()).append(" ").append(this.osoba.getSurname());
		if(this.repairsCar(car))
			sb.append(" prowadzi naprawe auto ").append(car.getName()).append(".");
		else
			sb.append(" nie prowadzi naprawe ").append(car.getName()).append(".");
	
		System.out.println(sb);
		return true;
	}
}
