package intervalset;

import intervalset.exception.IntervalConflictException;

import java.util.Set;

public interface IntervalSet <L>{


    public static<L> IntervalSet<L> empty()
    {
        return new CommonIntervalSet<>();
    }

    /**
     * 在当前对象中插入新的时间段和标签
     * @param start the start
     * @param end the end
     * @param label a label
     */
    public void insert(long start,long end,L label) throws IntervalConflictException;


    /**
     * 获得当前对象中的标签集合
     * @return 当前对象中标签的集合
     */
    public Set<L> labels();

    /**
     * 从当前对象中移除某个标签所关联的时间段
     * @param label a label
     * @return 是否成功移除当前对象label对应的标签关联的时间段
     */
    public boolean remove(L label);


    /**
     * 获得某个标签对应的时间段的开始时间
     * @param label a label
     * @return label标签对应的时间段的开始时间
     */
    public long start(L label);


    /**
     * 获得某个标签对应的时间段的结束时间
     * @param label a label
     * @return label标签对应的时间段的结束时间
     */
    public long end(L label);

}
