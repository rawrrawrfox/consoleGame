



public class Action {
	
	Object parent=null;
	String[] groups = null;
	Entity source = null;
	
	public void setParent(Object o){
		parent = o;
	}
	
	public boolean perform(String action, Entity source) throws Exception {
		return false;
	}
	
	public void actionResponse() throws Exception {
		
	}
	
	public void complete() throws Exception {
		groups = null;
		source = null;
	}
}

