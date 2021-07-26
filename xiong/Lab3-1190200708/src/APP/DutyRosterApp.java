package APP;

import API.APIs;
import employee.*;
import intervalset.Interval;
import intervalset.exception.IntervalBlankException;
import intervalset.exception.IntervalConflictException;
import intervalset.exception.IntervalPeriodicException;
import paser.Paser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static paser.Paser.DatePaser;

public class DutyRosterApp {


    /**
     * 显示菜单
     */
    public static void menu() {
        System.out.println("欢迎使用排班系统，请输入所需功能对应数字:");
        System.out.println("1.显示菜单");
        System.out.println("2.设置值班表信息");
        System.out.println("3.添加员工信息");
        System.out.println("4.删除员工信息");
        System.out.println("5.显示所有员工信息");
        System.out.println("6.分配员工值班");
        System.out.println("7.显示当前空闲时间");
        System.out.println("8.检验当前值班信息是否符合要求");
        System.out.println("9.保存值班信息并导出");
        System.out.println("10.自动随机编排值班表");
        System.out.println("11.从外部文件导入值班信息");
        System.out.println("0.退出排班系统");
    }


    public static void main(String[] args) {
        DutyRosterApp.menu();
        String choice;

        // 值班表
        DutyIntervalSet<Employee> DutyInterval = null;
        List<String> dateSplit = new LinkedList<>();
        Set<Employee> employeeSet = new HashSet<>();
        int addEmployeeNum;
        int labelsCount;
        String EmployeeName;
        String EmployeePosition;
        String EmployeePhoneNumber;
        Date startDate;
        Date endDate;
        long start = 0;
        long end = 0;
        while (true) {
            System.out.println("请输入您的选项:");

            Scanner in = new Scanner(System.in);
            choice = in.nextLine();

            switch (choice) {
                case "1":
                    DutyRosterApp.menu();
                    break;

                case "2":
                    System.out.println("请输入起始时间和截止时间:(eg:2021-03-06,2021-03-06)");
                    String date = in.nextLine();
                    // String[]dateSplit = date.split(",");

                    dateSplit.addAll(Arrays.asList(date.split(",")));

                    dateSplit.removeAll(Collections.singleton(new String()));


                    boolean startLegal = Paser.legalDate(dateSplit.get(0));
                    boolean endLegal = Paser.legalDate(dateSplit.get(1));
                    if (dateSplit.size() > 2 || !startLegal || !endLegal) {
                        System.out.println("您的输入有误!请重新输入您的选项:");
                        break;
                    }

                    Date dateStart = null;
                    Date dateEnd = null;
                    SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        dateStart = TimeFormat.parse(dateSplit.get(0));
                        dateEnd = TimeFormat.parse(dateSplit.get(1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    start = dateStart.getTime();
                    end = dateEnd.getTime();
                    DutyInterval = new DutyIntervalSet<>(dateStart.getTime(), dateEnd.getTime());

                    break;
                case "3":
                    System.out.println("请输入待添加的员工人数:");
                    addEmployeeNum = Integer.parseInt(in.nextLine());
                    for (int i = 0; i < addEmployeeNum; i++) {
                        System.out.println("请输入员工姓名:");
                        EmployeeName = in.nextLine();
                        System.out.println("请输入员工职位:");
                        EmployeePosition = in.nextLine();
                        System.out.println("请输入员工手机号:");
                        EmployeePhoneNumber = in.nextLine();
                        employeeSet.add(new Employee(EmployeeName, EmployeePosition, EmployeePhoneNumber));
                    }

                    break;
                case "4":
                    System.out.println("请输入待删除员工姓名:");
                    EmployeeName = in.nextLine();
                    System.out.println("请输入待删除员工职位:");
                    EmployeePosition = in.nextLine();
                    System.out.println("请输入待删除员工手机号:");
                    EmployeePhoneNumber = in.nextLine();
                    boolean flag = true;
                    for (Employee employee : employeeSet) {
                        if (employee.getName().equals(EmployeeName) && employee.getPosition().equals(EmployeePosition) && employee.getPhonenumber().equals(EmployeePhoneNumber)) {
                            employeeSet.remove(employee);
                            System.out.println("删除成功!");
                            flag = false;
                            break;
                        }
                    }
                    if (flag)
                        System.out.println("未发现符合条件的未编排值班时间的员工!");
                    break;
                case "5":
                    System.out.println("Employee List:");
                    if (employeeSet.size() != 0) {
                        for (Employee employee : employeeSet) {
                            System.out.print("Name\t" + employee.getName() + "\t");
                            System.out.print("Position\t" + employee.getPosition() + "\t");
                            System.out.println("PhoneNumber\t" + employee.getPhonenumber() + "\t");
                        }
                    }
                    if (DutyInterval != null) {
                        for (Employee employee : DutyInterval.labels()) {
                            System.out.print("Name\t" + employee.getName() + "\t");
                            System.out.print("Position\t" + employee.getPosition() + "\t");
                            System.out.println("PhoneNumber\t" + employee.getPhonenumber() + "\t");
                        }
                    }
                    break;
                case "6":
                    if (start == 0 || end == 0) {
                        System.out.println("当前还未初始化值班信息!");
                    } else {
                        System.out.println("请为员工分配值班时间");
                        char addChoice = 'y';
                        long startDuty = 0;
                        long endDuty = 0;
                        boolean startDutyLegal = false;
                        boolean endDutyLegal = false;
                        Set<Employee> copySet = new HashSet<>();
                        copySet.addAll(employeeSet);
                        while (addChoice == 'y' || addChoice == 'Y') {
                            System.out.println("请输入待分配的员工的姓名:");
                            EmployeeName = in.nextLine();
                            System.out.println("请输入待分配的员工的职位:");
                            EmployeePosition = in.nextLine();
                            boolean flagexit = true;
                            for (Employee employee : employeeSet) {
                                if (employee.getName().equals(EmployeeName) && employee.getPosition().equals(EmployeePosition)) {
                                    flagexit = false;
                                }
                            }
                            if (flagexit == true) {
                                System.out.println("重复分配或当前不存在此人！");
                            } else {
                                for (Employee employee : copySet) {
                                    if (employee.getName().equals(EmployeeName) && employee.getPosition().equals(EmployeePosition)) {
                                        System.out.println("请输入员工值班时间:(eg:2021-03-06,2021-03-06)");
                                        String dateDuty = in.nextLine();
                                        // String[]dateSplit = date.split(",");
                                        List<String> dateDutySplit = new LinkedList<>();
                                        dateDutySplit.addAll(Arrays.asList(dateDuty.split(",")));

                                        dateDutySplit.removeAll(Collections.singleton(new String()));

                                        startDutyLegal = Paser.legalDate(dateDutySplit.get(0));
                                        endDutyLegal = Paser.legalDate(dateDutySplit.get(1));
                                        if (dateDutySplit.size() > 2 || !startDutyLegal || !endDutyLegal) {
                                            System.out.println("您的输入有误!请重新输入您的选项:");
                                            break;
                                        }
                                        Date dateStartEmployee = null;
                                        Date dateEndEmployee = null;
                                        SimpleDateFormat TimeFormatEmployee = new SimpleDateFormat("yyyy-MM-dd");
                                        try {
                                            dateStartEmployee = TimeFormatEmployee.parse(dateDutySplit.get(0));
                                            dateEndEmployee = TimeFormatEmployee.parse(dateDutySplit.get(1));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        startDuty = dateStartEmployee.getTime();
                                        endDuty = dateEndEmployee.getTime();
                                        if (startDuty < start || endDuty > end) {
                                            System.out.println("您的输入有误!请重新输入您的选项:");
                                            break;
                                        } else {
                                            try {
                                                if (employeeSet.contains(employee)) {
                                                    DutyInterval.insert(startDuty, endDuty, employee, false);
                                                    employeeSet.remove(employee);
                                                } else {
                                                    throw new Exception("重复分配同一人!");
                                                }
                                            } catch (Exception exception) {
                                                exception.printStackTrace();
                                            }
                                        }
                                    }
                                }
                                System.out.println("是否继续分配值班(y/Y):");
                                addChoice = in.nextLine().charAt(0);
                            }
                        }
                    }
                    break;
                case "7":
                    if (start == 0 || end == 0) {
                        System.out.println("当前还未初始化值班信息!");
                    } else {
                        DutyInterval.showFreeTime();
                    }
                    break;
                case "8":
                    if (DutyInterval.checkNoBlank(start, end) && DutyInterval.checkNoOverlap() && DutyInterval.checkNoPeriodic()) {
                        System.out.println("当前已满足分配条件!");
                    } else {
                        if (!DutyInterval.checkNoBlank(start, end)) {
                            System.out.println("当前存在空闲时间!");
                        }
                        if (!DutyInterval.checkNoOverlap()) {
                            System.out.println("当前存在重叠时间!");
                        }
                        if (!DutyInterval.checkNoPeriodic()) {
                            System.out.println("当前存在周期性时间!");
                        }
                    }
                    break;
                case "9":



                    if (!DutyInterval.checkNoBlank(start, end) || !DutyInterval.checkNoOverlap() || !DutyInterval.checkNoPeriodic()) {
                        System.out.println("当前未满足分配条件!");
                    } else {
                        DutyInterval.showTimeTable();
                    }
                    //DutyInterval.showTimeTable();
                    break;
                case "10":
                    if (employeeSet.size() == 0) {
                        System.out.println("当前未添加员工!");
                    } else {
                        List<Employee> setLabelsCopy = new ArrayList<>();
                        setLabelsCopy.addAll(employeeSet);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            startDate = format.parse(dateSplit.get(0));
                            endDate = format.parse(dateSplit.get(1));
                            long stateTimeLong = startDate.getTime();
                            long endTimeLong = endDate.getTime();
                            // 结束时间-开始时间 = 天数
                            long Countday = (endTimeLong - stateTimeLong) / (24 * 60 * 60 * 1000);
                            labelsCount = employeeSet.size();
                            long labelDay = Countday / labelsCount;
                            for (int i = 0; i < labelsCount - 1; i++) {
                                Calendar calendar = new GregorianCalendar();
                                calendar.setTime(startDate);
                                calendar.add(calendar.DATE, (int) labelDay);
                                endDate = calendar.getTime();
                                try {
                                    DutyInterval.insert(startDate.getTime(), endDate.getTime(), setLabelsCopy.get(i), false);
                                } catch (IntervalConflictException | IntervalPeriodicException | IntervalBlankException e) {
                                    e.printStackTrace();
                                }
                                startDate = endDate;
                            }
                            Date dateEndEmployeedis = null;
                            SimpleDateFormat TimeFormatEmployee = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                dateEndEmployeedis = TimeFormatEmployee.parse(dateSplit.get(1));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            try {



                                DutyInterval.insert(startDate.getTime(), dateEndEmployeedis.getTime(), setLabelsCopy.get(setLabelsCopy.size() - 1), false);
                            } catch (IntervalConflictException | IntervalPeriodicException | IntervalBlankException e) {
                                e.printStackTrace();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case "11":
                    System.out.println("请选择test1.txt-test8.txt");
                    String fileName = in.nextLine();
                    BufferedReader file = null;
                    try {
                        file = new BufferedReader(new FileReader("src/txt/" + fileName));
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
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
                    boolean checkFileformat = APIs.checkFileFormat(fileContent.toString());
                    if (!checkFileformat) {
                        System.out.println("文件格式错误");
                        return;
                    }

                    // 加载值班起止时间
                    Pattern pattern1 = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}),(\\d{4}-\\d{2}-\\d{2})");
                    Matcher matcher1 = pattern1.matcher(APIs.removeStringHead("Period", fileContent.toString()));
                    matcher1.find();
                    Interval interval = null;


                    Date dateStartRead = null;
                    Date dateEndRead = null;
                    SimpleDateFormat TimeFormatEmployee = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        dateStartRead = TimeFormatEmployee.parse( matcher1.group(1));
                        dateEndRead = TimeFormatEmployee.parse( matcher1.group(2));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        interval = new Interval( dateStartRead.getTime() ,dateEndRead.getTime()+24*60*60*1000);
                    } catch (Exception exception) {
                    }
                    start = interval.getStart();
                    end = interval.getEnd();

                    DutyInterval = new DutyIntervalSet<>(interval.getStart(), interval.getEnd());

                    // 加载全体员工信息
                    String fileContentWithoutWhite =
                            fileContent.toString()
                                    .replace("\n", "")
                                    .replace("\r", "")
                                    .replace("　", "")
                                    .replace(" ", "");
                    Pattern pattern2 = Pattern.compile(
                            "([A-Za-z]+\\{[A-Za-z\\s]+),(\\d{3}-\\d{4}-\\d{4})"
                    );
                    Matcher matcher2 = pattern2.matcher(
                            APIs.removeStringHead("Employee", fileContentWithoutWhite)
                    );
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
                            APIs.removeStringHead("Roster", fileContentWithoutWhite)
                    );

                    while (matcher3.find()) {
                        fileInput = matcher3.group(1).split("\\{");
                        for (Employee e : employeeSet) {
                            if (e.getName().equals(fileInput[0])) {

                                try {
                                    dateStartRead = TimeFormatEmployee.parse( fileInput[1]);
                                    dateEndRead = TimeFormatEmployee.parse( matcher3.group(2));
                                } catch (Exception exception  ) {
                                    exception.printStackTrace();
                                }


                                Interval intervaladd = new Interval(dateStartRead.getTime(),dateEndRead.getTime()+24*60*60*1000);
                                try {
                                    DutyInterval.insert(intervaladd.getStart(),intervaladd.getEnd(), e);
                                } catch (IntervalConflictException intervalConflictException) {
                                    intervalConflictException.printStackTrace();
                                }
                                break;
                            }
                        }
                    }
                    System.out.println("添加成功");








                    break;
                case "0":
                    return;
                default:
                    System.out.println("输入有误，请重新输入:");
                    break;

            }


        }


    }


}
