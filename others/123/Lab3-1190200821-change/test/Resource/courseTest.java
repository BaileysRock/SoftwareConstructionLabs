package Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class courseTest {
	Course c1 = new Course("c1", "Chinese", "wang", "zx11", 8);
	Course c2 = new Course("c1", "Chinese", "wang", "zx11", 8);
	Course c3 = new Course("a5", "English", "liu", "zx21", 4);
	/* Testing strategy
	 * ����getCid����
     * ���Է���ֵ����
     */
	@Test
	public void getCidTest() {
		assertEquals("c1", c1.getCid());
		assertEquals("a5", c3.getCid());
	}
	/* Testing strategy
	 * ����getCname����
     * ���Է���ֵ����
     */
	@Test
	public void getCnameTest() {
		assertEquals("Chinese",c1.getCname());
		assertEquals("English",c3.getCname());
	}
	/* Testing strategy
	 * ����getTname����
     * ���Է���ֵ����
     */
	@Test
	public void getTnameTest() {
		assertEquals("wang",c1.getTname());
		assertEquals("liu",c3.getTname());
	}
	/* Testing strategy
	 * ����getPlace����
     * ���Է���ֵ����
     */
	@Test
	public void getPlaceTest() {
		assertEquals("zx11",c1.getPlace());
		assertEquals("zx21",c3.getPlace());
	}
	/* Testing strategy
	 * ����hashcode����
     * ����������ͬ����hashcode�Ƿ���ͬ����
     */
	@Test
	public void hashcodeTest() {
		assertEquals(c1.hashCode(), c2.hashCode());
	}
	/* Testing strategy
	 * ����equals����
     * �����Ƿ���ͬ���ֵȼ��ࣺ��ͬ����ͬ
     */
	@Test
	public void equalTest() {
		assertEquals(true, c1.equals(c2));
		assertEquals(false, c1.equals(c3));
	}
	
}
