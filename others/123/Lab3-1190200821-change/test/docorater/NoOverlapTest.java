package docorater;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import derector.NoOverlapIntervalSet;
import interval.IntervalConflictException;
import interval.IntervalSet;

public class NoOverlapTest {
	@Test
	public void insertTest() throws IntervalConflictException {
		IntervalSet<String> i = IntervalSet.empty();
		NoOverlapIntervalSet<String> a = new NoOverlapIntervalSet<String>(i);
		a.insert(0, 5, "a");
		a.insert(6, 9, "b");
		Set<String> set = new HashSet<>();
		set.add("a");
		set.add("b");
		assertEquals(set, a.labels());	
		
	}
	@Test
	public void toStringTest() throws IntervalConflictException {
		IntervalSet<String> i = IntervalSet.empty();
		NoOverlapIntervalSet<String> a = new NoOverlapIntervalSet<String>(i);
		a.insert(0, 5, "a");
		a.insert(6, 9, "b");
		assertEquals("[intervals={a=interval.Pair@0, b=interval.Pair@36}, intervalSet={a=5, b=9}]", a.toString());
	}
}
