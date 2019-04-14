


public class LocalPlayer extends Player {
	
	Game gameRef = null;
	
	public LocalPlayer(Game g){
		super();
		gameRef = g;
	}
	
	protected void init() {
		super.init();
	}
	
	public void doTurn() throws Exception {
		
		boolean actionSuccess = false;
		while(!actionSuccess){
			String command = Input.readLine();
			actionSuccess = gameRef.processAction(this,command);
			if(!actionSuccess){
				System.out.println("Can't do this.");
			}
		}
		
	}
}
