package Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ParserTest {
	/**
	 * 测试正则表达式格式，分对错两种；
	 * @throws IOException
	 */
	@Test
	public void checkTest() throws IOException {
		@SuppressWarnings("resource")
		BufferedReader bf=new BufferedReader(new FileReader("src/txt/test1.txt"));
		String fileentry="";
		String fileoneline;
		Parser tempparser=new Parser();
		while((fileoneline=bf.readLine())!=null) { 
			if(fileoneline.equals("")) {  
				continue;
			}
			else {
				fileentry=fileentry+fileoneline+"\n";
			}
		}
		assertEquals(true, tempparser.checkParttenCorrect(fileentry));
		assertEquals(false, tempparser.checkParttenCorrect("Employee:"));
	}
	/**
	 * 测试得到信息字符串;分对错两种
	 * @throws IOException
	 */
	@Test
	public void getTest() throws IOException {
		@SuppressWarnings("resource")
		BufferedReader bf=new BufferedReader(new FileReader("src/txt/test1.txt"));
		String fileentry="";
		String fileoneline;
		Parser tempparser=new Parser();
		while((fileoneline=bf.readLine())!=null) { 
			if(fileoneline.equals("")) {  
				continue;
			}
			else {
				fileentry=fileentry+fileoneline+"\n";
			}
		}
		assertEquals("2021-01-10,2021-03-06}", tempparser.getAllinformation("Period\\{", fileentry));
		
	}
}
