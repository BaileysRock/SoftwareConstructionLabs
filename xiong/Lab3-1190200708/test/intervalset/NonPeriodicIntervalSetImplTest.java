package intervalset;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class NonPeriodicIntervalSetImplTest {

    @Test
    public void checkNoPeriodic() {

        Map<String, Interval> IntervalMap = new HashMap<>();
        Interval interval1 = new Interval(1,2);
        Interval interval2 = new Interval(1,2);
        Interval interval4 = new Interval(4,5);
        String s1 = "1";
        String s2 = "2";
        IntervalMap.put(s1,interval1);
        IntervalMap.put(s2,interval2);
        NonPeriodicIntervalSetImpl<String> nonOverlapIntervalSet = new NonPeriodicIntervalSetImpl<>(IntervalMap);
        assert nonOverlapIntervalSet.checkNoPeriodic();

    }
}