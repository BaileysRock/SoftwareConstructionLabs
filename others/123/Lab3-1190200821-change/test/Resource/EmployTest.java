package Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EmployTest {
	Employee e1 = new Employee("li", "rubbish", "400-123456") ;
	Employee e2 = new Employee("wu", "niu", "110") ;
	Employee e3 = new Employee("li", "rubbish", "400-123456") ;
	
	/* Testing strategy
     * 测试getName方法
     * 测试返回值即可
     */
	@Test
	public void getNameTest() {
		assertEquals("li", e1.getName());
		assertEquals("wu", e2.getName());
	}
	/* Testing strategy
	 * 测试getPosition方法
     * 测试返回值即可
     */
	@Test
	public void getPositionTest() {
		assertEquals("rubbish",e1.getPosition());
		assertEquals("niu",e2.getPosition());
	}
	/* Testing strategy
	 * 测试getTelePhone方法
     * 测试返回值即可
     */
	@Test
	public void getTelePhoneTest() {
		assertEquals("400-123456",e1.getTelephone());
		assertEquals("110",e2.getTelephone());
	}
	/* Testing strategy
	 * 测试hashCode方法
     * 测试两个相同的类hashCode是否相同即可
     */
	@Test
	public void hashcodeTest() {
		assertEquals(e1.hashCode(), e3.hashCode());
	}
	/* Testing strategy
	 * 测试equals方法
     * 按照是否相同划分等价类：相同，不同
     */
	@Test
	public void equalTest() {
		assertEquals(true, e1.equals(e3));
		assertEquals(false, e1.equals(e2));
	}
	
}
