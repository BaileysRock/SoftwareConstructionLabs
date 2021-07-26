package API;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import intervalset.Interval;
import intervalset.IntervalSet;
import intervalset.MultiIntervalSet;

/**
 * 通用 API
 */
public abstract class APIs {
    /**
     * 求两个 IntervalSet 的交集
     *
     * @param intervalSet1 第一个intervalset
     * @param intervalSet2 第二个intervalSet
     * @return 返回 intervalSet1, intervalSet2 的交集
     */
    private static Set<Interval> IntervalSetUnion(
            IntervalSet<Integer> intervalSet1,
            IntervalSet<Integer> intervalSet2
    ) {
        int minSize = Math.min(
                intervalSet1.labels().size(),
                intervalSet2.labels().size()
        );
        int index = 0;
        Set<Interval> newInterval = new HashSet<>();
        for (int label1 = 0; label1 < minSize; label1++) {
            long start = intervalSet1.start(label1);
            long end = intervalSet1.end(label1);
            while (index < minSize && intervalSet2.end(index) <= start) {
                index++;
            }
            while (index < minSize && intervalSet2.end(index) < end) {
                newInterval.add(
                        new Interval(
                                Math.max(
                                        start,
                                        intervalSet2.start(index)
                                ),
                                intervalSet2.end(index)
                        )
                );
                index++;
            }
            if (index >= minSize) {
                break;
            }
            if (intervalSet2.start(index) < end) {
                newInterval.add(
                        new Interval(
                                Math.max(
                                        start,
                                        intervalSet2.start(index)
                                ),
                                end
                        )
                );
            }
        }

        return newInterval;
    }

    /**
     * 求一个时间段集合中，所有时间段占用的总长度；
     * 将中间的空闲时间看作被占用
     *
     * @param set 一个时间段集合
     * @return 时间段的总长度
     */
    private static long intervalsLength(Set<Interval> set) {
        long sum = 0;
        for (Interval interval : set) {
            sum += interval.getEnd() - interval.getStart();
        }
        return sum;
    }

    /**
     * 求一个时间段集合中，所有时间段占用的总长度；
     * 将中间的空闲时间看作被占用
     *
     * @param set 一个时间段集合
     * @return 时间段的总长度
     */
    public static <L> long intervalsLength(MultiIntervalSet<L> set) {
        return getMaxEnd(set) - getMinStart(set);
    }

    /**
     * 求一个时间段集合中，第一个开头的时间点
     *
     * @param set 时间段集合
     * @return 返回第一个开头的时间点
     */
    private static <L> Long getMinStart(MultiIntervalSet<L> set) {
        long min = Long.MAX_VALUE;
        for (L label : set.labels()) {
            IntervalSet<Integer> interval = set.intervals(label);
            for (Integer i : interval.labels()) {
                if (interval.start(i) < min) {
                    min = interval.start(i);
                }
            }
        }
        return min;
    }

    /**
     * 求一个时间段集合中，最后一个结尾的时间点
     *
     * @param set 时间段集合
     * @return 返回最后一个结尾的时间点
     */
    private static <L> Long getMaxEnd(MultiIntervalSet<L> set) {
        long max = Long.MIN_VALUE;
        for (L label : set.labels()) {
            IntervalSet<Integer> interval = set.intervals(label);
            for (Integer i : interval.labels()) {
                if (interval.end(i) > max) {
                    max = interval.end(i);
                }
            }
        }
        return max;
    }

    /**
     * 计算两个 MultiIntervalSet 对象的相似度
     * <p>
     * 计算方法：
     * 按照时间轴从早到晚的次序，针对同一个时间段内两个对象里的 interval，
     * 若它们标注的 label 等价，则二者相似度为 1，否则为 0；
     * 若同一时间段内只有一个对象有 interval 或二者都没有，则相似度为 0。
     * 将各 interval 的相似度与 interval 的长度相乘后求和，除以总长度，
     * 即得到二者的整体相似度。
     *
     * @param set1 第一个intervalset
     * @param set2 第二个intervalSet
     * @return set1, set2的相似度
     */
    public static <L> double similarity(
            MultiIntervalSet<L> set1,
            MultiIntervalSet<L> set2
    ) {
        long min = Math.min(getMinStart(set1), getMinStart(set2));
        long max = Math.max(getMaxEnd(set1), getMaxEnd(set2));

        double similarity = 0.0;
        for (L label : set1.labels()) {
            if (set2.labels().contains(label)) {
                similarity += intervalsLength(
                        IntervalSetUnion(
                                set1.intervals(label),
                                set2.intervals(label)
                        )
                );
            }
        }

        similarity /= (double) (max - min);
        return similarity;
    }

    /**
     * 求一个时间段集合中，重叠时间段的比例
     *
     * @param set 时间段集合
     * @return 返回重叠时间段的比例
     */
    public static <L> double calcConflictRatio(IntervalSet<L> set) {
        return calcConflictRatio(MultiIntervalSet.init(set));
    }

    /**
     * 求一个时间段集合中，重叠时间段的比例
     *
     * @param set 时间段集合
     * @return 返回重叠时间段的比例
     */
    public static <L> double calcConflictRatio(MultiIntervalSet<L> set) {
        Set<Interval> conflictIntervals = new HashSet<>();
        for (L label1 : set.labels()) {
            for (L label2 : set.labels()) {
                if (!label1.equals(label2)) {
                    Set<Interval> intervals =
                            IntervalSetUnion(
                                    set.intervals(label1),
                                    set.intervals(label2)
                            );
                    for (Interval interval : conflictIntervals) {
                        Set<Interval> copySet = new HashSet<>(intervals);
                        for (Interval newIntervals : copySet) {
                            if (interval.getStart() < newIntervals.getEnd() &&
                                    interval.getEnd() > newIntervals.getStart()
                            ) {
                                intervals.remove(newIntervals);
                                if (newIntervals.getStart() <
                                        interval.getStart()) {
                                    intervals.add(
                                            new Interval(
                                                    newIntervals.getStart(),
                                                    interval.getStart()
                                            )
                                    );
                                }
                                if (newIntervals.getEnd() >
                                        interval.getEnd()) {
                                    intervals.add(
                                            new Interval(
                                                    interval.getEnd(),
                                                    newIntervals.getEnd()
                                            )
                                    );
                                }
                            }
                        }
                    }
                    conflictIntervals.addAll(intervals);
                }
            }
        }
        return (double)
                intervalsLength(conflictIntervals) / intervalsLength(set);
    }

    /**
     * 求一个时间段集合中，空闲时间段的比例
     *
     * @param set 时间段集合
     * @return 返回空闲时间段的比例
     */
    public static <L> double calcFreeTimeRatio(IntervalSet<L> set) {
        return calcFreeTimeRatio(MultiIntervalSet.init(set));
    }

    /**
     * 求一个时间段集合中，空闲时间段的比例
     *
     * @param set 时间段集合
     * @return 返回空闲时间段的比例
     */
    public static <L> double calcFreeTimeRatio(MultiIntervalSet<L> set) {
        long min = getMinStart(set);
        long max = getMaxEnd(set);

        Set<Interval> freeIntervals = new HashSet<>();
        freeIntervals.add(new Interval(min, max));
        for (L label : set.labels()) {
            IntervalSet<Integer> intervalSet = set.intervals(label);
            for (Integer i : intervalSet.labels()) {
                long start = intervalSet.start(i);
                long end = intervalSet.end(i);
                Set<Interval> copyIntervals = new HashSet<>(freeIntervals);
                for (Interval free : copyIntervals) {
                    if (free.getStart() < end && free.getEnd() > start) {
                        freeIntervals.remove(free);
                        if (free.getEnd() > end) {
                            freeIntervals.add(
                                    new Interval(end, free.getEnd())
                            );
                        }
                        if (free.getStart() < start) {
                            freeIntervals.add(
                                    new Interval(free.getStart(), start)
                            );
                        }
                    }
                }
            }

        }
        return (double)
                intervalsLength(freeIntervals) / intervalsLength(set);
    }

    /**
     * 获取去除头部后，得到的字符串
     *
     * @param head 字符串头部
     * @param str  整个字符串
     * @return 整个字符串除去头部字符串后的字符串
     */
    public static String removeHead(String head, String str) {
        Pattern pattern = Pattern.compile("(?<=" + head + ").+");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    /**
     * 检测值班时间表文件格式是否符合要求
     *
     * @param str 文件内容
     * @return 符合要求则返回 true，否则返回 false
     */
    public static boolean checkDutyFileFormatCorrect(String str) {
        // "Employee" 在 "Period" 之前
        Pattern pattern1 = Pattern.compile(
                "Employee\\{\\s+[\n" +
                        "\u3000(A-Za-z+){a-zA-Z\\s,\\d3}\\-4]+\n" +
                        "}\n" +
                        "Period\\{(\\d{4}-\\d{2}-\\d{2})," +
                        "(\\d{4}-\\d{2}-\\d{2})}\n" +
                        "Roster\\{\\s+[\n" +
                        "\u3000(A-Za-z+){\\d4}\\-2,]+\n" +
                        "}\n"
        );

        // "Employee" 在 "Period" 之后
        Pattern pattern2 = Pattern.compile(
                "Period\\{\\d{4}-\\d{2}-\\d{2},\\d{4}-\\d{2}-\\d{2}}\n" +
                        "Employee\\{[\n" +
                        "\u3000A-Za-z+{A-Za-z\\s,\\d3}\\-4]+\n" +
                        "}+\n" +
                        "Roster\\{\\s+[\n" +
                        "\u3000A-Za-z+{\\d4}\\-2,]+\n" +
                        "}\n"
        );

        Matcher matcher1 = pattern1.matcher(str);
        Matcher matcher2 = pattern2.matcher(str);
        return matcher1.matches() || matcher2.matches();
    }
}
