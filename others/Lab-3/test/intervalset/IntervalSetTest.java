package intervalset;

import intervalset.exceptions.IntervalConflictException;
import org.junit.Test;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.Assert.*;

public class IntervalSetTest {
    // Testing Strategy
    // 插入的标签：重复，不重复
    @Test
    public void labels() {
        CommonIntervalSet<String> commonIntervalSet =
                new CommonIntervalSet<>();
        try {
            commonIntervalSet.insert(0, 2, "a");
            commonIntervalSet.insert(3, 4, "b");
            commonIntervalSet.insert(5, 7, "c");
        } catch (IntervalConflictException e) {
            assert false;
        }

        try {
            commonIntervalSet.insert(8, 9, "a");
            assert false;
        } catch (IntervalConflictException e) {
            assert true;
        }

        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        set.add("c");
        assertEquals(set, commonIntervalSet.labels());
    }

    // Testing Strategy
    // 插入时间段：重复，不重复
    // 插入的标签：重复，不重复
    @Test
    public void insert() {
        CommonIntervalSet<String> commonIntervalSet =
                new CommonIntervalSet<>();

        try {
            commonIntervalSet.insert(0, 2, "a");
        } catch (IntervalConflictException e) {
            assert false;
        }

        try {
            commonIntervalSet.insert(0, 1, "b");
            assert false;
        } catch (IntervalConflictException e) {
            assert true;
        }

        try {
            commonIntervalSet.insert(3, 4, "b");
        } catch (IntervalConflictException e) {
            assert false;
        }

        try {
            commonIntervalSet.insert(5, 7, "c");
        } catch (IntervalConflictException e) {
            assert false;
        }

        assertEquals(0, commonIntervalSet.start("a"));
        assertEquals(2, commonIntervalSet.end("a"));
        assertEquals(3, commonIntervalSet.start("b"));
        assertEquals(4, commonIntervalSet.end("b"));
        assertEquals(5, commonIntervalSet.start("c"));
        assertEquals(7, commonIntervalSet.end("c"));

    }

    // Testing Strategy
    // 删除时间段：存在，不存在
    @Test
    public void remove() {
        CommonIntervalSet<String> commonIntervalSet = new CommonIntervalSet<String>();
        try {
            commonIntervalSet.insert(0, 2, "a");
            commonIntervalSet.insert(3, 4, "b");
        } catch (IntervalConflictException e) {
            assert false;
        }

        assertTrue(commonIntervalSet.remove("a"));
        assertFalse(commonIntervalSet.remove("a"));
        assertFalse(commonIntervalSet.remove("c"));

    }

    // Testing Strategy
    // 查找时间段：存在，不存在
    @Test
    public void start() {
        CommonIntervalSet<String> commonIntervalSet = new CommonIntervalSet<String>();

        try {
            commonIntervalSet.insert(0, 2, "a");
            commonIntervalSet.insert(5, 7, "b");
        } catch (IntervalConflictException e) {
            assert false;
        }
        assertEquals(0, commonIntervalSet.start("a"));
        assertEquals(5, commonIntervalSet.start("b"));

        try {
            commonIntervalSet.start("c");
            assert false;
        } catch (NoSuchElementException e) {
            assert true;
        }
    }

    // Testing Strategy
    // 查找时间段：存在，不存在
    @Test
    public void end() {
        CommonIntervalSet<String> commonIntervalSet = new CommonIntervalSet<String>();

        try {
            commonIntervalSet.insert(0, 2, "a");
            commonIntervalSet.insert(5, 7, "b");
        } catch (IntervalConflictException e) {
            assert false;
        }
        assertEquals(2, commonIntervalSet.end("a"));
        assertEquals(7, commonIntervalSet.end("b"));

        try {
            commonIntervalSet.start("c");
            assert false;
        } catch (NoSuchElementException e) {
            assert true;
        }
    }
}