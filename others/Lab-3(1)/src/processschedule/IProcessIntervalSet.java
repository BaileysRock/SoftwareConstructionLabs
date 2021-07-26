package processschedule;

import intervalset.NonOverlapIntervalSet;
import intervalset.NonPeriodicIntervalSet;
import intervalset.exceptions.IntervalConflictException;
import intervalset.exceptions.IntervalPeriodicException;

/**
 * 进程调度器接口
 */
public interface IProcessIntervalSet extends
        NonOverlapIntervalSet<Process>,
        NonPeriodicIntervalSet<Process> {
    /**
     * 在当前对象中插入新的时间段和标签
     *
     * @param start  开始时间
     * @param end    结束时间
     * @param label  标签
     * @param finish 是否为最后一次插入
     * @throws IntervalConflictException 出现重叠时间段
     * @throws IntervalPeriodicException 出现周期时间段
     */
    public void insert(long start, long end, Process label, boolean finish)
            throws IntervalConflictException, IntervalPeriodicException;
}
