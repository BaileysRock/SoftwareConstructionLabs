package intervalset;

import java.util.ArrayList;
import java.util.Map;

/**
 * 对 NonPeriodicIntervalSet 接口的实现
 * <p>
 * 一个标签可以对应多个时间段
 */
public class NonPeriodicMultiIntervalSetImpl<L>
        implements NonPeriodicIntervalSet<L> {
    /**
     * 标签到时间段的映射
     */
    private final Map<L, ArrayList<Interval>> multiIntervalMap;

    /**
     * 初始化
     *
     * @param multiIntervalMap 标签到时间段的映射
     */
    public NonPeriodicMultiIntervalSetImpl(Map<L, ArrayList<Interval>> multiIntervalMap) {
        this.multiIntervalMap = multiIntervalMap;
    }

    @Override
    public boolean checkNoPeriodic() {
        for (ArrayList<Interval> arrayList : multiIntervalMap.values()) {
            for (Interval interval : arrayList) {
                if (interval.isPeriodic()) {
                    return false;
                }
            }
        }
        return true;
    }
}
