package intervalset;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 对 NonOverlapIntervalSet 接口的实现
 * <p>
 * 时间段对应的各个标签不重复
 */
public class NonOverlapIntervalSetImpl<L>
        implements NonOverlapIntervalSet<L> {
    /**
     * 标签到时间段的映射
     */
    private final Map<L, Interval> intervalMap;

    /**
     * 初始化
     *
     * @param intervalMap 标签到时间段的映射
     */
    public NonOverlapIntervalSetImpl(Map<L, Interval> intervalMap) {
        this.intervalMap = intervalMap;
    }

    @Override
    public boolean checkNoOverlap() {
        Set<Long> set = new HashSet<>();

        for (Interval interval : intervalMap.values()) {
            for (long i = interval.getStart(); i < interval.getEnd(); i++) {
                if (set.contains(i)) {
                    return false;
                }
                set.add(i);
            }
        }

        return true;
    }

    @Override
    public boolean checkNoOverlap(long start, long end) {
        Set<Long> set = new HashSet<>();
        for (long i = start; i < end; i++) {
            set.add(i);
        }

        for (Interval interval : intervalMap.values()) {
            for (long i = interval.getStart(); i < interval.getEnd(); i++) {
                if (set.contains(i)) {
                    return false;
                }
            }
        }

        return true;
    }
}
