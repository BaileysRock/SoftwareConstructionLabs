package intervalset;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class NonOverlapIntervalSetImplTest {

    @Test
    public void checkNoOverlap() {

        Map<String, Interval> IntervalMap = new HashMap<>();
        Interval interval1 = new Interval(1,2);
        Interval interval2 = new Interval(1,2);
        Interval interval4 = new Interval(4,5);
        String s1 = "1";
        String s2 = "2";
        IntervalMap.put(s1,interval1);
        IntervalMap.put(s2,interval2);
        NonOverlapIntervalSetImpl<String> NonOverlapIntervalSet = new NonOverlapIntervalSetImpl<>(IntervalMap);
        assert !NonOverlapIntervalSet.checkNoOverlap();
        assert !NonOverlapIntervalSet.checkNoOverlap(1,2);
    }

    @Test
    public void testCheckNoOverlap() {
        Map<String, Interval> IntervalMap = new HashMap<>();
        Interval interval1 = new Interval(1,2);
        Interval interval2 = new Interval(2,3);
        String s1 = "1";
        String s2 = "2";
        IntervalMap.put(s1,interval1);
        IntervalMap.put(s2,interval2);
        NonOverlapIntervalSetImpl<String> NonOverlapIntervalSet = new NonOverlapIntervalSetImpl<>(IntervalMap);
        assert !NonOverlapIntervalSet.checkNoOverlap(1,3);
        assert NonOverlapIntervalSet.checkNoOverlap(4,5);
    }
}