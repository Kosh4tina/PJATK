import functions.PartWithoutWholeException;
import java.util.Vector;
import java.util.Set;
import java.util.HashSet;

public class Rider {
	private Person person;
	private Vector<Horse> horsesRidden;
	private int experienceLevel;
	
	private Set<Group> groupsMember; //asocjacja grupa - czlonek grupy
	private Set<Group> groupLeader; //ograniczenie {subset}
	
	public Rider(Person person) throws PartWithoutWholeException{
		if(person == null)
			throw new PartWithoutWholeException();
		this.person = person;
		this.experienceLevel = 0;
		this.horsesRidden = new Vector<>();
		this.groupsMember = new HashSet<>();
		this.groupLeader = new HashSet<>();
	}
	
	public Person getPerson(){
		return this.person;
	}

	// 1. Ograniczenie dynamiczne atrybutu
	public int getExperienceLevel() {
		return this.experienceLevel;
	}

	public void setExperienceLevel(int experienceLevel) throws Exception{
		if(experienceLevel < this.experienceLevel)
			throw new Exception("Poziom doswiadczenia nie moze spadac.");
		this.experienceLevel = experienceLevel;
	}

	// 4. Ograniczenie {ordered}
	public Vector<Horse> getHorsesRidden(){
		return this.horsesRidden;
	}
	public void addHorse(Horse horse){
		horsesRidden.add(horse);
	}
	
	// 3. Implementacja asocjacji i ograniczenia {subset}
	private boolean findGroup(Group group){
		if(group == null)
			return false;
		return this.groupsMember.contains(group);
	}
	public void addToGroup(Group group){
		if(!findGroup(group)){
			this.groupsMember.add(group);
			group.addRider(this);
		}
	}
	public void removeFromGroup(Group group) {
		if(findGroup(group)){
			this.groupsMember.remove(group);
			group.removeRider(this);
		}
	}
	
	private boolean findGroupLeader(Group group){
		if(group == null)
			return false;
		return this.groupLeader.contains(group);
	}
	public void makeGroupLeader(Group group) throws Exception{
		if(findGroup(group) && !findGroupLeader(group)){
			this.groupLeader.add(group);
			group.setLeader(this);
		}
		else
			throw new Exception("Powiazanie nadrzedne nie istnieje - jezdziec nie jest czlonkiem grupy.");
	}
	public void removeAsLeader(Group group){
		if(findGroupLeader(group)){
			this.groupLeader.remove(group);
			group.removeLeader();
		}
	}
	
	public String toString(){
		return "Jezdziec " + this.getPerson().getName()
				+ " " + this.getPerson().getSurname();
	}
}
