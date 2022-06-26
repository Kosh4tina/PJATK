import functions.PartWithoutWholeException;
import java.util.Vector;
import java.util.Set;
import java.util.HashSet;
import java.util.Calendar;

public class Coach {
	private Person person;
		
	public Coach(Person person) throws PartWithoutWholeException {
		if(person == null)
			throw new PartWithoutWholeException();
		setPerson(person);
	}

	public Person getPerson() {
		return person;
	}

	private void setPerson(Person person) {
		this.person = person;
	}
	
	public Vector<CoachGroup> getGroupPairs(){
		return CoachGroup.getCoachGroups(this);
	}
	public Set<Group> getGroups(){
		Set<Group> groups = new HashSet<>();
		for(CoachGroup cg : getGroupPairs())
			groups.add(cg.getGroup());
		return groups;
	}
	public void addGroup(Group group){
		new CoachGroup(this, group);
	}
	public void addGroup(Group group, Calendar to){
		new CoachGroup(this, group, to);
	}
	
	public String toString(){
		return "Trener " + this.getPerson().getName() + " " + this.getPerson().getSurname();
	}
}
