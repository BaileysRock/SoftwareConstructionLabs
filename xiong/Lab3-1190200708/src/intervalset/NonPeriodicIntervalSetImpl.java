package intervalset;

import java.util.Map;

public class NonPeriodicIntervalSetImpl<L> implements NonPeriodicIntervalSet<L> {


    private final Map<L, Interval> IntervalMap;

    /**
     * 初始化
     * @param IntervalMap 标签到时间段的映射
     */
    public NonPeriodicIntervalSetImpl(Map<L, Interval> IntervalMap) {
        this.IntervalMap = IntervalMap;
    }


    @Override
    public boolean checkNoPeriodic() {
        for (Interval interval : IntervalMap.values()) {
            if (interval.getperiodicity()) {
                return false;
            }
        }
        return true;
    }

}
