
import java.util.*;


public class Entity {
	
	ArrayList<Action> entityActions;
	Location currentLocation;
	String name;
	String type;
	
	private boolean markedForRemoval;
	
	public Entity(){
		init();
	}
	
	protected void init() {
		System.out.println("Entity initializing "+this);
		entityActions = new ArrayList<Action>();
		currentLocation = null;
		name = "?";
		type = "?";
		markedForRemoval=false;
	}
	
	public Location getLocation(){
		return currentLocation;
	}
	public void setLocation(Location l){
		currentLocation=l;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String n){
		name=n;
	}
	
	public String getType(){
		return type;
	}
	public void setType(String t){
		type=t;
	}
	
	public String getIfKnown(String attribute, String valueAssumedIfUnknown){
		LocalPlayer localPlayer = Main.g.getLocalPlayer();
		String valueKnowledge = localPlayer.getMemoryAbout(this,attribute);
		return (
			valueKnowledge==null?
			valueAssumedIfUnknown:
			valueKnowledge
		);
	}
	
	public String getNameIfKnown(){
		return getIfKnown("name", this.getType());
	}
	
	public ArrayList<Action> getActions(){
		return entityActions;
	}
	
	public void addAction(Action a){
		a.setParent(this);
		entityActions.add(a);
	}
	
	
	public void Remove(){
		markedForRemoval=true;
	}
	
	public boolean isMarkedForRemoval(){
		return markedForRemoval;
	}
	
}

