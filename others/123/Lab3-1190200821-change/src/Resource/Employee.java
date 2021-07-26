package Resource;

/**
 * 员工ADT
 * @author liyu
 *
 */

import java.util.Objects;

public class Employee {
	private final String name;
	private final String position;
	private final String telephone;
	//constructor
	public Employee(String name,String position,String telephone){
		this.name = name;
		this.position = position;
		this.telephone = telephone;
	}
	//get方法对private进行获取
	public String getName() {
		return name;
	}
	
	public String getPosition() {
		return position;
	}
	
	public String getTelephone() {
		return telephone;
	}
	//重写hashCode和equal方法（很重要）
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		Employee emObj = (Employee) obj;
		return Objects.equals(this.name, emObj.name) &&
				Objects.equals(this.position, emObj.position) && 
				this.telephone == emObj.telephone;
	}
	
	@Override
	public int hashCode() {
		return this.position.hashCode() * this.name.hashCode()* this.telephone.hashCode();
	}
	
	
}
