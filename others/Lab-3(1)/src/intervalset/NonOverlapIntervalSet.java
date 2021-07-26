package intervalset;

/**
 * NonOverlapIntervalSet 是一个 mutable 的 ADT，
 * 用于在 IntervalSet 的基础上，添加“时间段”不可重叠的性质
 * 每个时间段附着一个特定的标签，
 * 标签类型为 L，是 immutable 的
 *
 * @param <L> 时间段标签类型，需要是 immutable
 */
public interface NonOverlapIntervalSet<L> {
    /**
     * 在所有时间段中，检查没有重叠时间段
     *
     * @return 无重叠则返回 true，否则返回 false
     */
    public boolean checkNoOverlap();

    /**
     * 检查 [start, end) 与现有的时间段没有重叠
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 无重叠则返回 true，否则返回 false
     */
    public boolean checkNoOverlap(long start, long end);
}
