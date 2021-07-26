package intervalset;

import intervalset.exceptions.IntervalConflictException;

/**
 * 开启时间段周期性装饰器
 */
public class PeriodicMultiIntervalSet<L>
        extends MultiIntervalSetDelegation<L> {
    // Abstraction function:
    //   AF() = 一组在时间轴上分布的时间段，
    //          每个时间段附着一个标签
    // Representation invariant:
    //   时间段的开始时间 > 结束时间，
    //   时间段周期 > 0
    // Safety from rep exposure:
    //   字段使用 private 和 final 修饰

    /**
     * 时间段周期
     */
    private final long period;

    /**
     * 初始化时间段周期装饰器
     *
     * @param period           周期
     * @param multiIntervalSet 初始时间段
     */
    public PeriodicMultiIntervalSet(
            long period,
            MultiIntervalSet<L> multiIntervalSet
    ) {
        super(multiIntervalSet);
        this.period = period;

        checkRep();
    }

    /**
     * 检查 RI 是否为 true
     * <p>
     * 时间段周期 > 0
     */
    private void checkRep() {
        assert this.period > 0;
    }

    @Override
    public void insert(long start, long end, L label)
            throws IntervalConflictException {
        long startMod = start % this.period;
        long endMod = end % this.period;

        if (endMod < startMod) {
            super.insert(0, endMod, label);
            super.insert(startMod, this.period, label);
        } else {
            super.insert(startMod, endMod, label);
        }

        checkRep();
    }
}
