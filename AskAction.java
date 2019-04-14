







import java.util.regex.*;

public class AskAction extends Action {
	
	public boolean perform(String action, Entity source) throws Exception {
		// expected pattern: ^\s*ask\s+(Fox Girl)\s+(for|about|regarding|what is|what are)(\s+(his|her|its|your|my|a))?\s+([\w\d-+ ]+)\s*$
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
			"ask"+
			EZMatch.m_spaces_required+
			"(%s|%s)?"+
			EZMatch.m_spaces_required+
			"(for|about|regarding|what is|what are)"+
			"("+
			EZMatch.m_spaces_required+
			"(his|her|its|your|my|a))?"+
			EZMatch.m_spaces_required+
			"("+EZMatch.m_simple_text+")?"+
			EZMatch.m_spaces+
			EZMatch.m_input_end;
		
		pattern = String.format(
			pattern,
			Pattern.quote(parentEnt.getName()),
			Pattern.quote(parentEnt.getType())
		);
		
		groups = EZMatch.match(pattern,action);
			
		if(groups!=null && source!=parent){
			actionResponse();
			complete();
			return true;
		}
		
		complete();
		return false;
	}
	
	public void actionResponse() throws Exception {
		
		String refName = groups[1];
		String specificQuestion = groups[2];
		String pronounOrOtherRef = groups[4];
		String property = groups[5];
		
		LivingEntity LE_parent = (LivingEntity)parent;
		
		if(
			specificQuestion.equals("for") ||
			specificQuestion.equals("about") ||
			specificQuestion.equals("regarding") ||
			specificQuestion.equals("what is") 
		) {
			if(
				pronounOrOtherRef.equals("his") ||
				pronounOrOtherRef.equals("her") ||
				pronounOrOtherRef.equals("its") ||
				pronounOrOtherRef.equals("your")
			){
				if(property.equals("name")){
					respondKnowledge("name",LE_parent.getName());
				}else if(property.equals("age")){
					respondKnowledge("age",""+LE_parent.getAge());
				}else{
					String val = LE_parent.getMemoryAbout(LE_parent,property);
					if(val!=null){
						respondKnowledge(property,val);
					}else{
						dontKnow();
					}
				}
			}else if(
				pronounOrOtherRef.equals("my")
			){
				String val = LE_parent.getMemoryAbout(source,property);
				if(val==null){
					dontKnow();
				}else{
					respondKnowledge(property,val);
				}
			}else if(
				pronounOrOtherRef.equals("a")
			){
				String val = LE_parent.getMemoryAbout(LE_parent,property+"_definition");
				if(val==null){
					dontKnow();
				}else{
					respondKnowledge(property,val);
				}
			}
		}else if (
			specificQuestion.equals("what are")
		){
			if(property.equals("those")){
				if(parent instanceof Fox_Girl){
					property = "tails";
					Fox_Girl FG_parent = (Fox_Girl)parent;
					LivingEntity LE_source = (LivingEntity)source;
					Output.println(FG_parent.getNameIfKnown()+" answers \""+FG_parent.getTailCount()+" "+property+"\".");
					String oldKnowledge = LE_source.getMemoryAbout(FG_parent,property);
					String value = ""+FG_parent.getTailCount();
					boolean isKnowledgeNew = (oldKnowledge==null || (!value.equals(oldKnowledge)));
					LE_source.setMemoryAbout((Entity)parent,property,value);
					
					if(isKnowledgeNew){
						if(LE_source==Main.g.getLocalPlayer()){
							Output.println("You've learned something new.");
						}
					}
		
				}else{
					laugh();
				}
			}else{
				dontKnow();
			}
		}else{
			dontUnderstand();
		}
		
	}
	
	protected void dontKnow(){
		if(parent==Main.g.getLocalPlayer()){
			Output.println("You don't know.");
		}else{
			Output.println(((Entity)parent).getNameIfKnown()+" doesn't know.");
		}
	}
	
	protected void dontUnderstand(){
		if(parent==Main.g.getLocalPlayer()){
			Output.println("You don't understand the question.");
		}else{
			Output.println(((Entity)parent).getNameIfKnown()+" doesn't understand the question.");
		}
	}
	
	protected void respondKnowledge(String property,String value){
		boolean isFirstPerson=(parent==Main.g.getLocalPlayer());
		
		if(isFirstPerson){
			Output.println("You answer \""+value+"\".");
		}else{
			Output.println(((Entity)parent).getNameIfKnown()+" answers \""+value+"\".");
		}
		
		LivingEntity LE_source = ((LivingEntity)source);
		String oldKnowledge = LE_source.getMemoryAbout(((Entity)parent),property);
		boolean isKnowledgeNew = (oldKnowledge==null || (!value.equals(oldKnowledge)));
		LE_source.setMemoryAbout((Entity)parent,property,value);
		
		if(isKnowledgeNew){
			if(LE_source==Main.g.getLocalPlayer()){
				Output.println("You've learned something new.");
			}
		}
	}
	
	protected void laugh(){
		if(parent==Main.g.getLocalPlayer()){
			Output.println("You laugh.");
		}else{
			Output.println(((Entity)parent).getNameIfKnown()+" laughs.");
		}
	}
	
}

