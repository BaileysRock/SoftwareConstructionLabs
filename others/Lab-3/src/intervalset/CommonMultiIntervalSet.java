package intervalset;

import intervalset.exceptions.IntervalConflictException;

import java.util.*;

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
     * 可重叠集合视为若干个不可重叠集合
     */
    private ArrayList<IntervalSet<L>> intervalSetList;

    /**
     * 初始化空对象
     */
    public CommonMultiIntervalSet() {
        this.intervalSetList = new ArrayList<>();
    }

    /**
     * 根据 initial 数据创建非空对象
     *
     * @param initial 初始数据
     */
    public CommonMultiIntervalSet(IntervalSet<L> initial) {
        intervalSetList = new ArrayList<>(Collections.singletonList(initial));
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
        int index = -1;
        boolean conflict = false;
        for (int i = 0; i < intervalSetList.size(); i++) {
            IntervalSet<L> set = intervalSetList.get(i);
            if (!set.labels().contains(label)) {
                index = i;
            }

            for (L edLabel : set.labels()) {
                long edStart = set.start(edLabel);
                long edEnd = set.end(edLabel);
                if (isTimeInInterval(start, edStart, edEnd)
                        || isTimeInInterval(edStart, start, end)) {
                    index = -1;
                }

                if (label.equals(edLabel) &&
                        edStart == start &&
                        edEnd == end) {
                    conflict = true;
                }
            }
        }

        if (!conflict) {
            if (index == -1) {
                // 新建 IntervalSet
                IntervalSet<L> intervalSet = IntervalSet.empty();
                intervalSet.insert(start, end, label);
                intervalSetList.add(intervalSet);
            } else {
                intervalSetList.get(index).insert(start, end, label);
            }
        }
    }

    @Override
    public Set<L> labels() {
        Set<L> set = new HashSet<>();
        for (IntervalSet<L> intervalSet : intervalSetList) {
            set.addAll(intervalSet.labels());
        }
        return set;
    }

    @Override
    public boolean remove(L label) {
        boolean found = false;
        for (IntervalSet<L> set : intervalSetList) {
            if (set.remove(label)) {
                found = true;
            }
        }
        return found;
    }

    @Override
    public IntervalSet<Integer> intervals(L label)
            throws NoSuchElementException {
        IntervalSet<Integer> intervalSet = IntervalSet.empty();
        ArrayList<Interval> arrayList = new ArrayList<>();
        for (IntervalSet<L> set : intervalSetList) {
            if (set.labels().contains(label)) {
                arrayList.add(
                        new Interval(set.start(label), set.end(label))
                );
            }
        }
        if (arrayList.size() == 0) {
            throw new NoSuchElementException();
        }

        sortArrayListByStart(arrayList);

        try {
            for (int i = 0; i < arrayList.size(); i++) {
                intervalSet.insert(
                        arrayList.get(i).getStart(),
                        arrayList.get(i).getEnd(),
                        i
                );
            }
        } catch (IntervalConflictException ignored) {
        }

        return intervalSet;
    }

    /**
     * 对一个 ArrayList<Interval> 列表结构，
     * 按照 Interval 中的 start 时间，
     * 从小到大对 Interval 排序
     *
     * @param arrayList 列表
     */
    private void sortArrayListByStart(ArrayList<Interval> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.size() - 1; j++) {
                if (arrayList.get(j).getStart() >
                        arrayList.get(j + 1).getStart()) {
                    Interval tmp = new Interval(
                            arrayList.get(j).getStart(),
                            arrayList.get(j).getEnd()
                    );
                    arrayList.set(j, arrayList.get(j + 1));
                    arrayList.set(j + 1, tmp);
                }
            }
        }
    }
}
