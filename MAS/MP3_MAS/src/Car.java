import Functions.CarFuel;

public class Car {
	private static int count = 0;
	
	private final int id;
	private String name;
	private Fuel fuel;
	
	public Car(String name){
		count++;
		this.id = count;
		this.name = name;
	}
	public Car(String name, CarFuel fuel){
		count++;
		this.id = count;
		this.name = name;
		switch(fuel){
			case PETROL:
				this.fuel = new Petrol();
				break;
			case DIESEL:
				this.fuel = new Diesel();
				break;
		}
	}
	
	//Gettery i settery atrybutow
	public int getId(){
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	//Metoda wolana polimorficznie
	public void repair(){
		System.out.println("Ogolna naprawa auto " + getName() + ".");
	}


}
