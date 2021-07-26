package Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ProcessTest {

	Process p1 = new Process(1, "p1", 1, 5, "wait");
	Process p2 = new Process(2, "p2", 4, 7, "run");
	Process p3 = new Process(1, "p1", 1, 5, "wait");
	
	/* Testing strategy
	 * ����getPid����
     * ���Է���ֵ����
     */
	@Test
	public void getPidTest() {
		assertEquals(1, p1.getPid());
		assertEquals(1, p3.getPid());
		assertEquals(2, p2.getPid());
}
	/* Testing strategy
	 * ����getPname����
     * ���Է���ֵ����
     */
	@Test
	public void getPnameTest() {
		assertEquals("p1",p1.getPname());
		assertEquals("p2",p2.getPname());
	}
	/* Testing strategy
	 * ����getTname����
     * ���Է���ֵ����
     */
	@Test
	public void getShortTest() {
		assertEquals(1,p1.getShortestTime());
		assertEquals(4,p2.getShortestTime());
	}
	/* Testing strategy
	 * ����getPlace����
     * ���Է���ֵ����
     */
	@Test
	public void getPlaceTest() {
		assertEquals(5,p1.getLongestTime());
		assertEquals(7,p2.getLongestTime());
	}
	/* Testing strategy
	 * ����hashcode����
     * ����������ͬ����hashcode�Ƿ���ͬ����
     */
	@Test
	public void hashcodeTest() {
		assertEquals(p1.hashCode(), p3.hashCode());
	}
	/* Testing strategy
	 * ����equals����
     * �����Ƿ���ͬ���ֵȼ��ࣺ��ͬ����ͬ
     */
	@Test
	public void equalTest() {
		assertEquals(true, p1.equals(p3));
		assertEquals(false, p1.equals(p2));
	}
	
}
