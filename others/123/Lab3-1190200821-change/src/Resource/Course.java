package Resource;

import java.util.Objects;
/**
 * �γ�ADT
 * @author liyu
 *
 */
public class Course {
	private final String cid;
	private final String cname;
	private final String tname;
	private final String place;
	private final int studyNum;
	
	//constructor
	public Course(String cid,String cname,String tname,String place,int studyNum) {
		this.cid = cid;
		this.cname = cname;
		this.place = place;
		this.tname = tname;
		this.studyNum = studyNum;
	}
	//get������private���л�ȡ
	public String getCid() {
		return cid;
	}
	
	public String getCname() {
		return cname;
	}
	public String getTname() {
		return tname;
	}
	public String getPlace() {
		return place;
	}
	public int getStudyNum() {
		return studyNum;
	}
	//��дhashCode��equal����������Ҫ��
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		
		Course crsObj = (Course) obj;
		return Objects.equals(this.cname, crsObj.cname) && 
				Objects.equals(this.tname, crsObj.tname) && 
				Objects.equals(this.place, crsObj.place) && 
				Objects.equals(this.cid, crsObj.cid);
				
	}
	
	@Override
	public int hashCode() {
		return this.cname.hashCode() * this.cid.hashCode() * this.tname.hashCode() * this.place.hashCode()*this.studyNum;
	}
	
	
	
	
}
