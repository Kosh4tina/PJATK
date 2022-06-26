import Functions.CarFuel;
import Functions.IDrift;

public class SportCar extends SpeedCar implements IDrift {
	public SportCar(String name){
		super(name);
	}

	public SportCar(String name, CarFuel fuel) {
		super(name, fuel);
	}

	//Metoda wolana polimorficznie
	public void repair(){
		System.out.println("Naprawa sportowego auto " + getName() + ".");
	}
}
