package intervalset;

import java.util.Set;

/**
 * IntervalSet 是一个 mutable 的 ADT，
 * 其描述了一组在时间轴上分布的“时间段”，
 * 每个时间段附着一个特定的标签，且标签不重复，
 * 标签类型为 L，是 immutable 的
 *
 * @param <L> 标签的类型，需要是 immutable
 */
public interface IntervalSet<L> {
    /**
     * 创建一个空对象
     *
     * @return 返回创建的空对象
     */
    public static <L> IntervalSet<L> empty() {
        return new CommonIntervalSet<>();
    }

    /**
     * @return 获得当前对象中的标签集合
     */
    public Set<L> labels();

    /**
     * 插入新时间段
     *
     * @param start    开始时间
     * @param end      停止时间
     * @param periodic 是否周期
     * @param label    时间段标签
     */
    public void insert(long start, long end, boolean periodic, L label);

    /**
     * 从当前对象中移除某个标签所关联的时间段
     *
     * @param label 移除时间段的标签
     * @return 若集合中没有找到指定的标签则返回 false；成功删除则返回 true
     */
    public boolean remove(L label);

    /**
     * @param label 标签
     * @return 返回某个标签对应的时间段的开始时间
     */
    public long start(L label);

    /**
     * @param label 标签
     * @return 返回某个标签对应的时间段的结束时间
     */
    public long end(L label);
}
