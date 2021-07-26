package intervalset;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class NonPeriodicMultiIntervalSetImplTest {

    @Test
    public void checkNoPeriodic() {
        Map<String, ArrayList<Interval>> MultiIntervalMap = new HashMap<>();
        Interval interval1 = new Interval(1,2);
        Interval interval2 = new Interval(2,3);
        //Interval interval3 = new Interval(3,4);
        Interval interval4 = new Interval(4,5);
        ArrayList<Interval> arrayList = new ArrayList<>();
        arrayList.add(interval1);
        arrayList.add(interval2);
        ArrayList<Interval> arrayList1 = new ArrayList<>();
        //arrayList.add(interval3);
        arrayList.add(interval4);
        String s1 = "1";
        String s2 = "2";
        MultiIntervalMap.put(s1,arrayList);
        MultiIntervalMap.put(s2,arrayList1);
        NonPeriodicMultiIntervalSetImpl<String> nonOverlapMultiIntervalSet = new NonPeriodicMultiIntervalSetImpl<>(MultiIntervalMap);
        assert nonOverlapMultiIntervalSet.checkNoPeriodic();
    }
}