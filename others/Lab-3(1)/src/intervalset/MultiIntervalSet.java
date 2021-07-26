package intervalset;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * MultiIntervalSet 是一个 mutable 的 ADT，
 * 其描述了一组在时间轴上分布的“时间段”，
 * 每个时间段附着一个标签，
 * 一个标签可以对应多个时间段，
 * 标签类型为 L，是 immutable 的
 *
 * @param <L> 标签的类型，需要是 immutable
 */
public interface MultiIntervalSet<L> {
    /**
     * 创建一个空对象
     *
     * @return 返回创建的空对象
     */
    public static <L> MultiIntervalSet<L> empty() {
        return new CommonMultiIntervalSet<>();
    }

    /**
     * 创建一个非空对象
     *
     * @param initial 数据来源
     * @return 返回创建的非空对象
     */
    public static <L> MultiIntervalSet<L> init(IntervalSet<L> initial) {
        return new CommonMultiIntervalSet<>(initial);
    }

    /**
     * @return 获得当前对象中的标签集合
     */
    public Set<L> labels();

    /**
     * 从当前对象中移除某个标签所关联的时间段
     *
     * @param label 移除时间段的标签
     * @return 若集合中没有找到指定的标签则返回 false；成功删除则返回 true
     */
    public boolean remove(L label);

    /**
     * 从当前对象中获取与某个标签所关联的所有时间段，
     * 返回的时间段按开始时间从小到大的次序排列；
     * <p>
     * 例如：当前对象为 {"A" = [[0, 10], [20, 30]], "B" = [[10, 20]]}，
     * 那么 intervals("A") 返回的结果是{0 = [0, 10], 1 = [20, 30] }
     *
     * @param label 搜索的标签
     * @return 返回与 label 所关联的所有时间段
     */
    public IntervalSet<Integer> intervals(L label);

    /**
     * @return 获取标签到时间段数组的映射
     */
    public Map<L, ArrayList<Interval>> getmultiIntervalMap();
}
