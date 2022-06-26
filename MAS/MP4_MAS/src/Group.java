import functions.NotUniqueValueException;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Vector;
import java.util.Calendar;

public class Group {
	private int id;
	private String name; //ograniczenie {unique}
	private Set<Rider> members;
	private Rider leader;
	
	private static int counter = 0;
	private static Map<String, Group> groups = new HashMap<>();
	
	public Group(String name) throws RuntimeException{
		counter++;
		setId(counter);
		setName(name);
		this.members = new HashSet<>();
		this.leader = null;
		addGroup(this);
	}

	public int getId() {
		return id;
	}
	private void setId(int id) {
		this.id = id;
	}

	// 2. Ograniczenie {unique}
	public void setName(String name) throws NotUniqueValueException{
		if(findGroup(name))
			throw new NotUniqueValueException();
		this.name = name;
	}

	public static boolean findGroup(Group group){
		return groups.containsValue(group);
	}
	public static boolean findGroup(String name){
		return groups.containsKey(name);
	}
	
	//Obsluga asocjacji grupa-czlonek grupy
	private boolean findRider(Rider rider){
		return this.members.contains(rider);
	}
	public void addRider(Rider rider){
		if(!findRider(rider)){
			this.members.add(rider);
			rider.addToGroup(this);
		}
	}
	public void removeRider(Rider rider){
		if(findRider(rider)){
			this.members.remove(rider);
			rider.removeFromGroup(this);
		}
	}
	
	//3. Ograniczenie {subset}
	public void setLeader(Rider leader) throws Exception {
		if(leader == null)
			removeLeader();
		else if(this.leader == leader){}
		else if(findRider(leader)){
			if(this.leader != null)
				this.leader.removeAsLeader(this);
			this.leader = leader;
			leader.makeGroupLeader(this);
			System.out.println(leader + " jest liderom " + name);
		}
		else{
			throw new Exception("Powiazanie nadrzedne nie istnieje - jezdziec nie jest czecia grupy");
		}
	}
	public void removeLeader(){
		this.leader = null;
	}

	private static void addGroup(Group group){
		groups.put(group.name, group);
	}
	public static void removeGroup(Group group) throws Exception {
		if(findGroup(group))
			groups.remove(group.name);
		else
			throw new Exception("Ta grupa nie nalezy do ekstensji");
	}
	public static void removeGroup(String name) throws Exception {
		if(findGroup(name))
			groups.remove(name);
		else
			throw new Exception("Ta grupa nie nalezy do ekstensji");
	}


	//Asocjacja z atrybutem Coach-Group realizowana za pomoca klasy posredniej CoachGroup
	public Vector<CoachGroup> getCoachPairs(){
		return CoachGroup.getCoachGroups(this);
	}
	public Set<Coach> getCoaches(){
		Set<Coach> coaches = new HashSet<>();
		for(CoachGroup cg : getCoachPairs())
			coaches.add(cg.getCoach());
		return coaches;
	}

	public void addCoach(Coach coach){
		new CoachGroup(coach, this);
	}
	public void addCoach(Coach coach, Calendar to){
		new CoachGroup(coach, this, to);
	}
}
