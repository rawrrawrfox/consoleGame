

public class Player extends LivingEntity {
	
	

	protected void init() {
		super.init();
		
		name = "Player";
		type = "Player";
		age = 20;
		height = 1.7653f;
		
		updateSelfKnowledge();
	}
	
}

