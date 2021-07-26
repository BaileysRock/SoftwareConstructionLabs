package API;

import intervalset.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class APIs {



    /**
     * s1与s2的重合时间段
     *
     * @param s1 由MultiIntervalSet<L></L>的label对应的多个Interval构成的Interval<Integer></Integer>，其中Integer为从小到大的顺序
     * @param s2 由MultiIntervalSet<L></L>的label对应的多个Interval构成的Interval<Integer></Integer>，其中Integer为从小到大的顺序
     * @return s1, s2的重合的时间段
     */
    public static Set<Interval> similarIntervals(IntervalSet<Integer> s1, IntervalSet<Integer> s2) {
        Set<Interval> IntervalSimilar = new HashSet<>();
        long startS1;
        long endS1;
        long startS2;
        long endS2;
        // 小的标签，指A1、A2、A3
        for (Integer IntegerLabel : s1.labels()) {
            startS1 = s1.start(IntegerLabel);
            endS1 = s1.end(IntegerLabel);
            for (Integer IntegerLabelS2 : s2.labels()) {
                startS2 = s2.start(IntegerLabelS2);
                endS2 = s2.end(IntegerLabelS2);
                if (startS2 >= endS1 || endS2 <= startS1) {
                    continue;
                } else {
                    //开始时间取两者间大的
                    long target_start = Math.max(startS1, startS2);
                    //结束时间取两者间小的
                    long target_end = Math.min(endS1, endS2);
                    IntervalSimilar.add(new Interval(target_start, target_end));
                }
            }
        }
        return IntervalSimilar;
    }


    /**
     * 计算s1中的时间的长度
     *
     * @param s1 Set<Interval></Interval>
     * @return s1中各个时间段的长度和
     */
    public static double intervalLength(Set<Interval> s1) {
        double timeSimilarity = 0;
        for (Interval intervals : s1) {
            timeSimilarity += intervals.getEnd() - intervals.getStart();
        }
        return timeSimilarity;
    }


    /**
     * 获得对于MultiIntervalSet的最后的时间
     *
     * @param s   MultiIntervalSet<L></L>
     * @param <L> 泛型
     * @return 返回最后的时间
     */
    public static <L> long getEndTimeShaft(MultiIntervalSet<L> s) {
        long max = Long.MIN_VALUE;
        for (L label : s.labels()) {
            IntervalSet<Integer> interval = s.intervals(label);
            for (Integer IntegerLabel : interval.labels()) {
                if (interval.end(IntegerLabel) > max)
                    max = interval.end(IntegerLabel);
            }
        }
        return max;
    }

    /**
     * 获得对于MultiIntervalSet的最早的时间
     *
     * @param s   MultiIntervalSet<L></L>
     * @param <L> 泛型
     * @return 返回最早的时间
     */
    public static <L> long getStartTimerShaft(MultiIntervalSet<L> s) {
        long min = Long.MAX_VALUE;
        for (L label : s.labels()) {
            IntervalSet<Integer> interval = s.intervals(label);
            for (Integer IntegerLabel : interval.labels()) {
                if (interval.start(IntegerLabel) < min)
                    min = interval.start(IntegerLabel);
            }
        }
        return min;
    }


    /**
     * 计算相似度
     *
     * @param s1  MultiIntervalSet<L></L>
     * @param s2  MultiIntervalSet<L></L>
     * @param <L> 泛型
     * @return 相似度比例
     */
    public static <L> double Similarity(MultiIntervalSet<L> s1, MultiIntervalSet<L> s2) {
        long startTimerShaft = Math.min(getStartTimerShaft(s1), getStartTimerShaft(s2));
        long endTimerShaft = Math.max(getEndTimeShaft(s1), getEndTimeShaft(s2));
        double lastTime = (double) endTimerShaft - (double) startTimerShaft;

        if (lastTime == 0)
            return 0;
        double timeSimilarity = 0;
        // 大的标签
        // 指ABC
        for (L label : s1.labels()) {
            if (s2.labels().contains(label)) {
                timeSimilarity += intervalLength(similarIntervals(s1.intervals(label), s2.intervals(label)));
            }
        }
        double ratio = timeSimilarity / lastTime;
        return ratio;
    }


    public static <L> double calcConflictRatio(IntervalSet<L> set) {
        return calcConflictRatio(MultiIntervalSet.init(set));
    }

    public static <L> double calcConflictRatio(MultiIntervalSet<L> set) {
        Set<Interval> conflict = new HashSet<>();//用来记录冲突的时间段
        Map<L, Boolean> visitedLabel = new HashMap<>();//用来记录某个label之前是否已经被遍历过
        Set<L> labelSet = set.labels();
        for (L label : labelSet) {
            visitedLabel.put(label, false);
        }//初始化都设置为false
        for (L label1 : labelSet) {
            visitedLabel.put(label1, true);
            for (L label2 : labelSet) {
                if (label1.equals(label2) || visitedLabel.get(label2)) {
                    //如果两个标签相等 或 label2已经访问过了
                    continue;
                } else {
                    Set<Interval> currentConflict = similarIntervals(set.intervals(label1), set.intervals(label2));//利用之前的函数记录重合的Interval
                    conflict.addAll(currentConflict);//加入conflict中
                }
            }
        }
        
        double conflictTime = intervalLength(conflict);//计算冲突的时长
        double timeALL = 0;//计算总的时长
        for (L label : labelSet) {
            IntervalSet<Integer> intervalIntegerSet = set.intervals(label);
            for (Integer i : intervalIntegerSet.labels()) {
                timeALL += intervalIntegerSet.end(i) - intervalIntegerSet.start(i);
            }
        }
        return (double) conflictTime / timeALL;
    }


    public static <L> double calcFreeTimeRatio(IntervalSet<L> set) {
        return calcFreeTimeRatio(MultiIntervalSet.init(set));
    }

    public static <L> double calcFreeTimeRatio(MultiIntervalSet<L> set) {
        long min = getStartTimerShaft(set);
        long max = getEndTimeShaft(set);
        double FreeTime = 0.0;
        Set<Interval> FreeInterval = new HashSet<Interval>();
        Set<Interval> FreeIntervalAll = new HashSet<Interval>();
        Interval freeAll = new Interval(min, max);
        FreeInterval.add(freeAll);
        FreeIntervalAll.add(freeAll);
        for (L label : set.labels()) {
            IntervalSet<Integer> intervalSet = set.intervals(label);
            for (Integer i : intervalSet.labels()) {
                long start = intervalSet.start(i);
                long end = intervalSet.end(i);
                for (Interval free : FreeInterval) {
                    if (free.getStart() >= end || free.getEnd() <= start)
                        continue;
                    else {
                        FreeInterval.remove(free);
                        if (free.getEnd() > end)
                            FreeInterval.add(new Interval(end, free.getEnd()));
                        if (free.getStart() < start)
                            FreeInterval.add(new Interval(free.getStart(), start));
                    }
                }
            }
        }
        FreeTime = intervalLength(FreeInterval) / intervalLength(FreeIntervalAll);
        return FreeTime;

    }

    /**
     * 检验从文件读取出的串是否符合要求
     *
     * @param string 从文件中读取出的字符串
     * @return 是否满足要求
     */
    public static boolean checkFileFormat(String string) {
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
        Pattern pattern2 = Pattern.compile(
                "Period\\{\\d{4}-\\d{2}-\\d{2},\\d{4}-\\d{2}-\\d{2}}\n" +
                        "Employee\\{[\n" +
                        "\u3000A-Za-z+{A-Za-z\\s,\\d3}\\-4]+\n" +
                        "}+\n" +
                        "Roster\\{\\s+[\n" +
                        "\u3000A-Za-z+{\\d4}\\-2,]+\n" +
                        "}\n"
        );
        Matcher matcher1 = pattern1.matcher(string);
        Matcher matcher2 = pattern2.matcher(string);
        return matcher1.matches() || matcher2.matches();
    }

    public static String removeStringHead(String StringHead, String str) {
        Pattern pattern = Pattern.compile("(?<=" + StringHead + ").+");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }


}
