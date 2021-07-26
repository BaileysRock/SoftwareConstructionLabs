package courseschedule;

import intervalset.CommonMultiIntervalSet;
import intervalset.Interval;

import java.util.ArrayList;

/**
 * 大学课表管理 CourseIntervalSet 是一个 mutable 的 ADT，
 * <p>
 * 针对某个班级，其各周的课表都是完全一样的
 * （意即同样的课程安排将以“周”为单位进行周期性的重复，直到学期结束）；
 * 一门课程每周可以出现 1 次，也可以安排多次，
 * 且由同一位教师承担并在同样的教室进行；
 * 允许课表中有空白时间段（未安排任何课程）；
 * 考虑到不同学生的选课情况不同，同一个时间段内可以安排不同的课程；
 * 一位教师也可以承担课表中的多门课程；
 */
public class CourseIntervalSet
        extends CommonMultiIntervalSet<Course>
        implements ICourseIntervalSet {
    // Abstraction function:
    //   AF() = 大学课表管理系统
    // Representation invariant:
    //   每个课程的 start 时间 > 一个学期的开始日期
    //   每个课程的  end  时间 < 一个学期的总周数
    //   每个课程的周学时数 <= 学期总周数
    // Safety from rep exposure:
    //   字段使用 private 和 final 修饰

    /**
     * 一个学期的开始日期（单位为小时）
     */
    private final long semesterStart;

    /**
     * 一个学期的总周数
     */
    private final int semesterWeeks;

    /**
     * 初始化大学课表管理系统
     * <p>
     * 不区分闰年和平年，一年均按照 365 天计算；
     * 不区分月份，一个月均按照 30 天计算
     *
     * @param startYear     一个学期的开始年份
     * @param startMonth    一个学期的开始月份
     * @param startDay      一个学期的开始日期
     * @param semesterWeeks 一个学期的总周数
     */
    public CourseIntervalSet(final int startYear,
                             final int startMonth,
                             final int startDay,
                             final int semesterWeeks) {
        this.semesterStart = startYear * 365L + startMonth * 30L + startDay;
        this.semesterWeeks = semesterWeeks;
    }

    /**
     * 插入上课时间段
     *
     * @param courseTime 上课时间
     * @param week       课程周数
     * @param course     课程
     */
    public void insert(CourseTime courseTime, Week week, Course course) {
        // 换算日期到小时
        long startHour = semesterStart +
                courseTime.getStart() +
                (week.convert() - 1) * 24L;

        long endHour = semesterStart +
                courseTime.getEnd() +
                (week.convert() - 1) * 24L;

        insert(startHour, endHour, course);
    }

    @Override
    public void insert(long start, long end, Course label) {
        // 根据课程的周学时数进行循环插入
        for (int i = 0; i < label.getPeriod(); i++) {
            Interval interval = new Interval(
                    start + (long) i * 7 * 24,
                    end + (long) i * 7 * 24,
                    true
            );
            if (multiIntervalMap.containsKey(label)) {
                multiIntervalMap.get(label).add(interval);
            } else {
                ArrayList<Interval> arrayList = new ArrayList<>();
                arrayList.add(interval);
                this.multiIntervalMap.put(label, arrayList);
            }
        }
    }

    /**
     * 检查 RI 是否为 true
     * <p>
     * 每个课程的 start 时间 > 一个学期的开始日期
     * 每个课程的  end  时间 < 一个学期的总周数
     * 每个课程的周学时数 <= 学期总周数
     */
    private void checkRep() {
        for (Course course : multiIntervalMap.keySet()) {
            assert course.getPeriod() < semesterWeeks;
            ArrayList<Interval> arrayList = multiIntervalMap.get(course);
            for (Interval interval : arrayList) {
                assert interval.getStart() > semesterStart;
                assert interval.getEnd() <
                        semesterStart + (long) semesterWeeks * 7 * 24;
            }
        }
    }
}
