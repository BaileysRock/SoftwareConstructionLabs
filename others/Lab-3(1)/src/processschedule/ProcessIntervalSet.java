package processschedule;

import intervalset.CommonMultiIntervalSet;
import intervalset.Interval;
import intervalset.NonOverlapMultiIntervalSetImpl;
import intervalset.NonPeriodicMultiIntervalSetImpl;
import intervalset.exceptions.IntervalConflictException;
import intervalset.exceptions.IntervalPeriodicException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 进程调度器 ProcessIntervalSet 是一个 mutable 的 ADT
 * <p>
 * 每个时间只能有 1 个进程在执行，其他进程处于休眠状态；
 * 一个进程的执行被分为多个时间段；
 * 在特定时刻，CPU可以“闲置”，意即操作系统没有调度执行任何进程；
 * 即进程调度需要满足无时间段重叠、可以出现时间段空闲、无周期性
 */
public class ProcessIntervalSet
        extends CommonMultiIntervalSet<Process>
        implements IProcessIntervalSet {
    // Abstraction function:
    //   AF(nois, nbis, npis) =
    //      进程调度器：每个时间只能有 1 个进程在执行，
    //      其他进程处于休眠状态；一个进程的执行被分为多个时间段
    // Representation invariant:
    //   无时间段重叠、无周期性
    // Safety from rep exposure:
    //   字段使用 private 和 final 修饰

    /**
     * 无重叠时间
     */
    private final NonOverlapMultiIntervalSetImpl<Process> nois;

    /**
     * 无周期时间段委托
     */
    private final NonPeriodicMultiIntervalSetImpl<Process> npis;

    /**
     * 初始化进程调度器
     */
    public ProcessIntervalSet() {
        this.multiIntervalMap = new HashMap<>();

        // 设置委托关系
        this.nois = new NonOverlapMultiIntervalSetImpl<>(this.multiIntervalMap);
        this.npis = new NonPeriodicMultiIntervalSetImpl<>(this.multiIntervalMap);

        checkRep();
    }

    @Override
    public void insert(long start, long end, Process label, boolean finish)
            throws IntervalConflictException, IntervalPeriodicException {
        if (!checkNoOverlap(start, end)) {
            throw new IntervalConflictException();
        }

        if (multiIntervalMap.containsKey(label)) {
            multiIntervalMap.get(label).add(new Interval(start, end, false));
        } else {
            ArrayList<Interval> arrayList = new ArrayList<>();
            arrayList.add(new Interval(start, end, false));
            this.multiIntervalMap.put(label, arrayList);
        }

        if (!checkNoOverlap()) {
            throw new IntervalConflictException();
        } else if (!checkNoPeriodic()) {
            throw new IntervalPeriodicException();
        }
    }

    @Override
    public boolean checkNoOverlap() {
        return this.nois.checkNoOverlap();
    }

    @Override
    public boolean checkNoOverlap(long start, long end) {
        return this.nois.checkNoOverlap(start, end);
    }

    @Override
    public boolean checkNoPeriodic() {
        return this.npis.checkNoPeriodic();
    }

    /**
     * 检查 RI 是否为 true
     * <p>
     * 无时间段重叠，无周期性
     */
    private void checkRep() {
        assert !checkNoOverlap();
        assert !checkNoPeriodic();
    }
}
