package intervalset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 对 MultiIntervalSet 接口的实现
 */
public class CommonMultiIntervalSet<L> implements MultiIntervalSet<L> {
    // Abstraction function:
    //   AF() = 一组在时间轴上分布的时间段，每个时间段附着一个标签
    // Representation invariant:
    //   所有时间段的开始时间是非负整数，每个标签且非空
    // Safety from rep exposure:
    //   字段使用 private 和 final 修饰，
    //   同时在赋值和返回时使用防御性复制

    /**
     * 标签到时间段数组的映射
     */
    protected Map<L, ArrayList<Interval>> multiIntervalMap;

    /**
     * 初始化空对象
     */
    public CommonMultiIntervalSet() {
        multiIntervalMap = new HashMap<>();
    }

    /**
     * 根据 initial 数据创建非空对象
     *
     * @param initial 初始数据
     */
    public CommonMultiIntervalSet(IntervalSet<L> initial) {
        for (L label : initial.labels()) {
            ArrayList<Interval> arrayList = new ArrayList<>();
            arrayList.add(
                    new Interval(
                            initial.start(label),
                            initial.end(label),
                            false
                    )
            );
            multiIntervalMap.put(label, arrayList);
        }
    }

    /**
     * 检查 RI 是否为 true
     * <p>
     * 所有时间段的开始时间是非负整数，
     * 所有时间段的开始时间 < 结束时间，
     * 每个标签非空
     */
    private void checkRep() {
        for (L lable : multiIntervalMap.keySet()) {
            assert lable != null;
            ArrayList<Interval> multiInterval = multiIntervalMap.get(lable);
            for (Interval interval : multiInterval) {
                assert interval.getStart() > 0;
                assert interval.getStart() < interval.getEnd();
            }
        }
    }

    /**
     * 将对象内容表示为人容易理解的文本字符串形式
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (L lable : multiIntervalMap.keySet()) {
            stringBuilder.append(lable.toString()).append(": ");
            ArrayList<Interval> multiInterval = multiIntervalMap.get(lable);
            for (Interval interval : multiInterval) {
                stringBuilder.append(interval.toString()).append("\n");
            }
        }

        checkRep();
        return stringBuilder.toString();
    }

    @Override
    public Set<L> labels() {
        return multiIntervalMap.keySet();
    }

    @Override
    public boolean remove(L label) {
        return this.multiIntervalMap.remove(label) != null;
    }

    @Override
    public IntervalSet<Integer> intervals(L label) {
        IntervalSet<Integer> intervalSet = IntervalSet.empty();

        ArrayList<Interval> arrayList = multiIntervalMap.get(label);
        for (int i = 0; i < arrayList.size(); i++) {
            Interval interval = arrayList.get(i);
            intervalSet.insert(
                    interval.getStart(),
                    interval.getEnd(),
                    interval.isPeriodic(),
                    i
            );
        }

        return intervalSet;
    }

    @Override
    public Map<L, ArrayList<Interval>> getmultiIntervalMap() {
        return new HashMap<>(multiIntervalMap);
    }
}
