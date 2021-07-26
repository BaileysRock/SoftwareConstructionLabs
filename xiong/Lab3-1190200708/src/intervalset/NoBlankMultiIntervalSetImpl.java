package intervalset;

import java.util.*;

public class NoBlankMultiIntervalSetImpl<L> implements NoBlankIntervalSet<L> {



    /**
     * 标签到时间段的映射
     */
    private final Map<L, ArrayList<Interval>> MultiIntervalMap;

    /**
     * 初始化
     * @param MultiIntervalMap 标签到时间段的映射
     */
    public NoBlankMultiIntervalSetImpl(Map<L, ArrayList<Interval>> MultiIntervalMap) {
        this.MultiIntervalMap = MultiIntervalMap;
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
        for(List<Interval> IntervalList:MultiIntervalMap.values())
        {
            for(Interval interval:IntervalList)
            {
                for(long i=interval.getStart();i<interval.getEnd();i++)
                {
                    setTime.add(i);
                }

            }
        }
        for(long i = start;i<end;i++)
        {
            if(!setTime.contains(i))
            {
                return false;
            }
        }
        return true;
    }
}
