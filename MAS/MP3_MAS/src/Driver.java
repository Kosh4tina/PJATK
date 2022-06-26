import Functions.WithoutTheWhole;
import java.util.Vector;

public class Driver {
	private final Vector<Car> car;
	private final Osoba osoba;
	
	public Driver(Osoba osoba) throws WithoutTheWhole {
		if(osoba == null){
			throw new WithoutTheWhole();
		}
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
	public boolean drivesCar(Car car){
		return this.car.contains(car);
	}
	
	public boolean driveCar(Car car){
		if(car == null){
			System.out.println("Nie podano auto.");
			return false;
		}
		StringBuffer sb = new StringBuffer("Driver ");
		sb.append(this.osoba.getName()).append(" ").append(this.osoba.getSurname());
		if(this.drivesCar(car))
			sb.append(" odbywa jazde na auto ").append(car.getName()).append(".");
		else
			sb.append(" nie jest jednym z driverow tego auto ").append(car.getName()).append(".");
	
		System.out.println(sb);
		return true;
	}
}
