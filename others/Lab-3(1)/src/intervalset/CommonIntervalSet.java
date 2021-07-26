package intervalset;

import java.util.Map;
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
    protected Map<L, Interval> intervalMap;

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
            assert this.intervalMap.get(lable).getStart() > 0;
            assert this.intervalMap.get(lable).getStart()
                    < this.intervalMap.get(lable).getEnd();
        }
    }

    /**
     * 将对象内容表示为人容易理解的文本字符串形式
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (L lable : this.intervalMap.keySet()) {
            stringBuilder
                    .append(lable.toString())
                    .append(": ")
                    .append(this.intervalMap.get(lable).toString())
                    .append("\n");
        }

        checkRep();
        return stringBuilder.toString();
    }

    @Override
    public Set<L> labels() {
        return this.intervalMap.keySet();
    }

    @Override
    public void insert(long start, long end, boolean periodic, L label) {
        this.intervalMap.put(label, new Interval(start, end, periodic));
    }

    @Override
    public boolean remove(L label) {
        return this.intervalMap.remove(label) != null;
    }

    @Override
    public long start(L label) {
        return this.intervalMap.get(label).getStart();
    }

    @Override
    public long end(L label) {
        return this.intervalMap.get(label).getEnd();
    }
}
