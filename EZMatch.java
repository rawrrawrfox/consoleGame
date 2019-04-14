

import java.util.regex.*;


public class EZMatch {
	
	public static String m_spaces = "\\s*";
	public static String m_spaces_required = "\\s+";
	public static String m_not_spaces = "\\S*";
	public static String m_input_start = "^";
	public static String m_input_end = "$";
	public static String m_number = "[-+]?\\d+";
	public static String m_simple_text = "[\\w\\d-+ ]+";
	
	
	public static String[] match(String pattern,String input){
		Pattern p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(input);
		boolean b = m.matches();
		if(b){
			int c=m.groupCount()+1;
			String[] out = new String[c];
			for(int k=0;k<c;k++){
				
				out[k]=m.group(k);
			}
			return out;
		}else{
			return null;
		}
	}
}


