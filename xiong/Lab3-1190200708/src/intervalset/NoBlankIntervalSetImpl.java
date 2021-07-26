package intervalset;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NoBlankIntervalSetImpl<L> implements NoBlankIntervalSet<L>{


    /**
     * 标签到时间段的映射
     */
    private final Map<L, Interval> IntervalMap;

    /**
     * 初始化
     * @param IntervalMap 标签到时间段的映射
     */
    public NoBlankIntervalSetImpl(Map<L, Interval> IntervalMap) {
        this.IntervalMap = IntervalMap;
    }

    /**
     * 检查从start到end的时间段是否存在空闲时间段
     * @param start 起始时间
     * @param end 结束时间
     * @return 是否存在空闲时间
     */
    @Override
    public boolean checkNoBlank(long start, long end) {
        Set<Long> setTime = new HashSet<>();
        for(Interval interval:IntervalMap.values())
        {
            for(long i = interval.getStart();i<interval.getEnd();i+=24*60*60*1000)
            {
                setTime.add(i);
            }
        }
        for(long i = start;i<end;i+=24*60*60*1000)
        {
            if(!setTime.contains(i))
            {
                return false;
            }
        }
        return true;
    }
}
