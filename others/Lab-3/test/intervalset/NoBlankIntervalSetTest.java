package intervalset;

import intervalset.exceptions.IntervalConflictException;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class NoBlankIntervalSetTest {
    // Testing Strategy
    // 空闲时间段数量：0 个，1 个，多个
    @Test
    public void blankIntervalSet() {
        NoBlankIntervalSet<String> noBlankIntervalSet =
                new NoBlankIntervalSet<>(0, 10, IntervalSet.empty());
        Set<Interval> blankIntervalSet = new HashSet<>();

        // 多个空闲时间段
        try {
            noBlankIntervalSet.insert(0, 2, "a");
            noBlankIntervalSet.insert(3, 4, "b");
            noBlankIntervalSet.insert(6, 10, "c");
        } catch (IntervalConflictException e) {
            assert false;
        }
        blankIntervalSet.add(new Interval(2, 3));
        blankIntervalSet.add(new Interval(4, 6));
        assertEquals(
                blankIntervalSet,
                noBlankIntervalSet.blankIntervalSet()
        );

        // 1 个空闲时间段
        try {
            noBlankIntervalSet.insert(2, 3, "d");
        } catch (IntervalConflictException e) {
            assert false;
        }
        blankIntervalSet.clear();
        blankIntervalSet.add(new Interval(4, 6));
        assertEquals(
                blankIntervalSet,
                noBlankIntervalSet.blankIntervalSet()
        );

        // 0 个空闲时间段
        try {
            noBlankIntervalSet.insert(4, 6, "e");
        } catch (IntervalConflictException e) {
            assert false;
        }
        blankIntervalSet.clear();
        assertEquals(
                blankIntervalSet,
                noBlankIntervalSet.blankIntervalSet()
        );
    }
}
