
import java.util.*;


public class Input {
	public static String readLine() throws Exception {
		byte[] b = new byte[512];
		System.out.print("> ");
		int read=System.in.read(b);
		if(read==-1){
			return "";
		}
		return new String(Arrays.copyOf(b,read-1),"UTF-8");
	}
	
	static Random random = new Random();
	public static int randomInt(int min, int max){
		return random.nextInt(max-min+1)+min;
	}
}
