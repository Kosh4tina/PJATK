import Functions.CarFuel;

public class SpeedCar extends Car {
	public SpeedCar(String name){
		super(name);
	}

	public SpeedCar(String name, CarFuel fuel) {
		super(name, fuel);
	}

	//Metoda wolana polimorficznie
	public void repair(){
		System.out.println("Naprawa auto dla trasy " + getName() + ".");
	}
}
