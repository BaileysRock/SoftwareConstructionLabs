package intervalset;

/**
 * NoBlankIntervalSet 是一个 mutable 的 ADT，
 * 用于在 IntervalSet 的基础上，添加不可出现空闲“时间段”的性质
 * 每个时间段附着一个特定的标签，
 * 标签类型为 L，是 immutable 的
 *
 * @param <L> 时间段标签类型，需要是 immutable
 */
public interface NoBlankIntervalSet<L> {
    /**
     * 检查没有空白时间段
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 无空白则返回 true，否则返回 false
     */
    public boolean checkNoBlank(long start, long end);
}
