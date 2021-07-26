package dutyroster;

import intervalset.*;
import intervalset.exceptions.IntervalBlankException;
import intervalset.exceptions.IntervalConflictException;
import intervalset.exceptions.IntervalPeriodicException;

import java.util.HashMap;

/**
 * 值班时间表 DutyIntervalSet 是一个 mutable 的 ADT，
 * <p>
 * 每天只能安排唯一的 1 个员工在单位值班，且不能出现某天无人值班的情况，
 * 即值班时间表需要满足无时间段重叠、无时间段空闲、无周期性；
 * 值班表内需要记录员工的名字、职位、手机号码
 */
public class DutyIntervalSet
        extends CommonIntervalSet<Employee>
        implements IDutyIntervalSet {
    // Abstraction function:
    //   AF(nois, nbis, npis) =
    //      值班时间表：每天只能安排唯一的 1 个员工在单位值班，
    //      不能出现某天无人值班的情况，没有周期性
    // Representation invariant:
    //   无时间段重叠、无时间段空闲、无周期性
    // Safety from rep exposure:
    //   字段使用 private 和 final 修饰

    /**
     * 排班时间开始日期
     */
    private final long start;

    /**
     * 排班时间结束日期
     */
    private final long end;

    /**
     * 无重叠时间段委托
     */
    private final NonOverlapIntervalSetImpl<Employee> nois;

    /**
     * 无空闲时间段委托
     */
    private final NoBlankIntervalSetImpl<Employee> nbis;

    /**
     * 无周期时间段委托
     */
    private final NonPeriodicIntervalSetImpl<Employee> npis;

    /**
     * 初始化值班时间表
     */
    public DutyIntervalSet(long start, long end) {
        this.intervalMap = new HashMap<>();

        // 设置委托关系
        this.nois = new NonOverlapIntervalSetImpl<>(this.intervalMap);
        this.nbis = new NoBlankIntervalSetImpl<>(this.intervalMap);
        this.npis = new NonPeriodicIntervalSetImpl<>(this.intervalMap);

        // 设置时间表的开始和结束日期
        this.start = start;
        this.end = end;

        checkRep();
    }

    @Override
    public void insert(long start, long end, Employee label, boolean finish)
            throws
            IntervalConflictException,
            IntervalBlankException,
            IntervalPeriodicException {
        if (!checkNoOverlap(start, end)) {
            throw new IntervalConflictException();
        }

        this.intervalMap.put(label, new Interval(start, end, false));

        if (!checkNoOverlap()) {
            throw new IntervalConflictException();
        } else if (!checkNoPeriodic()) {
            throw new IntervalPeriodicException();
        } else if (finish && !checkNoBlank(this.start, this.end)) {
            throw new IntervalBlankException();
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
    public boolean checkNoBlank(long start, long end) {
        return this.nbis.checkNoBlank(start, end);
    }

    @Override
    public boolean checkNoPeriodic() {
        return this.npis.checkNoPeriodic();
    }

    /**
     * 检查 RI 是否为 true
     * <p>
     * 无时间段重叠，
     * 无时间段空闲，
     * 无周期性
     */
    private void checkRep() {
        assert !checkNoOverlap();
        assert !checkNoBlank(this.start, this.end);
        assert !checkNoPeriodic();
    }
}
