package intervalset;

import java.util.*;

public class NonOverlapMultiIntervalSetImpl<L> implements NonOverlapIntervalSet<L>{

    /**
     * 标签到时间段的映射
     */
    private final Map<L, ArrayList<Interval>> MultiIntervalMap;

    public NonOverlapMultiIntervalSetImpl(Map<L, ArrayList<Interval>> MultiIntervalMap) {
        this.MultiIntervalMap = MultiIntervalMap;
    }

    @Override
    public boolean checkNoOverlap() {
        Set<Long> set = new HashSet<>();
        for (List<Interval> IntervalList : this.MultiIntervalMap.values()) {
            for (Interval interval:IntervalList)
            {
                for(long i = interval.getStart();i<interval.getEnd();i++)
                {
                    if(set.contains(i))
                    {
                        return false;
                    }
                    set.add(i);
                }
            }
        }
        return true;
    }



    @Override
    public boolean checkNoOverlap(long start, long end)
    {
        Set<Long> set = new HashSet<>();
        for (List<Interval> IntervalList : this.MultiIntervalMap.values()) {
            for (Interval interval:IntervalList)
            {
                for(long i = interval.getStart();i<interval.getEnd();i++)
                {
                    if(set.contains(i))
                    {
                        return false;
                    }
                    set.add(i);
                }
            }
        }
        for(long i = start;i<end;i++)
        {
            if(set.contains(i))
            {
                return false;
            }
        }
        return true;
    }

}
