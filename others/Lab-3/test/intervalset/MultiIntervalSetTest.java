package intervalset;

import intervalset.exceptions.IntervalConflictException;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class MultiIntervalSetTest {
    // Testing Strategy
    // 插入时间段：重复，不重复
    // 插入的标签：重复，不重复
    @Test
    public void insert() {
        CommonMultiIntervalSet<String> commonMultiIntervalSet =
                new CommonMultiIntervalSet<>();

        try {
            commonMultiIntervalSet.insert(0, 1, "a");
            commonMultiIntervalSet.insert(3, 5, "a");
            commonMultiIntervalSet.insert(7, 9, "b");
            commonMultiIntervalSet.insert(7, 9, "c");
        } catch (IntervalConflictException e) {
            assert false;
        }
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        set.add("c");
        assertEquals(set, commonMultiIntervalSet.labels());
    }

    // Testing Strategy
    // 插入的标签：重复，不重复
    @Test
    public void labels() {
        CommonMultiIntervalSet<String> commonMultiIntervalSet =
                new CommonMultiIntervalSet<>();

        try {
            commonMultiIntervalSet.insert(0, 1, "a");
            commonMultiIntervalSet.insert(3, 5, "a");
            commonMultiIntervalSet.insert(7, 9, "b");
        } catch (IntervalConflictException e) {
            assert false;
        }
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        assertEquals(set, commonMultiIntervalSet.labels());
    }

    // Testing Strategy
    // 删除时间段：存在，不存在
    @Test
    public void remove() {
        CommonMultiIntervalSet<String> commonMultiIntervalSet =
                new CommonMultiIntervalSet<>();

        try {
            commonMultiIntervalSet.insert(0, 1, "a");
            commonMultiIntervalSet.insert(3, 5, "a");
            commonMultiIntervalSet.insert(7, 9, "b");
        } catch (IntervalConflictException e) {
            assert false;
        }
        assertTrue(commonMultiIntervalSet.remove("b"));
        assertFalse(commonMultiIntervalSet.remove("b"));
        assertTrue(commonMultiIntervalSet.remove("a"));
    }

    // Testing Strategy
    // 查找的标签：存在，不存在
    @Test
    public void intervals() {
        CommonMultiIntervalSet<String> commonMultiIntervalSet =
                new CommonMultiIntervalSet<>();

        try {
            commonMultiIntervalSet.insert(0, 1, "a");
            commonMultiIntervalSet.insert(3, 5, "a");
            commonMultiIntervalSet.insert(7, 9, "b");
        } catch (IntervalConflictException e) {
            assert false;
        }
        assertEquals(0, commonMultiIntervalSet.intervals("a").start(0));
        assertEquals(3, commonMultiIntervalSet.intervals("a").start(1));
        assertEquals(1, commonMultiIntervalSet.intervals("a").end(0));
        assertEquals(5, commonMultiIntervalSet.intervals("a").end(1));
    }
}