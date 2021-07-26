package Resource;

import java.util.Objects;

/**
 * 进程ADT
 * @author liyu
 *
 */
public class Process {
	private final int pid;
	private final String pname;
	private final int shortestTime;
	private final int longestTime;
	private String state;
	public void setState(String state) {
		this.state = state;
	}
	//constructor
	public Process(int pid,String pname,int shortestTime,int longesTime,String state)
	{
		this.pid = pid;
		this.longestTime =longesTime;
		this.shortestTime =shortestTime;
		this.pname = pname;
		this.state = state;
		
	}
	//get方法对private进行获取
	public int getPid() {
		return pid;
	}
	public String getPname() {
		return pname;
	}
	public int getShortestTime() {
		return shortestTime;
	}
	public int getLongestTime() {
		return longestTime;
	}
	public String getState() {
		return state;
	}
	//重写hashCode和equal方法（很重要）
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		
		Process prcObj = (Process) obj;
		return Objects.equals(this.pname, prcObj.pname) &&
				this.pid == prcObj.pid &&
				this.shortestTime == prcObj.shortestTime &&
				this.longestTime == prcObj.longestTime&&Objects.equals(this.state, prcObj.state);
	}
	
	@Override
	public int hashCode() {
		return this.pname.hashCode() * this.pid * (int)this.longestTime * (int)this.shortestTime;
	}
	
}
