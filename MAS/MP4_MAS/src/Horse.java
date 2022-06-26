public class Horse {
	private int id;
	private String name;
	
	private static int counter = 0;
	
	public Horse(String name){
		counter++;
		setId(counter);
		setName(name);
	}

	//Gettery i settery atrybutow
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static int getNumber(){
		return counter;
	}
}
