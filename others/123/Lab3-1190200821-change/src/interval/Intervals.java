package interval;
/**
 * �ʼд��ADT����������ûɶ�ã����ø���
 * @author liyu
 *
 */
public class Intervals {
	private long start,end;
	
	 public Intervals(long start,long end) {
		this.end= end;
		this.start = start;
	}
 
	public long getStart() {
		return start;
	}
 
	public void setStart(long start) {
		this.start = start;
	}
 
	public long getEnd() {
		return end;
	}
 
	public void setEnd(long end) {
		this.end = end;
	}
 
}