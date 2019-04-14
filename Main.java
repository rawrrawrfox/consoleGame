
public class Main {
	public static Game g = null;
	public static void main(String[]args) throws Exception {
		g = new Game();
		while(true){
			g.doTurn();
		}
	}
}