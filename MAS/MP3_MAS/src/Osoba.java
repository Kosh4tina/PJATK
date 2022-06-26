import Functions.OsobaType;
import java.util.Vector;

public class Osoba {
	private static int count = 0;

	private final int id;
	private String name;
	private String surname;
	
	//Overlapping i dynamic - realizacja za pomoca kompozycji
	private Driver driver;
	private Mechanic mechanic;
	
	public Osoba(String name, String surname, OsobaType type){
		count++;
		this.id = count;
		setName(name);
		setSurname(surname);
		if(type == OsobaType.DRIVER){
			this.driver = new Driver(this);
			this.mechanic = null;
		}
		else if(type == OsobaType.MECHANIC){
			this.driver = null;
			this.mechanic = new Mechanic(this);
		}
		else if(type == OsobaType.DRIVER_MECHANIC){
			this.driver = new Driver(this);
			this.mechanic = new Mechanic(this);
		}
		else{
			this.driver = null;
			this.mechanic = null;
		}
	}

	//Gettery i settery dla atrybutow
	public int getId() {
		return id;
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
	
	//Obsluga overlapping i dynamic
	public void becomeDriver(){
		if(!isDriver())
			this.driver = new Driver(this);
	}
	public void stopBeingDriver(){
		if(isDriver())
			this.driver = null;
	}
	public void becomeMechanic(){
		if(!isMechanic())
		this.mechanic = new Mechanic(this);
	}
	public void stopBeingMechanic(){
		if(isMechanic()){
			this.mechanic = null;
		}
	}
	public boolean isDriver(){
		return this.driver != null;
	}
	public boolean isMechanic(){
		return this.mechanic != null;
	}
	public void driveCar(Car car){
		if(isDriver())
			this.driver.driveCar(car);
	}
	public boolean addCarToDrive(Car car){
		if(isDriver()){
			this.driver.addCar(car);
			return true;
		}
		return false;
	}
	public Vector<Car> getCarToDrive(){
		if(isDriver())
			return this.driver.getCar();
		return null;
	}
	public void repairCar(Car car){
		if(isMechanic())
			this.mechanic.repairCar(car);
	}
	public boolean addCarToRepair(Car car){
		if(isMechanic()){
			this.mechanic.addCar(car);
			return true;
		}
		return false;
	}
	public Vector<Car> getCarToRepair(){
		if(isMechanic())
			return this.mechanic.getCar();
		return null;
	}
}
