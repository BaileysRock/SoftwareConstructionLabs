import courseschedule.ClassBuilding;
import courseschedule.Course;
import courseschedule.CourseIntervalSet;
import intervalset.Interval;

import java.util.ArrayList;
import java.util.Map;

/**
 * 课表管理系统
 */
public class CourseScheduleApp {
    /**
     * App 通用功能；设置委托
     */
    private static CommonApp commonApp = new CommonApp();

    /**
     * 课表
     */
    private static CourseIntervalSet courseIntervalSet;

    /**
     * 课程与其分配情况对应键值对
     */
    private static Map<Course, Boolean> courses;

    /**
     * 学期开始年份
     */
    private static int startYear;

    /**
     * 学期开始月份
     */
    private static int startMonth;

    /**
     * 学期开始日期
     */
    private static int startDay;

    /**
     * 学期总周数
     */
    private static int weeks;

    /**
     * 初始化学期开始日期和总周数
     */
    private static void init() {
        System.out.println("设置学期开始时间：");
        while (true) {
            System.out.print("年份：");
            startYear = commonApp.readInt();
            if (startYear < 0) {
                continue;
            }

            System.out.print("月份：");
            startMonth = commonApp.readInt();
            if (startMonth < 1 || startMonth > 12) {
                continue;
            }

            System.out.print("日期：");
            startDay = commonApp.readInt();
            if (startDay < 1 || startDay > 30) {
                continue;
            }

            System.out.print("设置学期总周数：");
            weeks = commonApp.readInt();
            if (weeks > 0) {
                break;
            }
        }
    }

    /**
     * 输出用户选项菜单
     *
     * @return 退出则返回 false；否则返回 true
     */
    private static boolean menu() {
        String menu = "0. 输出此菜单\n" +
                "1. 添加一门新课程\n" +
                "2. 安排一门课程到某个时间段\n" +
                "3. 显示未被安排的课程\n" +
                "4. 显示每周的空闲时间比例\n" +
                "5. 显示重复时间比例\n" +
                "6. 显示指定一天的课表\n" +
                "7. 退出";

        System.out.println(menu);
        int choice;

        do {
            choice = commonApp.readInt();
        } while (choice < 0 || choice > 7);

        switch (choice) {
            case 0:
                System.out.println(menu);
                return true;
            case 1:
                addCourse();
                return true;
            case 2:
                arrangeCourse();
                return true;
            case 3:
                showUnarrangedCourse();
                return true;
            case 4:
                showAvailableRate();
                return true;
            case 5:
                showOverlapRate();
                return true;
            case 6:
                showDayCourses();
                return true;
            case 7:
            default:
                return false;
        }
    }

    /**
     * 添加一门新课程
     */
    private static void addCourse() {
        System.out.print("课程 ID：");
        int id = commonApp.readInt();

        System.out.print("课程名称：");
        String name = commonApp.readString();

        System.out.print("教师名字：");
        String teacher = commonApp.readString();

        System.out.print("教学楼：");
        ClassBuilding classBuilding = null;
        String choices = "0. 正心楼\n" +
                "1. 诚意楼\n" +
                "2. 致知楼\n" +
                "3. 格物楼";
        System.out.println(choices);
        int choice;
        do {
            choice = commonApp.readInt();
        } while (choice < 0 || choice > 3);
        switch (choice) {
            case 0:
                classBuilding = ClassBuilding.ZHENG_XIN;
                break;
            case 1:
                classBuilding = ClassBuilding.CHENG_YI;
                break;
            case 2:
                classBuilding = ClassBuilding.ZHI_ZHI;
                break;
            case 3:
                classBuilding = ClassBuilding.GE_WU;
                break;
            default:
                break;
        }

        System.out.print("教室门牌号：");
        int room = commonApp.readInt();

        System.out.print("周学时数：");
        int period = commonApp.readInt();

        courses.put(
                new Course(id, name, teacher, classBuilding, room, period),
                false
        );
    }

    /**
     * 安排课程到时间段
     */
    private static void arrangeCourse() {

    }

    /**
     * 显示未安排的课程
     */
    private static void showUnarrangedCourse() {
        for (Course course : courses.keySet()) {
            if (!courses.get(course)) {
                System.out.println(course.toString());
            }
        }
    }

    /**
     * 显示每周的空闲时间比例
     */
    private static void showAvailableRate() {

    }

    /**
     * 显示重复时间比例
     */
    private static void showOverlapRate() {

    }

    /**
     * 显示指定一天的课表
     */
    private static void showDayCourses() {
        System.out.println("查看课表：");
        int year, month, day;

        while (true) {
            System.out.print("年份：");
            year = commonApp.readInt();
            if (year < 0) {
                continue;
            }

            System.out.print("月份：");
            month = commonApp.readInt();
            if (month < 1 || month > 12) {
                continue;
            }

            System.out.print("日期：");
            day = commonApp.readInt();
            if (day >= 1 && day <= 30) {
                break;
            }
        }

        long start = year * 365L + month * 30L + day;
        long end = start + 24;

        for (Course course :
                courseIntervalSet.getmultiIntervalMap().keySet()) {
            ArrayList<Interval> arrayList =
                    courseIntervalSet.getmultiIntervalMap().get(course);
            for (Interval interval : arrayList) {
                if (interval.getStart() >= start ||
                        interval.getEnd() <= end) {
                    System.out.println(course.toString());
                }
            }
        }
    }

    /**
     * 课表管理系统程序入口
     */
    public static void main(String[] args) {
        init();
        courseIntervalSet =
                new CourseIntervalSet(startYear, startMonth, startDay, weeks);

        while (menu()) ;
    }
}
