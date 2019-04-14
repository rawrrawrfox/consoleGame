
import java.util.*;


public class Location extends Entity {
	
	ArrayList<Action> locationActions = new ArrayList<Action>();
	ArrayList<Entity> ents = new ArrayList<Entity>();
	
	public Location(String n) {
		super();
		name = n;
		type = "Location";
	}
	
	
	public ArrayList<Action> getActions(){
		return locationActions;
	}
	
	public ArrayList<Entity> getEnts(){
		return ents;
	}
	
	public void addEnt(Entity e){
		Location curLoc = e.getLocation();
		if(curLoc!=null){
			curLoc.removeEnt(e);
		}
		e.setLocation(this);
		ents.add(e);
	}
	
	public void removeEnt(Entity e){
		ents.remove(e);
		e.setLocation(null);
	}
}

