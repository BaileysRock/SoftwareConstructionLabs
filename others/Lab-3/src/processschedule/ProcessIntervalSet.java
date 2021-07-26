package processschedule;

import intervalset.NoOverlapIntervalSet;
import intervalset.CommonMultiIntervalSet;
import intervalset.IntervalSet;

/**
 * 进程调度器 ProcessIntervalSet 是一个 mutable 的 ADT
 * <p>
 * 每个时间只能有 1 个进程在执行，其他进程处于休眠状态；
 * 一个进程的执行被分为多个时间段；
 * 在特定时刻，CPU可以“闲置”，意即操作系统没有调度执行任何进程；
 * 即进程调度需要满足无时间段重叠、可以出现时间段空闲、无周期性
 */
public class ProcessIntervalSet<L>
        extends CommonMultiIntervalSet<L> {
    // Abstraction function:
    //   AF(nois, nbis, npis) =
    //      进程调度器：每个时间只能有 1 个进程在执行，
    //      其他进程处于休眠状态；一个进程的执行被分为多个时间段
    // Representation invariant:
    //   无时间段重叠、无周期性
    // Safety from rep exposure:
    //   字段使用 private 和 final 修饰

    /**
     * 初始化空进程调度器
     */
    public ProcessIntervalSet() {
        this(IntervalSet.empty());
    }

    /**
     * 根据 initial 信息初始化进程调度器
     *
     * @param initial 初始信息
     */
    public ProcessIntervalSet(IntervalSet<L> initial) {
        super(new NoOverlapIntervalSet<>(initial));
    }
}
