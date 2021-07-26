package intervalset;

import java.util.HashSet;
import java.util.Set;

/**
 * 不允许时间段空闲装饰器
 */
public class NoBlankIntervalSet<L> extends IntervalSetDelegation<L> {
    // Abstraction function:
    //   AF() = 一组在时间轴上分布的时间段，
    //          每个时间段附着一个不重复的标签，
    //          时间轴上不允许有空闲的时间段
    // Representation invariant:
    //   时间段的开始时间 > 结束时间
    // Safety from rep exposure:
    //   字段使用 private 和 final 修饰

    /**
     * 总的时间段开始时间
     */
    private final long startTime;

    /**
     * 总的时间段结束时间
     */
    private final long endTime;

    /**
     * 初始化不允许时间段空闲装饰器
     *
     * @param startTime   总的时间段开始时间
     * @param endTime     总的时间段结束时间
     * @param intervalSet 初始时间段
     */
    public NoBlankIntervalSet(
            long startTime,
            long endTime,
            IntervalSet<L> intervalSet
    ) {
        super(intervalSet);
        this.startTime = startTime;
        this.endTime = endTime;

        checkRep();
    }

    /**
     * 检查 RI 是否为 true
     * <p>
     * 时间段的开始时间 > 结束时间
     */
    private void checkRep() {
        assert this.endTime > this.startTime;
    }

    /**
     * @return 返回时间轴上空闲的时间段
     */
    public Set<Interval> blankIntervalSet() {
        Set<Long> blankTimeSet = new HashSet<>();
        for (long i = startTime; i < endTime; i++) {
            blankTimeSet.add(i);
        }

        for (L label : labels()) {
            for (long i = start(label); i < end(label); i++) {
                blankTimeSet.remove(i);
            }
        }

        Set<Interval> blankIntervalSet = new HashSet<>();
        long blankIntervalStart = -1;
        long blankIntervalEnd = -1;
        for (long i : blankTimeSet) {
            if (blankIntervalStart == -1) {
                blankIntervalStart = i;
            }
            if (!blankTimeSet.contains(i + 1)) {
                // 寻找连续的 time
                blankIntervalEnd = i + 1;
            }

            if (blankIntervalStart != -1 && blankIntervalEnd != -1) {
                blankIntervalSet.add(
                        new Interval(blankIntervalStart, blankIntervalEnd)
                );
                blankIntervalStart = -1;
                blankIntervalEnd = -1;
            }
        }

        checkRep();
        return blankIntervalSet;
    }
}
