package interval;
/**
 * 最开始写的ADT，后来觉得没啥用，懒得改了
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