package intervalset;

import intervalset.exceptions.IntervalConflictException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * 对 IntervalSet 接口的实现
 */
public class CommonIntervalSet<L> implements IntervalSet<L> {
    // Abstraction function:
    //   AF() = 一组在时间轴上分布的时间段，每个时间段附着一个不重复的标签
    // Representation invariant:
    //   所有时间段的开始时间是非负整数，每个标签不重复且非空
    // Safety from rep exposure:
    //   字段使用 private 和 final 修饰，
    //   同时在赋值和返回时使用防御性复制

    /**
     * 标签到时间段的映射
     */
    private final Map<L, Interval> intervalMap = new HashMap<>();

    /**
     * 检查 RI 是否为 true
     * <p>
     * 所有时间段的开始时间是非负整数，
     * 所有时间段的开始时间 < 结束时间，
     * 每个标签不重复（已经隐含在 Map），
     * 每个标签非空
     */
    private void checkRep() {
        for (L lable : this.intervalMap.keySet()) {
            assert lable != null;
            assert this.intervalMap.get(lable).getStart() >= 0;
            assert this.intervalMap.get(lable).getStart()
                    < this.intervalMap.get(lable).getEnd();
        }
    }

    /**
     * 判断 time 是否处于 [start, end) 的时间段中
     *
     * @param time  时间
     * @param start 时间段起点
     * @param end   时间段终点
     * @return 在时间段中则返回 true，否则 false
     */
    private boolean isTimeInInterval(long time, long start, long end) {
        return time >= start && time < end;
    }

    @Override
    public void insert(long start, long end, L label)
            throws IntervalConflictException {
        for (L key : intervalMap.keySet()) {
            long entry_start = intervalMap.get(key).getStart();
            long entry_end = intervalMap.get(key).getEnd();
            if (key.equals(label)) {
                if (start != entry_start || end != entry_end) {
                    // 出现冲突标签
                    throw new IntervalConflictException();
                } else {
                    checkRep();
                    return;
                }
            } else {
                if (isTimeInInterval(start, entry_start, entry_end)
                        || isTimeInInterval(entry_start, start, end)) {
                    // 出现重叠的区间
                    throw new IntervalConflictException();
                }
            }
        }
        intervalMap.put(
                label,
                new Interval(start, end)
        );
        checkRep();
    }

    @Override
    public Set<L> labels() {
        return this.intervalMap.keySet();
    }

    @Override
    public boolean remove(L label) {
        return this.intervalMap.remove(label) != null;
    }

    @Override
    public long start(L label) throws NoSuchElementException {
        if (!intervalMap.containsKey(label)) {
            throw new NoSuchElementException();
        }
        return intervalMap.get(label).getStart();
    }

    @Override
    public long end(L label) throws NoSuchElementException {
        if (!intervalMap.containsKey(label)) {
            throw new NoSuchElementException();
        }
        return intervalMap.get(label).getEnd();

    }

    @Override
    public String toString() {
        return intervalMap.toString();
    }
}