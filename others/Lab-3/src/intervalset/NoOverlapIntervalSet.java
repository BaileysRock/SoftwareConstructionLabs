package intervalset;

import java.util.HashMap;
import java.util.Map;

import intervalset.IntervalSetDelegation;
import intervalset.exceptions.IntervalConflictException;
import intervalset.IntervalSet;
import intervalset.Interval;

/**
 * 不允许时间段重叠装饰器
 */
public class NoOverlapIntervalSet<L> extends IntervalSetDelegation<L> {
    // Abstraction function:
    //   AF() = 一组在时间轴上分布的时间段，
    //          每个时间段附着一个不重复的标签，
    //          时间轴上不允许有重叠的时间段
    // Representation invariant:
    //   时间段的开始时间 > 结束时间
    // Safety from rep exposure:
    //   字段使用 private 和 final 修饰

    /**
     * 标签到时间段的映射
     */
    private final Map<L, Interval> intervals = new HashMap<>();

    /**
     * 初始化不允许时间段空闲装饰器
     *
     * @param intervalSet 初始时间段
     */
    public NoOverlapIntervalSet(IntervalSet<L> intervalSet) {
        super(intervalSet);

        checkRep();
    }

    /**
     * 检查 RI 是否为 true
     * <p>
     * 时间段的开始时间 > 结束时间，
     * 无时间段重叠
     */
    private void checkRep() {
        for (L label1 : intervals.keySet()) {
            assert intervals.get(label1).getStart() <
                    intervals.get(label1).getEnd();
            for (L label2 : intervals.keySet()) {
                // 时间段不重叠
                assert label1.equals(label2) ||
                        intervals.get(label1).getStart() >=
                                intervals.get(label2).getEnd() ||
                        intervals.get(label2).getStart() >=
                                intervals.get(label1).getEnd();
            }
        }
    }

    @Override
    public void insert(long start, long end, L label)
            throws IntervalConflictException {
        // 先尝试插入
        super.insert(start, end, label);

        for (L nowLabel : intervals.keySet()) {
            if (nowLabel.equals(label)) {
                continue;
            }
            Interval interval = intervals.get(nowLabel);
            if (start < interval.getEnd() &&
                    end > interval.getStart()) {
                // 插入的时间段在别的时间段内部
                super.remove(label);
                throw new IntervalConflictException();
            }
        }

        intervals.put(label, new Interval(start, end));

        checkRep();
    }
}
