




public class LookAroundAction extends Action {
	
	public boolean perform(String action, Entity source) throws Exception {
		// expected pattern: ^\s*look\s+around\s*$
		this.source=source;
		String pattern =
			EZMatch.m_input_start+
			EZMatch.m_spaces+
			"look"+
			EZMatch.m_spaces_required+
			"around"+
			EZMatch.m_spaces+
			EZMatch.m_input_end;
		
		groups = EZMatch.match(pattern,action);
			
		if(groups!=null && source==parent){
			actionResponse();
			complete();
			return true;
		}
		
		complete();
		return false;
	}
	
	public void actionResponse() throws Exception {
		if(source==Main.g.getLocalPlayer()){
			Location curLoc = source.getLocation();
			Output.println("You see:");
			int count=1;
			for(Entity e : curLoc.getEnts()){
				Output.println(count+". "+e.getNameIfKnown());
				count++;
			}
		}else{
			Output.println(source.getNameIfKnown()+" is looking around.");
		}
	}
	
}

