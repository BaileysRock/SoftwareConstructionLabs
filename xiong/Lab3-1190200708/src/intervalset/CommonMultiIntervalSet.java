package intervalset;

import intervalset.exception.IntervalConflictException;

import java.util.*;

public class CommonMultiIntervalSet<L> implements MultiIntervalSet<L> {

    // Abstraction function:
    // 由Map构成的映射关系
    // 即从L到ArrayList<Interval>的映射构成的IntervalSet
    // Representation invariant:
    // ArrayList中不存在重叠的时间
    // label不为空
    // Map中不存在相同的label
    // Safety from rep exposure:
    // 使用private、final修饰变量
    // 采用防御性复制


    protected Map<L, ArrayList<Interval>> MultiIntervalMap;

    public CommonMultiIntervalSet() {
        MultiIntervalMap = new HashMap<>();
    }


    public CommonMultiIntervalSet(IntervalSet<L> intervalset) {
        MultiIntervalMap = new HashMap<>();
        for (L label : intervalset.labels()) {
            ArrayList<Interval> arrayList = new ArrayList<>();
            arrayList.add(new Interval(intervalset.start(label), intervalset.end(label), false));
            MultiIntervalMap.put(label, arrayList);
        }
    }


    /**
     * 检查不变量
     * 检查label不重复
     * label对应的标签不存在重叠的时间
     */
    public void checkRep() {
        List<L> listL = new ArrayList<>();
        for (Map.Entry<L, ArrayList<Interval>> entry : MultiIntervalMap.entrySet()) {
            assert entry.getKey() != null;
            assert !(listL.contains(entry.getKey()));
            listL.add(entry.getKey());
            for (Interval interval : entry.getValue()) {
                assert interval.getStart() < interval.getEnd();

            }
        }
    }


    /**
     * 子类方法
     * 检查是否存在时间重叠
     *
     * @param start        新时间段的起始时间
     * @param end          新时间段的截止时间
     * @param intervalList Interval的列表
     * @return 是否重叠
     */
    private boolean TimeOverlapping(long start, long end, ArrayList<Interval> intervalList) {
        for (Interval interval : intervalList) {
//            if(start == interval.getStart() && end == interval.getEnd())
//                continue;
            if ((start < interval.getEnd() && end > interval.getStart()) ||
                    (interval.getStart() < end && interval.getEnd() > start)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void insert(long start, long end, L label) throws IntervalConflictException {
        // 查看是否存在label
        if (MultiIntervalMap.containsKey(label)) {
            // 当前时间段是否会重叠
            if (TimeOverlapping(start, end, MultiIntervalMap.get(label))) {
                throw new IntervalConflictException("The time is overlapping!");
            }
            MultiIntervalMap.get(label).add(new Interval(start, end));
        }
        else {
            ArrayList<Interval> intervalArrayList = new ArrayList<>();
            Interval intervalAdd = new Interval(start, end);
            intervalArrayList.add(intervalAdd);
            MultiIntervalMap.put(label, intervalArrayList);
        }
        // checkRep();
    }


    @Override
    public Set<L> labels() {
        Set<L> labelsSet = new HashSet<>();
        labelsSet.addAll(MultiIntervalMap.keySet());
        return labelsSet;
    }


    @Override
    public boolean remove(L label) {
        return MultiIntervalMap.remove(label) != null;
    }

    @Override
    public IntervalSet<Integer> intervals(L label) {
        ArrayList<Interval> intervalArrayList = MultiIntervalMap.get(label);
        IntervalSet<Integer> IntegerIntervalSet = IntervalSet.empty();
        // 对List按照从小到大排序
        intervalArrayList.sort(new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                if (o1.getStart() < o2.getStart())
                    return -1;
                else if (o1.getStart() > o2.getStart())
                    return 1;
                else
                    return 0;
            }
        });
        for (int i = 0; i < intervalArrayList.size(); i++) {
            try {
                IntegerIntervalSet.insert(intervalArrayList.get(i).getStart(), intervalArrayList.get(i).getEnd(), i);
            } catch (IntervalConflictException e) {
                e.printStackTrace();
            }
        }
        return IntegerIntervalSet;


    }


}
