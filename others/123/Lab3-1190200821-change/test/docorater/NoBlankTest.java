package docorater;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import derector.CommonNoBlankIntervalSet;
import interval.IntervalConflictException;
import interval.IntervalSet;
import interval.Pair;

public class NoBlankTest {
	/**
	 * ≤‚ ‘ø’∞◊ºØ∫œ
	 * @throws IntervalConflictException
	 */
	@Test
	public void blankSetTest() throws IntervalConflictException {
		IntervalSet<String> i = IntervalSet.empty();
		Set<Pair<Long, Long>> set = new HashSet<>();
		i.insert(0, 1, "a");
		i.insert(5, 7, "b");
		set.add(new Pair<>(i.end("a"), i.start("b")));
	    set.add(new Pair<>(i.end("b"),i.end("b")+3));
		CommonNoBlankIntervalSet<String> a = new CommonNoBlankIntervalSet<String>(0, 10, i);
		assertEquals(set,a.blankSet());
	}
	/**
	 * ≤‚ ‘tostring
	 * @throws IntervalConflictException
	 */
	@Test
	public void toStringTest() throws IntervalConflictException {
		IntervalSet<String> i = IntervalSet.empty();
		i.insert(0, 1, "a");
		i.insert(5, 7, "b");
		CommonNoBlankIntervalSet<String> a = new CommonNoBlankIntervalSet<String>(0, 10, i);
		assertEquals("[startTime=Thu Jan 01 08:00:00 CST 1970, endTime=Thu Jan 01 08:00:00 CST 1970, intervalSet={a=1, b=7}]", a.toString());
		}
}
