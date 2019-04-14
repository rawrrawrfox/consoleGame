
public class Fox_Girl extends NPC {
	
	int tail_count;
	
	
	
	protected void init() {
		super.init();
		name = "Tamamo";
		type = "Fox Girl";
		age = 2682;
		height = 1.3208f;
		tail_count = 9;
		
		updateSelfKnowledge();
		
		addAction(new TouchFluffyTailAction());
	}
	
	protected void updateSelfKnowledge(){
		super.updateSelfKnowledge();
		setMemoryAbout(this,"tails",tail_count+"");
	}
	
	
	public void doTurn() {
		System.out.println(getNameIfKnown()+" is waiting around not doing much.");
	}
	
	public int getTailCount(){
		return tail_count;
	}
	
}