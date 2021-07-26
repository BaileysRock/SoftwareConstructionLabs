package intervalset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NonPeriodicMultiIntervalSetImpl<L> implements NonPeriodicIntervalSet<L>{


    private final Map<L, ArrayList<Interval>> MultiIntervalMap;

    /**
     * 初始化
     * @param MultiIntervalMap 标签到时间段的映射
     */
    public NonPeriodicMultiIntervalSetImpl(Map<L, ArrayList<Interval>> MultiIntervalMap) {
        this.MultiIntervalMap = MultiIntervalMap;
    }


    @Override
    public boolean checkNoPeriodic() {
        for (List<Interval> IntervalList : MultiIntervalMap.values()) {
            for(Interval interval:IntervalList)
            {
                if(interval.getperiodicity())
                    return false;
            }
        }
        return true;
    }
}
