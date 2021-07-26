package intervalset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 对 NoBlankIntervalSet 接口的实现
 * <p>
 * 一个标签可以对应多个时间段
 */
public class NoBlankMultiIntervalSetImpl<L>
        implements NoBlankIntervalSet<L> {
    /**
     * 标签到时间段的映射
     */
    private final Map<L, ArrayList<Interval>> multiIntervalMap;

    /**
     * 初始化
     *
     * @param multiIntervalMap 标签到时间段的映射
     */
    public NoBlankMultiIntervalSetImpl(Map<L, ArrayList<Interval>> multiIntervalMap) {
        this.multiIntervalMap = multiIntervalMap;
    }

    @Override
    public boolean checkNoBlank(long start, long end) {
        Set<Long> set = new HashSet<>();
        for (ArrayList<Interval> arrayList : multiIntervalMap.values()) {
            for (Interval interval : arrayList) {
                for (long i = interval.getStart();
                     i < interval.getEnd(); i++) {
                    set.add(i);
                }
            }
        }
        for(long i=start;i<end;i++){
            if (!set.contains(i)){
                return false;
            }
        }

        return true;
    }
}
