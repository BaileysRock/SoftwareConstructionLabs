package intervalsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import interval.CommonIntervalSet;
import interval.IntervalConflictException;

public class CommonIntervalTest {
	private final CommonIntervalSet<String> intval = new CommonIntervalSet<String>();
	/**
	 * 测试是否添加成功，用labels辅助测试
	 * @throws IntervalConflictException
	 */
	@Test
	public void InsertTest() throws IntervalConflictException {
		intval.insert(0, 2, "a");
		intval.insert(5, 7, "b");
		Set<String> set = new HashSet<>();
		set.add("a");
		set.add("b");
		assertEquals(set, intval.labels());
		assertEquals(0, intval.start("a"));
		assertEquals(7, intval.end("b"));

		
	}
	/**
	 * 测试label的值
	 * @throws IntervalConflictException
	 */
	@Test
	public void labelsTest() throws IntervalConflictException {
		intval.insert(0, 2, "a");
		intval.insert(5, 7, "b");
		Set<String> set = new HashSet<>();
		set.add("a");
		set.add("b");
		assertEquals(set, intval.labels());
	}
	/**
	 * 等价类：在集合中，不在集合中，重复删除
	 * @throws IntervalConflictException
	 */
	@Test
	public void RemoveTest() throws IntervalConflictException {
		intval.insert(0, 2, "a");
		intval.insert(5, 7, "b");
		assertEquals(true, intval.remove("a"));
		assertEquals(false, intval.remove("a"));
		assertEquals(false, intval.remove("c"));
	}
	/**
	 * 测试返回值
	 * @throws IntervalConflictException
	 */
	@Test
	public void beginTest() throws IntervalConflictException {
		intval.insert(0, 2, "a");
		intval.insert(5, 7, "b");
		assertEquals(0, intval.start("a"));
		assertEquals(5, intval.start("b"));
	}
	/**
	 * 测试返回值
	 * @throws IntervalConflictException
	 */
	@Test
	public void endTest() throws IntervalConflictException {
		intval.insert(0, 2, "a");
		intval.insert(5, 7, "b");
		assertEquals(2, intval.end("a"));
		assertEquals(7, intval.end("b"));
	}
	/**
	 * 测试toString方法
	 * @throws IntervalConflictException
	 */
	@Test
	public void toStringTest() throws IntervalConflictException {
		intval.insert(0, 1, "a");
	    intval.insert(7, 9, "b");
	    assertEquals("{a=1, b=9}", intval.toString());
	    
	}
}
