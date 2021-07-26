package Timelot;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class TimelotTest {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/* Testing strategy
	 * ����getbegintime����
     * ������ʼʱ��ķ���ֵ����
     */
	@Test
	public void getbegintest() throws ParseException {
		Timelot temp=new Timelot("2020-01-01","2020-01-02");
		Date begintime = sdf.parse("2020-01-01");
		assertEquals(begintime.getTime(),temp.getBegin());
		
	}
	
	/* Testing strategy
	 * ����getendtime����
     * ���Խ���ʱ��ķ���ֵ����
     */
	@Test
	public void getendtimetest() throws ParseException {
		Timelot temp=new Timelot("2020-01-01","2020-01-02");
		Date endtime = sdf.parse("2020-01-02");
		assertEquals(endtime.getTime(),temp.getEnd());
	}
	
	
	/* Testing strategy
	 * ����hashcode����
     * ����������ͬ��ʱ����hashcode�Ƿ���ͬ����
     */
	@Test
	public void hashcodetest() throws ParseException {
		Timelot temp=new Timelot("2020-01-01","2020-01-02");
		Timelot temp1=new Timelot("2020-01-01","2020-01-02");
		assertEquals(temp.hashCode(),temp1.hashCode());	
	}
	
	/* Testing strategy
	 * ����equals����
     * ��������ʱ���Ƿ���ͬ���ֵȼ��ࣺʱ����ͬ��ʱ�䲻ͬ
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
