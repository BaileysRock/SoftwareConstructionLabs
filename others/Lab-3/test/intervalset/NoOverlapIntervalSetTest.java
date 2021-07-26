package intervalset;

import intervalset.exceptions.IntervalConflictException;
import org.junit.Test;

public class NoOverlapIntervalSetTest {
    // Testing Strategy
    // 插入的时间段与现有时间段：不重叠，左侧重叠，右侧重叠，完全包含
    @Test
    public void insert() {
        NoOverlapIntervalSet<String> noOverlapIntervalSet =
                new NoOverlapIntervalSet<>(IntervalSet.empty());

        try {
            noOverlapIntervalSet.insert(1, 3, "a");
        } catch (IntervalConflictException e) {
            assert false;
        }

        // 不重叠
        try {
            noOverlapIntervalSet.insert(4, 8, "b");
        } catch (IntervalConflictException e) {
            assert false;
        }

        // 左侧重叠
        try {
            noOverlapIntervalSet.insert(7, 9, "c");
            assert false;
        } catch (IntervalConflictException e) {
            assert true;
        }

        // 右侧重叠
        try {
            noOverlapIntervalSet.insert(0, 2, "d");
            assert false;
        } catch (IntervalConflictException e) {
            assert true;
        }

        // 完全包含
        try {
            noOverlapIntervalSet.insert(5, 7, "e");
            assert false;
        } catch (IntervalConflictException e) {
            assert true;
        }
    }
}
