package Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EmployTest {
	Employee e1 = new Employee("li", "rubbish", "400-123456") ;
	Employee e2 = new Employee("wu", "niu", "110") ;
	Employee e3 = new Employee("li", "rubbish", "400-123456") ;
	
	/* Testing strategy
     * ����getName����
     * ���Է���ֵ����
     */
	@Test
	public void getNameTest() {
		assertEquals("li", e1.getName());
		assertEquals("wu", e2.getName());
	}
	/* Testing strategy
	 * ����getPosition����
     * ���Է���ֵ����
     */
	@Test
	public void getPositionTest() {
		assertEquals("rubbish",e1.getPosition());
		assertEquals("niu",e2.getPosition());
	}
	/* Testing strategy
	 * ����getTelePhone����
     * ���Է���ֵ����
     */
	@Test
	public void getTelePhoneTest() {
		assertEquals("400-123456",e1.getTelephone());
		assertEquals("110",e2.getTelephone());
	}
	/* Testing strategy
	 * ����hashCode����
     * ����������ͬ����hashCode�Ƿ���ͬ����
     */
	@Test
	public void hashcodeTest() {
		assertEquals(e1.hashCode(), e3.hashCode());
	}
	/* Testing strategy
	 * ����equals����
     * �����Ƿ���ͬ���ֵȼ��ࣺ��ͬ����ͬ
     */
	@Test
	public void equalTest() {
		assertEquals(true, e1.equals(e3));
		assertEquals(false, e1.equals(e2));
	}
	
}
