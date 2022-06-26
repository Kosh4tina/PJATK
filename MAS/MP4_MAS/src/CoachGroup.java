import java.util.*;

public class CoachGroup {
	private Coach coach;
	private Group group;
	private Calendar from;
	private Calendar to;
	
	private static List<CoachGroup> coachGroups = new ArrayList<>();

	public CoachGroup(Coach coach, Group group, Calendar to){
		setCoach(coach);
		setGroup(group);
		setFrom(Calendar.getInstance());
		setTo(to);
		addCoachGroup(this);
	}
	public CoachGroup(Coach coach, Group group){
		setCoach(coach);
		setGroup(group);
		setFrom(Calendar.getInstance());
		setTo(null);
		addCoachGroup(this);
	}

	public Coach getCoach() {
		return coach;
	}
	public void setCoach(Coach coach) {
		this.coach = coach;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public Calendar getFrom() {
		return from;
	}
	public String getFromDateString(){
		return String.format("%1$te.%1$tm.%1$tY", this.from);
	}
	private void setFrom(Calendar from) {
		this.from = Calendar.getInstance();
		this.from.clear();
		this.from.set(from.get(Calendar.YEAR), from.get(Calendar.MONTH), from.get(Calendar.DAY_OF_MONTH));
	}
	public Calendar getTo() {
		return to;
	}
	public String getToDateString(){
		return String.format("%1$te.%1$tm.%1$tY", this.to);
	}
	public void setTo(Calendar to) throws RuntimeException{
		//Porownaj z data from
		if(to == null){
			this.to = null;
		}
		//Rzuc wyjatek, jezeli data zakonczenia jest wczesniejsza niz data rozpoczecia
		else if(to.compareTo(this.from) < 0)
			throw new RuntimeException("Data zakonczenia jest wczesniejsza niz data rozpoczecia.");
		else{
			this.to = Calendar.getInstance();
			this.to.clear();
			this.to.set(to.get(Calendar.YEAR), to.get(Calendar.MONTH), to.get(Calendar.DAY_OF_MONTH));
		}
	}
	
	//Obsluga ekstensji
	private static void addCoachGroup(CoachGroup cg){
		coachGroups.add(cg);
	}
	public static boolean removeCoach(Coach coach){
		if(areThereCoachGroups(coach)){
			for(CoachGroup cg : getCoachGroups(coach)){
				coachGroups.remove(cg);
			}
			return true;
		}
		return false;
			
	}
	public static boolean removeGroup(Group group){
		if(areThereCoachGroups(group)){
			for(CoachGroup cg : getCoachGroups(group)){
				coachGroups.remove(cg);
			}
			return true;
		}
		return false;
			
	}
	public static boolean areThereCoachGroups(Coach coach){
		for(CoachGroup cg : coachGroups){
			if(cg.getCoach() == coach)
				return true;
		}
		return false;
	}
	public static boolean areThereCoachGroups(Group group){
		for(CoachGroup cg : coachGroups){
			if(cg.getGroup() == group)
				return true;
		}
		return false;
	}
	public static Vector<CoachGroup> getCoachGroups(Coach coach){
		Vector<CoachGroup> trainedByCoach = new Vector<>();
		for(CoachGroup cg : coachGroups){
			if(cg.getCoach() == coach)
				trainedByCoach.add(cg);
		}
		return trainedByCoach;
	}
	public static Vector<CoachGroup> getCoachGroups(Group group){
		Vector<CoachGroup> whoTrained = new Vector<>();
		for(CoachGroup cg : coachGroups){
			if(cg.getGroup() == group)
				whoTrained.add(cg);
		}
		return whoTrained;
	}
}
