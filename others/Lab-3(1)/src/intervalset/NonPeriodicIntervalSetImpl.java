package intervalset;

import java.util.Map;

/**
 * 对 NonPeriodicIntervalSet 接口的实现
 * <p>
 * 时间段对应的各个标签不重复
 */
public class NonPeriodicIntervalSetImpl<L>
        implements NonPeriodicIntervalSet<L> {
    /**
     * 标签到时间段的映射
     */
    private final Map<L, Interval> intervalMap;

    /**
     * 初始化
     *
     * @param intervalMap 标签到时间段的映射
     */
    public NonPeriodicIntervalSetImpl(Map<L, Interval> intervalMap) {
        this.intervalMap = intervalMap;
    }

    @Override
    public boolean checkNoPeriodic() {
        for (Interval interval : intervalMap.values()) {
            if (interval.isPeriodic()) {
                return false;
            }
        }

        return true;
    }
}
