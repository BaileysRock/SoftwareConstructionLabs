package Resource;

/**
 * Ա��ADT
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
	//get������private���л�ȡ
	public String getName() {
		return name;
	}
	
	public String getPosition() {
		return position;
	}
	
	public String getTelephone() {
		return telephone;
	}
	//��дhashCode��equal����������Ҫ��
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
