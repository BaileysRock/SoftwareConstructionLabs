package courseschedule;

import intervalset.IntervalSet;
import intervalset.MultiIntervalSet;
import intervalset.PeriodicMultiIntervalSet;

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
public class CourseIntervalSet<L> extends PeriodicMultiIntervalSet<L> {
    // Abstraction function:
    //   AF() = 大学课表管理系统
    // Representation invariant:
    //   每个课程的 start 时间 > 一个学期的开始日期
    //   每个课程的  end  时间 < 一个学期的总周数
    //   每个课程的周学时数 <= 学期总周数
    // Safety from rep exposure:
    //   字段使用 private 和 final 修饰

    public CourseIntervalSet(long period) {
        super(period, MultiIntervalSet.empty());
    }

    public CourseIntervalSet(long period, IntervalSet<L> initial) {
        super(period, MultiIntervalSet.init(initial));
    }
}
