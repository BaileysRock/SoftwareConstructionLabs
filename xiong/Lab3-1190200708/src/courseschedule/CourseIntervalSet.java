package courseschedule;

import API.APIs;
import intervalset.*;
import intervalset.exception.IntervalConflictException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseIntervalSet<L> extends CommonMultiIntervalSet<L> implements ICourseIntervalSet<L> {

    // Abstraction function:
    // 由七个私有变量映射到CourseIntervalSet类型
    // Representation invariant:
    // weeksNum为偶数且大于0
    // start > 0
    // Safety from rep exposure:
    // 使用private、final修饰变量
    // 采用防御性复制

    // MS即毫秒数
    // 一周的总毫秒数
    private final long WeekTimeMS = 7 * 24 * 60 * 60 * 1000;
    // 每周的学时总毫秒数
    // 每天五节课
    // 每节课两小时
    private final long WeekLearnTimeMS = 7 * 5 * 2 * 60 * 60 * 1000;

    // 每节课的课时
    // 两小时的毫秒数
    private final long courseTimeMS = 2 * 60 * 60 * 1000;
    // 每节课的小时数
    private final long courseHours = 2;


    // 学期开始时间
    private long start;

    // 学期周数
    private int weeksNum;

    // 所有课程的列表
    private final List<Course> allCourseList = new ArrayList<>();

    //用于委派的数据类型，可以单个标签绑定多个时间段的时间段集合
    private final CommonMultiIntervalSet intervalSet;


    /**
     * 初始化进程调度器
     */
    public CourseIntervalSet(long start, int weeksNum) {
        this.intervalSet = new CommonMultiIntervalSet<Course>();
        this.weeksNum = weeksNum;
        this.start = start;
        checkRep();

    }


    public double freeRatio() {
        return APIs.calcFreeTimeRatio(intervalSet);
    }


    public double conflictRatio() {
        return APIs.calcConflictRatio(intervalSet);
    }

    @Override
    public List<Course> getAllCourseList() {
        return new ArrayList<>(this.allCourseList);
    }


    @Override
    public int getWeeksNum() {
        return weeksNum;
    }


    @Override
    public Date getStart() {
        return new Date(this.start);
    }


    @Override
    public boolean addCourse(String CourseID, String CourseName, String TeacherName, String Place, int studyTime) {
        if (CourseID == null || CourseName == null || TeacherName == null || Place == null) {
            return false;
        }
        Course addCourse = new Course(CourseID, CourseName, TeacherName, Place, studyTime);
        for (Course course : allCourseList) {
            if (course.equals(addCourse)) {
                return false;
            }
        }
        allCourseList.add(addCourse);
        return true;
    }


    @Override
    public boolean insert(long start, long end, L label, long studyTime) {
        // 如果当前存在label标签
        long studyTimeDistribute = 0;
        if (intervalSet.labels().contains(label)) {
            for (Integer IntegerLabel : intervals(label).labels()) {
                studyTimeDistribute += (intervals(label).end(IntegerLabel) - intervals(label).start(IntegerLabel));
            }
            // 计算当前已分配学时是否满足周分配学时
            // 小于即可继续分配，否则返回false
            if (studyTime * courseTimeMS > studyTimeDistribute) {
                // 计算分配后的时间是否满足
                // 若满足即可分配
                if (studyTime * courseTimeMS >= end - start + studyTimeDistribute) {
                    try {
                        intervalSet.insert(start, end, label);
                    } catch (IntervalConflictException e) {
                        e.printStackTrace();
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        // 若不存在label标签
        if (studyTime * courseTimeMS >= end - start) {
            try {
                intervalSet.insert(start, end, label);
            } catch (IntervalConflictException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return true;
    }
}
