package intervalset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 对 NonOverlapIntervalSet 接口的实现
 * <p>
 * 一个标签可以对应多个时间段
 */
public class NonOverlapMultiIntervalSetImpl<L>
        implements NonOverlapIntervalSet<L> {
    /**
     * 标签到时间段的映射
     */
    private final Map<L, ArrayList<Interval>> multiIntervalMap;

    /**
     * 初始化
     *
     * @param multiIntervalMap 标签到时间段的映射
     */
    public NonOverlapMultiIntervalSetImpl
    (Map<L, ArrayList<Interval>> multiIntervalMap) {
        this.multiIntervalMap = multiIntervalMap;
    }

    @Override
    public boolean checkNoOverlap() {
        Set<Long> set = new HashSet<>();

        for (ArrayList<Interval> arrayList : multiIntervalMap.values()) {
            for (Interval interval : arrayList) {
                for (long i = interval.getStart();
                     i < interval.getEnd(); i++) {
                    if (set.contains(i)) {
                        return false;
                    }
                    set.add(i);
                }
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

        for (ArrayList<Interval> arrayList : multiIntervalMap.values()) {
            for (Interval interval : arrayList) {
                for (long i = interval.getStart();
                     i < interval.getEnd(); i++) {
                    if (set.contains(i)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
