public class Petrol extends Fuel {
	private int volume;
	private int consumption;
	
	public Petrol(){
		this.volume = 0;
	}
	
	public int getVolume(){
		return this.volume;
	}

	@Override
	void CountingСonsumption() {
		consumption = this.volume / 25;
	}

	public int getConsumption(){
		return this.consumption;
	}
}
