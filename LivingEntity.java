
import java.util.*;

public class LivingEntity extends Entity {
	
	float health;
	float maxhealth;
	
	int age;
	float height;
	int stunnedTurns;
	Action stunAction; // action responsible for skipping turns
	
	boolean alive;
	boolean undead;
	
	protected HashMap<Entity,Float> 					relationships;
	protected HashMap<Entity,HashMap<String,String>> 	memory;
	protected HashMap<String,Float> 					preferences;
	
	
	
	protected void init() {
		super.init();
		health = 100f;
		maxhealth = 100f;
		age = 0;
		height = 0;
		stunnedTurns = 0;
		stunAction = null; // action responsible for skipping turns
		alive = true;
		undead = false;
		relationships 	= new HashMap<Entity,Float>();
		memory 			= new HashMap<Entity,HashMap<String,String>>();
		preferences 	= new HashMap<String,Float>();
		
		updateSelfKnowledge();
		
		addAction(new IdleAction());
		addAction(new AskAction());
		addAction(new LookAroundAction());
	}
	
	protected void updateSelfKnowledge(){
		setMemoryAbout(this, "name", name);
		setMemoryAbout(this, "age", ""+age);
		setMemoryAbout(this, "height", ""+height);
		setMemoryAbout(this, "type", type);
	}
	
	public void setStunnedTurns(int a){
		stunnedTurns=a;
	}
	
	public int getStunnedTurns(){
		return stunnedTurns;
	}
	
	
	public void setStunnedAction(Action a){
		stunAction=a;
	}
	
	public Action getStunnedAction(){
		return stunAction;
	}
	
	
	
	public void setAge(int a){
		age=a;
	}
	
	public int getAge(){
		return age;
	}
	
	
	public String getAgeIfKnown(){
		return getIfKnown("age", "?");
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
	
	public float getRelationship(Entity e){
		if(relationships.containsKey(e)){
			float relationshipValue = relationships.get(e);
			return relationshipValue;
		}else{
			return 0.0f;
		}
	}
	
	public void setRelationship(Entity e, float v){
		float vfixed = (float)Math.min(Math.max(v,-1.0),1.0);
		relationships.put(e,vfixed);
	}
	
	public static String[] relationshipStatuses = new String[]{
		"Worst Enemy",
		"Hostile",
		"Hateful",
		"Malignant",
		"Unsociable",
		"Unfavorable",
		"On Bad Terms",
		"Neutral",
		"On Good Terms",
		"Favorable",
		"Friendly",
		"Loyal",
		"Intimate",
		"In Love",
		"Soul Mate"
	};
	
	public String getRelationshipDescription(Entity e){
		return relationshipStatuses[(int)(((getRelationship(e)+1.0f)/2.0f)*(float)relationshipStatuses.length)];
	}
	
	public String getMemoryAbout(Entity e, String attribute){
		if(memory.containsKey(e)){
			HashMap<String,String> tmp = memory.get(e);
			if(tmp!=null){
				if(tmp.containsKey(attribute)){
					String val = tmp.get(attribute);
					return val;
				}
			}
		}
		return null;
	}
	
	public void setMemoryAbout(Entity e, String attribute, String value){
		HashMap<String,String> memtmp = null;
		if(!memory.containsKey(e)){
			memtmp = new HashMap<String,String>();
			memory.put(e,memtmp);
		}else{
			memtmp = memory.get(e);
		}
		
		if(memtmp==null){
			memtmp = new HashMap<String,String>();
			memory.put(e,memtmp);
		}
		
		memtmp.put(attribute,value);
	}
	
	public void learn(String attribute, String value){
		setMemoryAbout(this, attribute, value);
	}
	
	public float getPreference(String type){
		if(preferences.containsKey(type)){
			float preferencesValue = preferences.get(type);
			return preferencesValue;
		}else{
			return 0.0f;
		}
	}
	
	public void setPreference(String type, float v){
		float vfixed = (float)Math.min(Math.max(v,-1.0),1.0);
		preferences.put(type,vfixed);
	}
	
	
	public boolean isAlive(){
		return alive;
	}
	
	public boolean isUndead(){
		return undead;
	}
	
	public void doTurn() throws Exception  {
		
	}
}



