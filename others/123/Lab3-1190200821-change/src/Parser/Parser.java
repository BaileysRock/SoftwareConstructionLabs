package Parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	
	/**
	 * 检测总体格式是否符合要求
	 * @param S
	 * @return
	 */
	public boolean checkParttenCorrect(String S) {
		//检查总体的格式是否符合报告要求
		Pattern pattern1 = Pattern.compile("Employee\\{\s+[\n　([A-Za-z]+)\\{([a-zA-Z\s]+),(\\d{3}\\-\\d{4}\\-"
				+ "\\d{4})\\}]+\n\\}"
				+ "\nPeriod\\{(\\d{4}\\-\\d{2}\\-\\d{2}),(\\d{4}\\-\\d{2}\\-\\d{2})\\}"
				+ "\nRoster\\{\s+[\n　([A-Za-z]+)\\{(\\d{4}\\-\\d{2}\\-\\d{2}),(\\d{4}\\-\\d{2}\\-\\d{2})]+\n\\}\n");
		
        Pattern pattern2 = Pattern.compile("Period\\{\\d{4}\\-\\d{2}\\-\\d{2},\\d{4}\\-\\d{2}\\-\\d{2}\\}"
	    		+ "\nEmployee\\{[\n　[A-Za-z]+\\{[A-Za-z\s]+,\\d{3}\\-\\d{4}\\-\\d{4}\\}]+\n\\}"
	    		+ "+\nRoster\\{\s+[\n　[A-Za-z]+\\{\\d{4}\\-\\d{2}\\-\\d{2},\\d{4}\\-\\d{2}\\-\\d{2}\\}]+\n\\}\n");
	   		
		    
	        Matcher mymacher1 = pattern1.matcher(S);
	   		Matcher mymatcher2 = pattern2.matcher(S);
	    	if(mymacher1.matches()||mymatcher2.matches())
	    		return true;
	   	return false;

	}
	
	 /**
	  * 得到一个字符串后面的所有字符串
	  * @param name 前面的字符串
	  * @param S 整个字符串
	  * @return 整个字符串除去前面字符串后的字符串
	  */
	 public String getAllinformation(String name,String S) {
			Pattern pattern = Pattern.compile("(?<="+name+").+");
			Matcher mc = pattern.matcher(S);
			while(mc.find())
				return mc.group();
			return "";
		}

}
