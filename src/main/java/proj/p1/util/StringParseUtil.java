package proj.p1.util;

public class StringParseUtil {
	public static boolean isInt(String strNum) {
		return strNum.matches("-*[0-9]+");
	}
	
	public static int parseInt(String strNum) {
		if (strNum == null) {
	        return 0;
	    }
	    try {
	        int num = Integer.parseInt(strNum);
	        return num;
	    } catch (NumberFormatException nfe) {
	        return 0;
	    }
	}
}
