package APP;

import API.APIs;
import courseschedule.Course;
import courseschedule.CourseIntervalSet;
import intervalset.Interval;
import intervalset.exception.IntervalBlankException;
import intervalset.exception.IntervalConflictException;
import intervalset.exception.IntervalPeriodicException;
import processschedule.Process;
import processschedule.ProcessIntervalSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CourseScheduleApp {


    public static void menu() {
        System.out.println("欢迎使用进程管理APP，请输入所需功能对应数字:");
        System.out.println("1.显示菜单");
        System.out.println("2.设定学期相关信息");
        System.out.println("3.增加一组课程，及其相关信息");
        System.out.println("4.安排课时");
        System.out.println("5.当前所有课程的安排情况");
        System.out.println("6.每周空闲时间比例");
        System.out.println("7.每周重复时间比例");
        System.out.println("8.查看一天课表");
    }

    public static void main(String[] args) {

        // MS即毫秒数
        // 一周的总毫秒数
        final long WeekTimeMS = 7 * 24 * 60 * 60 * 1000;
        CourseScheduleApp.menu();
        // 功能选项
        String choice;
        // 进程集合
        long timeLong = 0;
        String timeString;
        String addCourseID;
        String addCourseName;
        String addTeacherName;
        String addPlace;
        int addStudyTime;
        int weekNums;
        String timeStart;
        String timeEnd;
        Date dateStart;
        Date dateEnd;
        CourseIntervalSet courseIntervalSet = null;
        // 课程列表
        List<Course> courseList = new ArrayList<>();
        List<List<Interval>> CourseInterval = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("请输入您的选项:");
            choice = in.nextLine();
            switch (choice) {
                case "1":
                    CourseScheduleApp.menu();
                    break;
                case "2":
                    System.out.println("请输入学期开始时间(例:2021-01-02)");
                    timeString = in.nextLine();
                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                    Date startDate = null;
                    try {
                        startDate = ft.parse(timeString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    timeLong = startDate.getTime();
                    System.out.println("请输入总周数(例:18)");
                    weekNums = Integer.parseInt(in.nextLine());
                    courseIntervalSet = new CourseIntervalSet(timeLong, weekNums);
                    System.out.println("设置学期信息成功!");
                    break;
                case "3":
                    if (courseIntervalSet == null)
                        System.out.println("请先输入学期相关信息");
                    else {
                        System.out.println("请输入课程相关信息:");
                        System.out.println("请输入课程ID:");
                        addCourseID = in.nextLine();
                        System.out.println("请输入课程名称:");
                        addCourseName = in.nextLine();
                        System.out.println("请输入教师姓名:");
                        addTeacherName = in.nextLine();
                        System.out.println("请输入授课地点:");
                        addPlace = in.nextLine();
                        System.out.println("请输入周学时数(偶数)");
                        addStudyTime = Integer.parseInt(in.nextLine());
                        if (addStudyTime == 0) {
                            System.out.println("学时不能为0!");
                        } else {
                            courseList.add(new Course(addCourseID, addCourseName, addTeacherName, addPlace, addStudyTime));
                            CourseInterval.add(new ArrayList<Interval>());
                            courseIntervalSet.addCourse(addCourseID, addCourseName, addTeacherName, addPlace, addStudyTime);
                        }
                    }
                    break;
                case "4":
                    if (courseIntervalSet == null)
                        System.out.println("请先输入学期相关信息");
                    else {
                        boolean checkInsert = false;
                        boolean flag = false;
                        int index = 0;
                        System.out.println("请输入课程ID:");
                        addCourseID = in.nextLine();
                        System.out.println("请输入课程名称:");
                        addCourseName = in.nextLine();
                        System.out.println("请输入教师姓名:");
                        addTeacherName = in.nextLine();
                        System.out.println("请输入授课地点:");
                        addPlace = in.nextLine();
                        Course checkCourse = new Course(addCourseID, addCourseName, addTeacherName, addPlace, 0);
                        Course courseCheck = null;
                        for (int i = 0; i < courseIntervalSet.getAllCourseList().size(); i++) {
                            if (courseIntervalSet.getAllCourseList().get(i).equals(checkCourse)) {
                                dateStart = null;
                                dateEnd = null;
                                System.out.println("请输入安排时间的开始:(yyyy-MM-dd hh:mm:ss)");
                                timeStart = in.nextLine();
                                System.out.println("请输入安排时间的开始:(yyyy-MM-dd hh:mm:ss)");
                                timeEnd = in.nextLine();
                                SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                try {
                                    dateStart = TimeFormat.parse(timeStart);
                                    dateEnd = TimeFormat.parse(timeEnd);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                for (int j = 0; j < courseList.size(); j++) {
                                    if (courseList.get(j).getTname().equals(addTeacherName)
                                            && courseList.get(j).getCname().equals(addCourseName)
                                            && courseList.get(j).getCid().equals(addCourseID)
                                            && courseList.get(j).getPlace().equals(addPlace)
                                    ) {
                                        courseCheck = courseList.get(j);
                                        index = j;
                                    }
                                }
                                if (courseCheck != null) {
                                    flag = true;
                                    checkInsert = courseIntervalSet.insert(dateStart.getTime(), dateEnd.getTime(), courseIntervalSet.getAllCourseList().get(i), courseCheck.getStudyNum());

                                    if (checkInsert == true) {
                                        CourseInterval.get(index).add(new Interval(dateStart.getTime(), dateEnd.getTime(), false));
                                    }

                                }

                            }

                        }
                        if (flag == false)
                            System.out.println("不存在该课程!");
                    }
                    break;
                case "5":
                    if (courseIntervalSet == null)
                        System.out.println("请先输入学期相关信息");
                    else {
                        System.out.println("课程安排情况如下:");
                        for (int i = 0; i < courseList.size(); i++) {
                            System.out.println(courseList.get(i).getCname() + "\t");
                            if (CourseInterval.size() != 0) {
                                for (Interval interval : CourseInterval.get(i)) {
                                    System.out.println((new Date(interval.getStart()).toString()) + "->" + (new Date(interval.getEnd()).toString()));
                                }
                            }
                        }
                    }
                    break;
                case "6":
                    if (courseIntervalSet == null)
                        System.out.println("请先输入学期相关信息");
                    else {
                        double freeRatio = courseIntervalSet.freeRatio();
                        System.out.println(freeRatio);
                    }
                    break;
                case "7":
                    if (courseIntervalSet == null)
                        System.out.println("请先输入学期相关信息");
                    else {
                        double conflictRatio = courseIntervalSet.conflictRatio();
                        System.out.println(conflictRatio);
                    }

                    break;
                case "8":
                    if (courseIntervalSet == null)
                        System.out.println("请先输入学期相关信息");
                    else {
                        Date checkDayDate = null;
                        System.out.println("请输入待查询的时间:(yyyy-MM-dd)");
                        timeStart = in.nextLine();
                        SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            checkDayDate = TimeFormat.parse(timeStart);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long startDayTime = checkDayDate.getTime();
                        long endDayTime = startDayTime + 24 * 60 * 60 * 1000;
                        for (int i = 0; i < courseList.size(); i++) {
                            for (Interval interval : CourseInterval.get(i)) {
                                for (int j = 0; j < courseIntervalSet.getWeeksNum(); j++) {
                                    if (interval.getStart() + WeekTimeMS * j <= endDayTime && interval.getStart() + WeekTimeMS * j >= startDayTime) {
                                        System.out.println(courseList.get(i).getCname() + "\t" + (new Date(interval.getStart() + WeekTimeMS * j)) + "->" + (new Date(interval.getEnd() + WeekTimeMS * j)));
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "0":
                    return;
                default:
                    break;


            }


        }


    }


}
