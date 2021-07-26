package intervalset;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 对 NoBlankIntervalSet 接口的实现
 * <p>
 * 时间段对应的各个标签不重复
 */
public class NoBlankIntervalSetImpl<L>
        implements NoBlankIntervalSet<L> {
    /**
     * 标签到时间段的映射
     */
    private final Map<L, Interval> intervalMap;

    /**
     * 初始化
     *
     * @param intervalMap 标签到时间段的映射
     */
    public NoBlankIntervalSetImpl(Map<L, Interval> intervalMap) {
        this.intervalMap = intervalMap;
    }

    @Override
    public boolean checkNoBlank(long start, long end) {
        Set<Long> set = new HashSet<>();
        for (Interval interval : intervalMap.values()) {
            for (long i = interval.getStart(); i < interval.getEnd(); i++) {
                set.add(i);
            }
        }
        for (long i = start; i < end; i++) {
            if (!set.contains(i)) {
                return false;
            }
        }

        return true;
    }
}
