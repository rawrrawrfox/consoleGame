




public class ExitGameAction extends Action {
	
	public boolean perform(String action, Entity source) throws Exception {
		// expected pattern: ^\s*(exit|quit)\s*(game)?\s*$
		this.source=source;
		String pattern =
			EZMatch.m_input_start+
			EZMatch.m_spaces+
			"(exit|quit)"+
			EZMatch.m_spaces+
			"(game)?"+
			EZMatch.m_spaces+
			EZMatch.m_input_end;
		
		groups = EZMatch.match(pattern,action);
			
		if(groups!=null){
			actionResponse();
			complete();
			return true;
		}
		
		complete();
		return false;
	}
	
	public void actionResponse() throws Exception {
		System.exit(0);
	}
	
}

