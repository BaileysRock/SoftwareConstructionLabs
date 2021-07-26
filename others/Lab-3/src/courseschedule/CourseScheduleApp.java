package courseschedule;

import API.APIs;
import intervalset.Interval;
import intervalset.exceptions.IntervalConflictException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 大学课表管理系统 APP
 */
public class CourseScheduleApp {
    /**
     * 值班表开始日期
     */
    private static String start = "0-0-0";

    /**
     * 值班表结束日期
     */
    private static String end = "0-0-0";

    /**
     * 一天的长度
     */
    private static final long ONEDAY = 60 * 60 * 24 * 1000;

    /**
     * 日期格式化工具
     */
    private static final SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 上课时间段
     */
    private static Interval courseInterval;

    // 初始化值班时间段
    static {
        try {
            courseInterval = new Interval(start, end);
        } catch (ParseException ignored) {
        }
    }

    /**
     * 课表
     */
    private static final CourseIntervalSet<Course> courseIntervalSet =
            new CourseIntervalSet<>(5 * 7);

    /**
     * 课程集合
     */
    private static final Set<Course> courseSet = new HashSet<>();

    /**
     * 命令行输入
     */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * 输出用户选项菜单
     */
    private static void showMenu() {
        System.out.println("1. 设置课程表信息");
        System.out.println("2. 添加课程");
        System.out.println("3. 删除课程");
        System.out.println("4. 手动生成课程表");
        System.out.println("5. 查看未安排的课程");
        System.out.println("6. 查看课程表");
        System.out.println("7. 查看每周空闲时间占比");
        System.out.println("8. 查看每周课程重复时间比例");
        System.out.println("9. 查看特定日期的课表");
        System.out.println("10. 退出");
    }

    /**
     * 大学课表管理系统 APP 入口
     */
    public static void main(String[] args) {
        showMenu();
        System.out.println("最开始请先设置课程表相关的信息（输入1）");
        String choice;
        boolean loop = true;
        while (loop) {
            System.out.print("输入操作：");
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    initCourseIntervalSet();
                    break;
                case "2":
                    addCourse();
                    break;
                case "3":
                    deleteCourse();
                    break;
                case "4":
                    manualArrange();
                    break;
                case "5":
                    showUnarrangedCourse();
                    break;
                case "6":
                    showSchedule();
                    break;
                case "7":
                    System.out.println("每周空闲时间占比：" +
                            APIs.calcFreeTimeRatio(courseIntervalSet)
                    );
                    break;
                case "8":
                    System.out.println("每周课程重复时间时间比例：" +
                            APIs.calcConflictRatio(courseIntervalSet));
                    break;
                case "9":
                    showDaySchedule();
                    break;
                case "10":
                    System.out.println("退出");
                    loop = false;
                    break;
                default:
                    System.out.println("指令有误，请重新输入");
                    break;
            }
        }
    }

    /**
     * 查看特定日期的课表
     */
    private static void showDaySchedule() {
        System.out.print("查看特定日期的课表（yyyy-MM-dd）：");
        String selectDay = scanner.nextLine();
        try {
            Interval transInterval = new Interval(selectDay, end);
            long weekday = ((transInterval.getStart() -
                    courseInterval.getStart()) / ONEDAY) % 7;
            System.out.println("课表如下：");
            for (Course course : courseIntervalSet.labels()) {
                for (int i = 0; i < course.getPeriod() / 2; i++) {
                    long start = courseIntervalSet.intervals(course).start(i);
                    if (start == 5 * weekday) {
                        System.out.println("8:00-10:00" + "\t" +
                                course.getName());
                    } else if (start == 5 * (weekday) + 1) {
                        System.out.println("10:00-12:00" + "\t" +
                                course.getName()
                        );
                    } else if (start == 5 * (weekday) + 2) {
                        System.out.println("13:00-15:00" + "\t" +
                                course.getName()
                        );
                    } else if (start == 5 * (weekday) + 3) {
                        System.out.println("15:00-17:00" + "\t" +
                                course.getName()
                        );
                    } else if (start == 5 * (weekday) + 4) {
                        System.out.println("19:00-21:00" + "\t" +
                                course.getName()
                        );
                    }
                }
            }
        } catch (ParseException ignored) {
        }
    }

    /**
     * 查看课程表
     */
    private static void showSchedule() {
        System.out.println("课程表：\n");
        System.out.println("课程 ID\t课程名\t教师姓名\t上课教室\t周学时数\n");
        for (Course course : courseSet) {
            if (courseIntervalSet.labels().contains(course)) {
                System.out.println(course.getId() + "\t" +
                        course.getName() + "\t" +
                        course.getTeacher() + "\t" +
                        course.getPlace() + "\t" +
                        course.getPeriod() + "\n"
                );
            }
        }
    }

    /**
     * 查看未安排的课程
     */
    private static void showUnarrangedCourse() {
        System.out.println("未安排的课程：");
        System.out.println("课程 ID\t课程名\t教师姓名\t上课教室\t周学时数\n");
        for (Course course : courseSet) {
            if (!courseIntervalSet.labels().contains(course)) {
                System.out.println(course.getId() + "\t" +
                        course.getName() + "\t" +
                        course.getTeacher() + "\t" +
                        course.getPlace() + "\t" +
                        course.getPeriod()
                );
            }
        }
    }

    /**
     * 手动设置课表
     */
    private static void manualArrange() {
        boolean found = false;
        System.out.println("输入你课程 ID：");
        String setId = scanner.nextLine();
        for (Course course : courseSet) {
            if (course.getId().equals(setId)) {
                found = true;
                for (int i = 0; i < (course.getPeriod() / 2); i++) {
                    System.out.print("上课时间是星期几（1-7）:");
                    int courseWeekDay = scanner.nextInt();
                    System.out.print("上课的节数(1，3，5，7，9)：");
                    int courseIndex = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        courseIntervalSet.insert(
                                5L * (courseWeekDay - 1) + courseIndex / 2,
                                5L * (courseWeekDay - 1) + courseIndex / 2 + 1,
                                course
                        );
                    } catch (IntervalConflictException ignored) {
                    }
                    System.out.println("添加成功");
                }
            }
        }
        if (!found) {
            System.out.println("未找到课程");
        }
    }

    /**
     * 删除课程
     */
    private static void deleteCourse() {
        boolean found = false;
        System.out.print("输入删除课程的 ID：");
        String deleteId = scanner.nextLine();
        for (Course course : courseSet) {
            if (course.getId().equals(deleteId)) {
                courseSet.remove(course);
                found = true;
            }
        }
        if (found) {
            System.out.println("删除成功");
        } else {
            System.out.println("未找到课程");
        }
    }

    /**
     * 添加课程
     */
    private static void addCourse() {
        System.out.print("输入新课程信息（课程Id,课程名,教师姓名,上课地点）：");
        String[] input = scanner.nextLine().split(",");
        System.out.println("输入新课程周学时数：");
        int period = scanner.nextInt();
        scanner.nextLine();
        while (period % 2 != 0) {
            System.out.println("周学时数必须为偶数，请重新输入");
            period = scanner.nextInt();
        }
        Course newCourse = new Course(
                input[0],
                input[1],
                input[2],
                input[3],
                period
        );
        courseSet.add(newCourse);
        System.out.println("添加成功");
    }

    /**
     * 设置课程表信息
     */
    private static void initCourseIntervalSet() {
        System.out.print("学期开始时间（yyyy-MM-dd）：");
        start = scanner.nextLine();
        System.out.print("学期周数：");
        long week = scanner.nextInt();
        scanner.nextLine();
        try {
            courseInterval = new Interval(start, end);
            long end = courseInterval.getStart() + week * 7 * ONEDAY;
            courseInterval = new Interval(
                    start,
                    simpleDateFormat.format(new Date(end))
            );
        } catch (ParseException ignored) {
        }
        System.out.println("添加成功");
    }
}
