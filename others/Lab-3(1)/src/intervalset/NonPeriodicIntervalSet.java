package intervalset;

/**
 * NonPeriodicIntervalSet 是一个 mutable 的 ADT，
 * 用于在 IntervalSet 的基础上，添加不可出现周期“时间段”的性质
 * 每个时间段附着一个特定的标签，
 * 标签类型为 L，是 immutable 的
 *
 * @param <L> 时间段标签类型，需要是 immutable
 */
public interface NonPeriodicIntervalSet<L> {
    /**
     * 检查没有周期时间段
     *
     * @return 无周期则返回 true，否则返回 false
     */
    public boolean checkNoPeriodic();
}
