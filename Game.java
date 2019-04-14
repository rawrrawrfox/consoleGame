
import java.util.*;

public class Game {
	
	int turns = 0;
	ArrayList<Action> gameActions = new ArrayList<Action>();
	ArrayList<Location> locations = new ArrayList<Location>();
	LocalPlayer localPlayer = new LocalPlayer(this);
	
	public LocalPlayer getLocalPlayer(){
		return localPlayer;
	}
	
	public ArrayList<Action> getActions(){
		return gameActions;
	}
	
	
	public Game(){
		init();
	}
	
	
	protected void init(){
		gameActions.add(new ExitGameAction());
		Location the_beginning=new Location("The Beginning");
		locations.add(the_beginning);
		the_beginning.addEnt(localPlayer);
		the_beginning.addEnt(new Fox_Girl());
	}
	
	public boolean processAction(LivingEntity fromEnt, String action) throws Exception {
		ArrayList<Action> availableActions = new ArrayList<Action>();
		availableActions.addAll(this.getActions());   //game actions
		
		if(fromEnt.isAlive()){
			availableActions.addAll(fromEnt.getActions());//current player actions
			availableActions.addAll(fromEnt.getLocation().getActions()); // current location actions
			for(Entity e : fromEnt.getLocation().getEnts()){  // rest of entities' actions
				if(e==fromEnt){continue;}
				ArrayList<Action> tmp = e.getActions();
				availableActions.addAll(tmp);
			}
		}
		
		for(Action a : availableActions){
			boolean done = a.perform(action,fromEnt);
			if(done){
				return true;
			}
		}
		return false;
	}
	
	public void doTurn() throws Exception {

		
		for(Location l : locations){
			System.out.println("=[ Turn "+turns+" ]=");
			Iterator<Entity> it = l.getEnts().iterator();
			while(it.hasNext()){
				Entity ent = it.next();
				
				if(ent instanceof LivingEntity){
					LivingEntity le = ((LivingEntity)ent);
					if(le.getStunnedTurns()<=0){
						le.doTurn();
					}else{
						/*if(le==getLocalPlayer()){ // first person
							Output.println(String.format("You are stunned for %d turns.",le.getStunnedTurns()));
						}else{
							Output.println(String.format(le.getNameIfKnown()+" is stunned for %d turns.",le.getStunnedTurns()));
						}*/
						le.getStunnedAction().actionResponse();
						le.setStunnedTurns(le.getStunnedTurns()-1);
						if(le.getStunnedTurns()<=0){
							le.getStunnedAction().complete();
							le.setStunnedAction(null);
						}
					}
				}
				
				if(ent.isMarkedForRemoval()){
					it.remove();
				}
			}
		}
		turns++;
	}
	
}
