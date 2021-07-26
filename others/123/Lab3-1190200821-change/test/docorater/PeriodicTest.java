package docorater;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import derector.PeriodicMultiIntervalSet;
import interval.IntervalConflictException;
import interval.IntervalSet;
import interval.MultiIntervalSet;

public class PeriodicTest {
	@Test
	public void insertTest() throws IntervalConflictException {
		IntervalSet<String> i = IntervalSet.empty();
		PeriodicMultiIntervalSet<String> p = new PeriodicMultiIntervalSet<String>(7,MultiIntervalSet.init(i));
		p.insert​(0, 1, "a");
		p.insert​(8, 9, "b");
        assertEquals(1, p.intervals​("b").start(0));
	}
	@Test 
	public void toStringTest() throws IntervalConflictException {
		IntervalSet<String> i = IntervalSet.empty();
		PeriodicMultiIntervalSet<String> p = new PeriodicMultiIntervalSet<String>(7,MultiIntervalSet.init(i));
		p.insert​(0, 1, "a");
		p.insert​(8, 9, "b");
		assertEquals("[period=7, multiIntervalSet=[{a=1, b=2}]]", p.toString());
	}
}
