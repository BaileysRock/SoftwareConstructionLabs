package intervalsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import interval.CommonMultiIntervalSet;
import interval.IntervalConflictException;

public class CommonMultiTest {
	 
	private final CommonMultiIntervalSet<String> intval = new CommonMultiIntervalSet<String>();
	/**
	 * 利用label测试insert结果
	 * @throws IntervalConflictException
	 */
	@Test
	public void InsertTest() throws IntervalConflictException {
       intval.insert​(0, 1, "a");
       intval.insert​(3, 5, "a");
       intval.insert​(7, 9, "b");
       Set<String> set = new HashSet<>();
	   set.add("a");
	   set.add("b");
	   assertEquals(set, intval.labels());
       
	}
	/**
	 * 测试label的值
	 * @throws IntervalConflictException
	 */
	@Test
	public void labelsTest() throws IntervalConflictException {
		intval.insert​(0, 1, "a");
	    intval.insert​(3, 5, "a");
	    intval.insert​(7, 9, "b");
	    Set<String> set = new HashSet<>();
		set.add("a");
		set.add("b");
		assertEquals(set, intval.labels());
	}
	/**
	 * 测试删除是否成功，分为在集合中和不在集合中，还用重复删除
	 * @throws IntervalConflictException
	 */
	@Test
	public void RemoveTest() throws IntervalConflictException {
		intval.insert​(0, 1, "a");
	    intval.insert​(3, 5, "a");
	    intval.insert​(7, 9, "b");
	    assertEquals(true, intval.remove("b"));
	    assertEquals(false, intval.remove("b"));
	    assertEquals(true, intval.remove("a"));
	}
	/**
	 * 测试集合中的起始终止时间
	 * @throws IntervalConflictException
	 */
	@Test
	public void intervalsTest() throws IntervalConflictException {
		intval.insert​(0, 1, "a");
	    intval.insert​(3, 5, "a");
	    intval.insert​(7, 9, "b");
	    assertEquals(0,intval.intervals​("a").start(0));
	    assertEquals(3,intval.intervals​("a").start(1));
	    assertEquals(1,intval.intervals​("a").end(0));
	    assertEquals(5,intval.intervals​("a").end(1));
	}
	/**
	 * 测试toString
	 * @throws IntervalConflictException
	 */
	@Test
	public void toStringTest() throws IntervalConflictException {
		intval.insert​(0, 1, "a");
	    intval.insert​(3, 5, "a");
	    intval.insert​(7, 9, "b");
	    assertEquals("[{a=1}, {a=5, b=9}]", intval.toString());
	    
	}
}
