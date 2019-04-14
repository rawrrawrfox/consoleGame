
import java.util.regex.*;

public class TouchFluffyTailAction extends Action {
	
	public boolean perform(String action, Entity source) throws Exception {
		
		// expected pattern: ^\s*touch(\s*(name|type)('s)?)?(\s*fluffy)?\s*(tail|tails)\s*$
		this.source=source;
		Entity parentEnt = null;
		
		try{
			parentEnt = (Entity)parent;
		}catch(Exception e){
			throw new Exception(this.getClass().getCanonicalName()+" can only be applied to a parent of type "+Entity.class.getCanonicalName()+
			" but instead, the parent was of type "+parent.getClass().getCanonicalName(), e); 
		}

		String pattern = 
			EZMatch.m_input_start+
			EZMatch.m_spaces+
			"touch"+
			"("+
			EZMatch.m_spaces+
			"(%s|%s)('s)?)?"+
			"("+
			EZMatch.m_spaces+
			"fluffy)?"+
			EZMatch.m_spaces+
			"(tail|tails)"+
			EZMatch.m_spaces+
			EZMatch.m_input_end;
		
		pattern = String.format(
			pattern,
			Pattern.quote(parentEnt.getName()),
			Pattern.quote(parentEnt.getType())
		);
		
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
		Entity parentEnt = null;
		try{
			parentEnt = (Entity)parent;
		}catch(Exception e){
			throw new Exception(this.getClass().getCanonicalName()+" can only be applied to a parent of type "+Entity.class.getCanonicalName()+
			" but instead, the parent was of type "+parent.getClass().getCanonicalName(), e); 
		}
		LocalPlayer localPlayer = Main.g.getLocalPlayer();
		
		if(source instanceof LivingEntity){
			LivingEntity actionSourceLent = (LivingEntity)source;
			
			System.out.println(
				(
					(actionSourceLent==localPlayer)?
					"You touch":
					actionSourceLent.getNameIfKnown()+" touches"
				)+" "+
				(
					(parentEnt==localPlayer)?
					"your":
					parentEnt.getNameIfKnown()+"'s"
				)+" fluffy tail."
			);
			
			if(parentEnt instanceof LivingEntity){
				LivingEntity actionTarget = (LivingEntity)parentEnt;
				actionTarget.setRelationship(actionSourceLent,(actionTarget.getRelationship(actionSourceLent)+0.02f)*1.02f);
				String relationshipDescription = actionTarget.getRelationshipDescription(actionSourceLent);
				if(!(relationshipDescription.equals(actionSourceLent.getMemoryAbout(actionTarget,"relationship")))){
					actionSourceLent.setMemoryAbout(actionTarget,"relationship",relationshipDescription);
					if(actionSourceLent==localPlayer){
						System.out.println("Your relationship status with "+actionTarget.getNameIfKnown()+" has changed to: "+relationshipDescription);
					}
				}
			}
		}
	}
	
}


