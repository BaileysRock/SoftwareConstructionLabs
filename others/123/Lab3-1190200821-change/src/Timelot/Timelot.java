package Timelot;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换中间ADT
 * @author liyu
 *
 */
public class Timelot {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private final Long begin;
	private final Long end;
	//constructor
	public Timelot(String begin,String end) throws ParseException
	{
		Date biginDate = sdf.parse(begin);
		Date enDate = sdf.parse(end);
		this.begin=biginDate.getTime();
		this.end  =enDate.getTime();
	}
	//get方法
	public Long getBegin() {
		return begin;
	}
	public Long getEnd() {
		return end;
	}
	//重写hashCode和equal方法
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass()!=obj.getClass()) return false;
		Timelot otherTimelot = (Timelot) obj;
		return this.hashCode()==otherTimelot.hashCode();
	}
	@Override
	public int hashCode() {
		final int prime =31;
		int result = 1;
		result =prime * result + (begin.hashCode());
		result = prime *result +(end.hashCode());
		return result;
	}
	
	
}
