public class Diesel extends Fuel {
	private int volume;
	private int consumption;

	public Diesel(){
		this.volume = 0;
	}
	
	public int getVolume(){
		return this.volume;
	}

	@Override
	void CountingСonsumption() {
		this.consumption = this.volume / 10;
	}

	public int getConsumption(){
		return this.consumption;
	}
}
