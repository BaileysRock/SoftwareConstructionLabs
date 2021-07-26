package Timelot;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class TimelotTest {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/* Testing strategy
	 * 测试getbegintime方法
     * 测试起始时间的返回值即可
     */
	@Test
	public void getbegintest() throws ParseException {
		Timelot temp=new Timelot("2020-01-01","2020-01-02");
		Date begintime = sdf.parse("2020-01-01");
		assertEquals(begintime.getTime(),temp.getBegin());
		
	}
	
	/* Testing strategy
	 * 测试getendtime方法
     * 测试结束时间的返回值即可
     */
	@Test
	public void getendtimetest() throws ParseException {
		Timelot temp=new Timelot("2020-01-01","2020-01-02");
		Date endtime = sdf.parse("2020-01-02");
		assertEquals(endtime.getTime(),temp.getEnd());
	}
	
	
	/* Testing strategy
	 * 测试hashcode方法
     * 测试两个相同的时间类hashcode是否相同即可
     */
	@Test
	public void hashcodetest() throws ParseException {
		Timelot temp=new Timelot("2020-01-01","2020-01-02");
		Timelot temp1=new Timelot("2020-01-01","2020-01-02");
		assertEquals(temp.hashCode(),temp1.hashCode());	
	}
	
	/* Testing strategy
	 * 测试equals方法
     * 按照两个时间是否相同划分等价类：时间相同，时间不同
     */
	@Test
	public void equalstest() throws ParseException {
		Timelot temp=new Timelot("2020-01-01","2020-01-01");
		Timelot temp1=new Timelot("2020-01-01","2020-01-02");
		Timelot temp2=new Timelot("2020-01-01","2020-01-01");
		assertEquals(false,temp.equals(temp1));
		assertEquals(true,temp.equals(temp2));
	}

}
