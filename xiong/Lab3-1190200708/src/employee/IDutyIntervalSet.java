package employee;

import intervalset.NoBlankIntervalSet;
import intervalset.NonOverlapIntervalSet;
import intervalset.NonPeriodicIntervalSet;
import intervalset.exception.IntervalBlankException;
import intervalset.exception.IntervalConflictException;
import intervalset.exception.IntervalPeriodicException;

public interface IDutyIntervalSet<L> extends NonOverlapIntervalSet<L>, NoBlankIntervalSet<L>, NonPeriodicIntervalSet<L> {


    /**
     * 插入新的标签及其对应的时间段
     * @param start 新插入时间段的起始时间
     * @param end 新插入时间段的截止时间
     * @param label 插入时间段对应标签
     * @param finish 是否为最后依次插入
     * @throws IntervalConflictException 时间段重叠的异常
     * @throws IntervalBlankException 空白时间的异常
     * @throws IntervalPeriodicException 存在周期性的异常
     * @return 返回是否插入成功
     */
    public boolean insert(long start, long end, L label, boolean finish) throws IntervalConflictException, IntervalBlankException, IntervalPeriodicException;

}
