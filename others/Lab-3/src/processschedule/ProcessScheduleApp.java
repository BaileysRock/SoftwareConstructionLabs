package processschedule;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import API.APIs;
import intervalset.exceptions.IntervalConflictException;
import intervalset.IntervalSet;
import intervalset.MultiIntervalSet;

/**
 * 进程调度管理 APP
 */
public class ProcessScheduleApp {
    /**
     * 随机数生成器
     */
    private static final Random random = new Random();

    /**
     * 进程调度管理器
     */
    private static final ProcessIntervalSet<Process> processIntervalSet =
            new ProcessIntervalSet<>(IntervalSet.empty());

    /**
     * 命令行输入
     */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * 进程集合
     */
    private static final Set<Process> processSet = new HashSet<>();

    /**
     * 进程等待
     */
    private static final String WAIT = "wait";

    /**
     * 进程正在执行
     */
    private static final String RUN = "run";

    /**
     * 进程挂起
     */
    private static final String HUNG = "hung";

    /**
     * 进程结束
     */
    private static final String OVER = "over";

    /**
     * 进程是否挂起
     */
    private static boolean isHung = false;

    /**
     * 输出用户选项菜单
     */
    private static void showMenu() {
        System.out.println("1. 增加一个进程");
        System.out.println("2. 随机调度进程");
        System.out.println("3. 继续调度进程");
        System.out.println("4. 不执行进程");
        System.out.println("5. 显示进程调度结果");
        System.out.println("6. 执行最短时间进程调度");
        System.out.println("7. 退出");
    }

    /**
     * 进程调度管理 APP 入口
     */
    public static void main(String[] args) {
        System.out.println("最开始请先设置一组进程（输入1）");
        showMenu();
        boolean loop = true;
        while (loop) {
            System.out.println("请输入您要进行的操作：");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addProcess();
                    break;
                case "2":
                    randomRunProcess();
                    break;
                case "3":
                    restartProcess();
                    break;
                case "4":
                    hungProcess();
                    break;
                case "5":
                    showFinishedProcess();
                    break;
                case "6":
                    quickRunProcess();
                    break;
                case "7":
                    System.out.println("退出");
                    loop = false;
                    break;
                default:
                    System.out.println("输入有误，请重新输入");
                    break;
            }
        }
    }

    /**
     * 最短时间进程调度
     */
    private static void quickRunProcess() {
        int i = 0;
        int now = 0;

        System.out.println("最短时间进程调度");
        Set<Process> sortSet =
                new TreeSet<>(
                        (o1, o2) -> (int) (o1.getMaxTime() - o2.getMaxTime())
                );
        sortSet.addAll(processSet);

        if (isHung) {
            System.out.println("进程全部挂起，无法运行");
        } else {
            System.out.println("当前执行的进程是：");
            System.out.println("进程 ID\t进程名\n");
            for (Process process : sortSet) {
                if (process.getState().equals(WAIT)) {
                    for (i = now;
                         i < random.nextInt(
                                 (int) (process.getMaxTime() -
                                         process.getMinTime())
                         ) + process.getMinTime();
                         i++) {
                        try {
                            processIntervalSet.insert(now, now + i, process);
                        } catch (IntervalConflictException ignored) {
                        }
                    }
                    process.setState(RUN);
                    System.out.println(process.getPid() + "\t" +
                            process.getName()
                    );
                }
                now = now + i;
                process.setState(OVER);
            }
        }
    }

    /**
     * 显示进程调度结果
     */
    private static void showFinishedProcess() {
        System.out.println("已进行完的进程：");
        System.out.println("进程 ID\t进程名\t进程开始执行时间\t进程结束时间\n");
        for (Process pover : processSet) {
            if (pover.getState().equals(OVER)) {
                for (Integer j : processIntervalSet.intervals(pover).labels())
                    System.out.println(pover.getPid() +
                            "\t" +
                            pover.getName() +
                            "\t" +
                            processIntervalSet.intervals(pover).start(j) +
                            "\t" +
                            processIntervalSet.intervals(pover).end(j)
                    );
            }
        }
    }

    /**
     * 挂起所有进程
     */
    private static void hungProcess() {
        System.out.println("挂起所有进程");
        isHung = true;
        for (Process process : processSet) {
            if (process.getState().equals(RUN)) {
                process.setState(HUNG);
            }
        }
    }

    /**
     * 恢复所有进程
     */
    private static void restartProcess() {
        System.out.println("恢复所有进程");
        isHung = false;
        for (Process process : processSet) {
            if (process.getState().equals(HUNG)) {
                process.setState(RUN);
            }
        }
    }

    /**
     * 随机调度进程
     */
    private static void randomRunProcess() {
        System.out.println("随机调度进程中");
        System.out.println("当前执行的进程是：");
        System.out.println("进程 ID\t进程名\n");
        if (isHung) {
            System.out.println("进程全部挂起，无法运行");
        } else {
            int allOver = 0;
            int i = 0;
            int now = 0;
            int r = 0;

            while (allOver != processSet.size()) {
                for (Process process : processSet) {
                    if (process.getState().equals(WAIT)) {
                        int rand = random.nextInt(
                                (int) (process.getMaxTime() - 1)
                        ) + 1;
                        r = random.nextInt(5) + now;
                        for (i = r; i < r + rand; i++) {
                            process.setState(RUN);
                        }
                        System.out.println(process.getPid() + "\t" +
                                process.getName() + "\n"
                        );
                    }
                    try {
                        processIntervalSet.insert(r, i, process);
                    } catch (IntervalConflictException ignored) {
                    }
                    now = now + i;
                    if (APIs.intervalsLength(MultiIntervalSet.init(
                            processIntervalSet.intervals(process))) <=
                            process.getMaxTime() &&
                            APIs.intervalsLength(MultiIntervalSet.init(
                                    processIntervalSet.intervals(process))) >=
                                    process.getMinTime()
                    ) {
                        process.setState(OVER);
                        allOver++;
                    } else {
                        process.setState(WAIT);
                    }
                }
            }
        }
    }

    /**
     * 添加新进程
     */
    private static void addProcess() {
        System.out.print("输入新进程名：");
        String newName = scanner.nextLine();
        System.out.print("输入新进程 ID：");
        int newid = scanner.nextInt();
        System.out.print("输入新进程最短执行时间：");
        int newshort = scanner.nextInt();
        System.out.print("输入新进程最长执行时间：");
        int newlong = scanner.nextInt();
        Process newProcess = new Process(
                newid,
                newName,
                newshort,
                newlong,
                WAIT
        );
        processSet.add(newProcess);
        System.out.println("添加成功");
    }
}
