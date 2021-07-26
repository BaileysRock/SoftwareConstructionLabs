package intervalset;
import intervalset.exception.IntervalConflictException;

import java.util.Set;

public interface MultiIntervalSet <L>{


    public static<L> MultiIntervalSet<L> empty()
    {
        return new CommonMultiIntervalSet<>();
    }


    /**
     * 创建一个非空对象
     *
     * @param initial 数据来源
     * @return 返回创建的非空对象
     */
    public static<L> MultiIntervalSet<L> init(IntervalSet<L> initial)
    {
        return new CommonMultiIntervalSet<>(initial);
    }




    /**
     * 在当前对象中插入与label关联的时间段
     * @param start the start
     * @param end the end
     * @param label a label
     */
    public void insert(long start,long end,L label) throws IntervalConflictException;

    /**
     * 获得当前对象中的标签的集合
     * @return 当前对象中标签的集合
     */
    public Set<L> labels();

    /**
     * 从当前对象中移除label标签所关联的所有时间段
     * @param label a label
     * @return 是否成功移除当前对象的label标签
     */
    public boolean remove(L label);


    /**
     * 从当前对象中获取与某个标签所关联的所有时间段
     * @param label a label
     * @return 返回形式为IntervalSe<Integer></Integer>，且它的时间段按照开始时间从小到大次序排列
     */
    public IntervalSet<Integer> intervals(L label);




}




