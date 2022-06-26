import Functions.CarFuel;
import Functions.IDrift;

public class DriftCar extends Car implements IDrift {
	public DriftCar(String name){
		super(name);
	}

	public DriftCar(String name, CarFuel fuel) {
		super(name, fuel);
	}

	//Metoda wolana polimorficznie
	public void repair(){
		System.out.println("Naprawa auto dla driftu " + getName() + ".");
	}
}
