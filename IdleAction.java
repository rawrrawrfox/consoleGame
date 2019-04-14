
public class IdleAction extends Action {
	
	public boolean perform(String action, Entity source) throws Exception {
		// expected pattern: ^\s*(wait|idle)(\s*for)?(\s*([-+]?\d+)\s*turns)?\s*$
		this.source=source;
		String pattern =
			EZMatch.m_input_start+
			EZMatch.m_spaces+
			"(wait|idle)("+
			EZMatch.m_spaces+
			"for)?("+
			EZMatch.m_spaces+
			"("+EZMatch.m_number+")"+
			EZMatch.m_spaces+
			"turns)?"+
			EZMatch.m_spaces+
			EZMatch.m_input_end;
		
		groups = EZMatch.match(pattern,action);
		
		
		
		if(source==parent && groups!=null){
			if(groups[4]!=null){
				int turns = Integer.parseInt(groups[4])-1;
				if(turns>0){
					LivingEntity LE_souce = (LivingEntity)source;
					LE_souce.setStunnedTurns(turns);
					LE_souce.setStunnedAction(this);
				}
			}
			actionResponse();
			return true;
		}
		
		complete();
		return false;
	}
	
	public static String[] idleResponsesFirstPerson = new String[]{
		"You think about life.",
		"You stand around doing nothing useful.",
		"You are enjoying the fresh air."
	};
	
	public static String[] idleResponsesThirdPerson = new String[]{
		"%s thinks about life.",
		"%s stands around doing nothing useful.",
		"%s is enjoying the fresh air."
	};
	
	public void actionResponse() throws Exception {
		if(source==Main.g.getLocalPlayer()){ // first person messages
			Output.println(
				idleResponsesFirstPerson[Input.randomInt(0,idleResponsesFirstPerson.length-1)]
			);
		} else { // third person messages
			Output.println(
				String.format(
					idleResponsesThirdPerson[Input.randomInt(0,idleResponsesThirdPerson.length-1)],
					((LivingEntity)source).getNameIfKnown()
				)
			);
		}
	}
	
}

