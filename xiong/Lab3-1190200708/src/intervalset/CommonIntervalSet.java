package intervalset;


import intervalset.exception.IntervalConflictException;

import java.sql.Time;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommonIntervalSet<L> implements IntervalSet<L> {

    // Abstraction function:
    // 由Map构成的映射关系
    // 即从L映射到Interval的关系
    // Representation invariant:
    // 标签不重复且为不为空
    // 所有时间段的开始时间不为负
    // 且所有时间段的end>start
    // Safety from rep exposure:
    // 使用private、final修饰变量
    // 采用防御性复制
    protected Map<L, Interval> IntervalMap;


    public CommonIntervalSet() {
        IntervalMap = new HashMap<>();
    }


    /**
     * 检查不变量
     * 所有时间段的开始时间不为负
     * 且所有时间段的end>start
     */
    public void checkRep()
    {
        for(Map.Entry<L,Interval> entry:IntervalMap.entrySet())
        {
            assert entry.getValue().getStart()>=0;
            assert entry.getValue().getEnd()>entry.getValue().getStart();
            assert TimeOverlapping(entry.getValue().getStart(),entry.getValue().getEnd(),entry);
        }
    }

    /**
     * 子类方法
     * 检查是否存在时间重叠
     * @param start 新时间段的起始时间
     * @param end 新时间段的截止时间
     * @param entry 原Map的一个映射关系
     * @return 是否重叠
     */
    private boolean TimeOverlapping(long start,long end,Map.Entry<L,Interval> entry)
    {
        // 若重叠则返回true
        if((start<entry.getValue().getEnd() && end>entry.getValue().getStart() )||
                (entry.getValue().getStart()<end && entry.getValue().getEnd()>start))
        {
            return true;
        }
        return false;
    }


    @Override
    public void insert(long start, long end, L label) throws IntervalConflictException {
        IntervalMap.put(label,new Interval(start, end));
        checkRep();
    }

    @Override
    public Set<L> labels() {
        Set<L> labelsSet = new HashSet<>();
        labelsSet.addAll(this.IntervalMap.keySet());
        return labelsSet;
    }

    @Override
    public boolean remove(L label) {
        return this.IntervalMap.remove(label) != null;
    }

    @Override
    public long start(L label) {
        return this.IntervalMap.get(label).getStart();
    }

    @Override
    public long end(L label) {
        return this.IntervalMap.get(label).getEnd();
    }

}
