package intervalset;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NonOverlapIntervalSetImpl<L> implements NonOverlapIntervalSet<L>{



    /**
     * 标签到时间段的映射
     */
    private final Map<L, Interval> IntervalMap;

    /**
     * 初始化
     * @param IntervalMap 标签到时间段的映射
     */
    public NonOverlapIntervalSetImpl(Map<L, Interval> IntervalMap) {
        this.IntervalMap = IntervalMap;
    }




    @Override
    public boolean checkNoOverlap()
    {

        Set<Long> set = new HashSet<>();
        for (Interval interval : this.IntervalMap.values()) {
//            for (long i = interval.getStart(); i < interval.getEnd(); i++) {
//                if (set.contains(i)) {
//                    return false;
//                }
//                set.add(i);
//            }
            if(set.contains(interval.getStart()))
                return false;
            set.add(interval.getStart());

        }
        return true;
    }

    @Override
    public boolean checkNoOverlap(long start, long end)
    {
//        Set<Long> set = new HashSet<>();
        for (Interval interval : this.IntervalMap.values()) {
//            for (long i = interval.getStart(); i < interval.getEnd(); i++) {
//                if (set.contains(i)) {
//                    return false;
//                }
//                set.add(i);
//            }

            if(start>=interval.getStart() && start<interval.getEnd() ||
                end>=interval.getStart()&& end< interval.getEnd())
            {
                return false;
            }



        }
//        for(long i = start;i<end;i++)
//        {
//            if(set.contains(i))
//            {
//                return false;
//            }
//        }
        return true;
    }


}
