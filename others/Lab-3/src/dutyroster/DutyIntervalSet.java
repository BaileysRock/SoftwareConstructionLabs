package dutyroster;

import intervalset.NoBlankIntervalSet;
import intervalset.NoOverlapIntervalSet;
import intervalset.IntervalSet;

/**
 * 值班时间表 DutyIntervalSet 是一个 mutable 的 ADT，
 * <p>
 * 每天只能安排唯一的 1 个员工在单位值班，且不能出现某天无人值班的情况，
 * 即值班时间表需要满足无时间段重叠、无时间段空闲、无周期性；
 * 值班表内需要记录员工的名字、职位、手机号码
 */
public class DutyIntervalSet<L> extends NoBlankIntervalSet<L> {
    // Abstraction function:
    //   AF() = 值班时间表：每天只能安排唯一的 1 个员工在单位值班，
    //          不能出现某天无人值班的情况，没有周期性
    // Representation invariant:
    //   无时间段重叠、无时间段空闲、无周期性
    // Safety from rep exposure:
    //   字段使用 private 和 final 修饰

    public DutyIntervalSet(long start, long end) {
        super(
                start,
                end,
                new NoOverlapIntervalSet<>(IntervalSet.empty())
        );
    }
}
