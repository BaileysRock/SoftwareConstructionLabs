package dutyroster;

import API.APIs;
import intervalset.Interval;
import intervalset.MultiIntervalSet;
import intervalset.exceptions.IntervalConflictException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 值班表管理 APP
 */
public class DutyRosterApp {
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
     * 值班时间段
     */
    private static Interval dutyInterval;

    // 初始化值班时间段
    static {
        try {
            dutyInterval = new Interval(start, end);
        } catch (ParseException ignored) {
        }
    }

    /**
     * 值班时间表
     */
    private static DutyIntervalSet<Employee> dutyIntervalSet =
            new DutyIntervalSet<>(
                    dutyInterval.getStart(),
                    dutyInterval.getEnd()
            );

    /**
     * 员工集合
     */
    private static final Set<Employee> employeeSet = new HashSet<>();

    /**
     * 命令行输入
     */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * 输出用户选项菜单
     */
    private static void showMenu() {
        System.out.println("1. 设置值班表信息");
        System.out.println("2. 添加员工信息");
        System.out.println("3. 删除员工信息");
        System.out.println("4. 随机生成值班表");
        System.out.println("5. 手动安排值班表");
        System.out.println("6. 查看排班表是否已满或空闲比例");
        System.out.println("7. 查看排班表");
        System.out.println("8. 从文件导入员工信息");
        System.out.println("9. 删除某一员工值班信息");
        System.out.println("10. 退出");
    }

    /**
     * 值班表管理 APP 入口
     */
    public static void main(String[] args) {
        showMenu();
        String choice;
        boolean loop = true;
        // System.out.println("请先设置值班表信息（1）");
        while (loop) {
            System.out.print("请输入操作：");
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    initDutyIntervalSet();
                    break;
                case "2":
                    addEmployee();
                    break;
                case "3":
                    deleteEmployee();
                    break;
                case "4":
                    randomArrange();
                    break;
                case "5":
                    manualArrange();
                    break;
                case "6":
                    showFreeStatus();
                    break;
                case "7":
                    showDutyRoster();
                    break;
                case "8":
                    loadFileArrangement();
                    break;
                case "9":
                    deleteArrangement();
                    break;
                case "10":
                    System.out.println("已退出");
                    loop = false;
                    break;
                default:
                    System.out.print("输入有误，请检查\n");
                    break;
            }
        }
    }

    /**
     * 从文件中加载值班安排
     */
    private static void loadFileArrangement() {
        System.out.println("选择 test1.txt ~ test8.txt：");
        String fileName = scanner.nextLine();

        // 获取文件名
        BufferedReader file = null;
        try {
            file = new BufferedReader(
                    new FileReader("src/txt/" + fileName)
            );
        } catch (FileNotFoundException ignored) {
        }

        // 读取文件内容
        StringBuilder fileContent = new StringBuilder();
        String fileContentLine = null;
        while (true) {
            try {
                assert file != null;
                if ((fileContentLine = file.readLine()) == null) {
                    break;
                }
            } catch (IOException ignored) {
            }
            assert fileContentLine != null;
            if (!fileContentLine.equals("")) {
                fileContent.append(fileContentLine).append("\n");
            }
        }

        // 检查文件格式
        boolean formatCorrect = APIs.checkDutyFileFormatCorrect(fileContent.toString());
        if (!formatCorrect) {
            System.out.println("文件中格式不符合要求");
            return;
        } else {
            System.out.println("文件中格式符合要求");
        }

        // 加载值班起止时间
        Pattern pattern1 = Pattern.compile(
                "(\\d{4}-\\d{2}-\\d{2}),(\\d{4}-\\d{2}-\\d{2})"
        );
        Matcher matcher1 = pattern1.matcher(
                APIs.removeHead("Period", fileContent.toString())
        );
        matcher1.find();
        try {
            dutyInterval = new Interval(matcher1.group(1), matcher1.group(2));
        } catch (ParseException ignored) {
        }
        dutyIntervalSet = new DutyIntervalSet<>(
                dutyInterval.getStart(),
                dutyInterval.getEnd() + ONEDAY
        );

        // 加载全体员工信息
        String fileContentWithoutWhite =fileContent.toString()
                        .replace("\n", "")
                        .replace("\r", "")
                        .replace("　", "")
                        .replace(" ", "");
        Pattern pattern2 = Pattern.compile("([A-Za-z]+\\{[A-Za-z\\s]+),(\\d{3}-\\d{4}-\\d{4})");
        Matcher matcher2 = pattern2.matcher(APIs.removeHead("Employee", fileContentWithoutWhite));
        String[] fileInput;
        while (matcher2.find()) {
            fileInput = matcher2.group(1).split("\\{");
            Employee nEmployee = new Employee(fileInput[0],fileInput[1],matcher2.group(2));
            employeeSet.add(nEmployee);
        }

        // 加载员工值班信息
        Pattern pattern3 = Pattern.compile(
                "([A-Za-z]+\\{\\d{4}-\\d{2}-\\d{2}),(\\d{4}-\\d{2}-\\d{2})"
        );
        Matcher matcher3 = pattern3.matcher(
                APIs.removeHead("Roster", fileContentWithoutWhite)
        );
        while (matcher3.find()) {
            fileInput = matcher3.group(1).split("\\{");
            for (Employee e : employeeSet) {
                if (e.getName().equals(fileInput[0])) {
                    try {
                        Interval interval = new Interval(
                                fileInput[1],
                                matcher3.group(2)
                        );
                        dutyIntervalSet.insert(
                                interval.getStart(),
                                interval.getEnd() + ONEDAY, e
                        );
                    } catch (ParseException |
                            IntervalConflictException ignored) {
                    }
                    break;
                }
            }
        }
        System.out.println("添加成功");
    }

    /**
     * 随机安排值班
     */
    private static void randomArrange() {
        int i = 0;
        long begin;
        long end = dutyInterval.getStart() + (i + 1) * ONEDAY;
        Employee lastEmployee = null;
        for (Employee e : employeeSet) {
            if (dutyInterval.getStart() + (i + 1) * ONEDAY <=
                    dutyInterval.getEnd()) {
                begin = dutyInterval.getStart() + i * ONEDAY;
                end = dutyInterval.getStart() + (i + 1) * ONEDAY;
                try {
                    dutyIntervalSet.insert(begin, end, e);
                } catch (IntervalConflictException ignored) {
                }
                lastEmployee = e;
                i++;
            } else {
                break;
            }
        }
        if (!dutyIntervalSet.blankIntervalSet().isEmpty()) {
            try {
                dutyIntervalSet.insert(end, dutyInterval.getEnd(), lastEmployee);
            } catch (IntervalConflictException ignored) {
            }
        }
        System.out.println("已经随机生成值班表；");
    }

    /**
     * 设置值班时间表信息
     */
    private static void initDutyIntervalSet() {
        System.out.println("请输入值班阶段的开始时间：");
        start = scanner.nextLine();
        System.out.println("请输入值班阶段的结束时间：");
        end = scanner.nextLine();
        try {
            dutyInterval = new Interval(start, end);
        } catch (ParseException ignored) {
        }
        dutyIntervalSet = new DutyIntervalSet<>(
                dutyInterval.getStart(),
                dutyInterval.getEnd()
        );
        System.out.println("时间设置完成！");
    }

    /**
     * 手动安排值班
     */
    private static void manualArrange() {
        String[] input;
        System.out.print("输入值班员工姓名：");
        String name = scanner.nextLine();
        for (Employee employee : employeeSet) {
            if (employee.getName().equals(name)) {
                System.out.print("输入值班的（起始时间,结束时间）：");
                input = scanner.nextLine().split(",");
                try {
                    Interval dutyTime = new Interval(input[0], input[1]);
                    dutyIntervalSet.insert(
                            dutyTime.getStart(),
                            dutyTime.getEnd(),
                            employee
                    );
                } catch (ParseException | IntervalConflictException ignored) {
                }
                System.out.println("添加成功");
                break;
            }
        }
    }

    /**
     * 查看排班表是否已满或空闲比例
     */
    private static void showFreeStatus() {
        long freeLength = 0;
        Set<Interval> blankIntervalSet = dutyIntervalSet.blankIntervalSet();
        if (blankIntervalSet.isEmpty()) {
            System.out.println("值班表已满");
        } else {
            System.out.println("值班表有空闲，空闲时间段是：\n");
            for (Interval blankInterval : blankIntervalSet) {
                freeLength += blankInterval.getEnd() -
                        blankInterval.getStart();
                System.out.println(
                        "[" +
                                simpleDateFormat.format(
                                        new Date(blankInterval.getStart())
                                ) +
                                "," +
                                simpleDateFormat.format(
                                        new Date(blankInterval.getEnd())
                                ) +
                                "]\n"
                );
            }
            System.out.println(
                    "空闲时间占比：" +
                            APIs.intervalsLength(
                                    MultiIntervalSet.init(dutyIntervalSet)
                            ) /
                                    (freeLength +
                                            APIs.intervalsLength(
                                                    MultiIntervalSet.init(
                                                            dutyIntervalSet
                                                    )
                                            )
                                    )
            );
        }
    }

    /**
     * 删除员工
     */
    private static void deleteEmployee() {
        System.out.println("要删除的员工姓名：");
        String deleteName = scanner.nextLine();
        for (Employee label : dutyIntervalSet.labels()) {
            if (label.getName().equals(deleteName)) {
                System.out.println("不能删除有值班安排的员工\n");
                break;
            } else {
                for (Employee employee : employeeSet) {
                    if (employee.getName().equals(deleteName)) {
                        employeeSet.remove(employee);
                        System.out.println("删除成功");
                    } else {
                        System.out.println("找不到此员工");
                    }
                }
            }
        }
    }

    /**
     * 添加新员工
     */
    private static void addEmployee() {
        Employee myEmployee;
        System.out.println("输入新员工信息（姓名,职务,电话号码）：");
        try {
            String[] input = scanner.nextLine().split(",");
            myEmployee = new Employee(input[0], input[1], input[2]);
            employeeSet.add(myEmployee);
            System.out.println("添加成功");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("输入有误");
        }
    }

    /**
     * 打印值班时间表
     */
    private static void showDutyRoster() {
        for (Employee emplo : dutyIntervalSet.labels()) {
            System.out.println(
                    "值班开始日期\t" +
                            "值班结束日期\t" +
                            "值班人员姓名\t" +
                            "值班人员职位\t" +
                            "值班人员电话\n"
            );
            System.out.println(
                    simpleDateFormat.format(
                            new Date(
                                    dutyIntervalSet.start(emplo)
                            )
                    ) + "\t" +
                            simpleDateFormat.format(
                                    new Date(
                                            dutyIntervalSet.end(emplo)
                                    )
                            ) + "\t" +
                            emplo.getName() + "\t" +
                            emplo.getPosition() + "\t" +
                            emplo.getPhoneNumber() + "\n");
        }
    }

    /**
     * 删除某一员工的值班安排
     */
    private static void deleteArrangement() {
        System.out.print("要删除的员工姓名：");
        boolean found = false;
        String deleteName = scanner.nextLine();
        for (Employee label : dutyIntervalSet.labels()) {
            if (label.getName().equals(deleteName)) {
                dutyIntervalSet.remove(label);
                System.out.println("删除成功！\n");
                found = true;
            }
        }
        if (!found) {
            System.out.println("未找到员工");
        }
    }
}
