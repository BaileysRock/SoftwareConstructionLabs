package APP;

import API.APIs;
import employee.DutyIntervalSet;
import employee.Employee;
import intervalset.exception.IntervalBlankException;
import intervalset.exception.IntervalConflictException;
import intervalset.exception.IntervalPeriodicException;
import javafx.util.Pair;
import paser.Paser;
import processschedule.Process;
import processschedule.ProcessIntervalSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static paser.Paser.DatePaser;

public class ProcessScheduleApp {

    public static void menu () {
        System.out.println("欢迎使用进程管理APP，请输入所需功能对应数字:");
        System.out.println("1.显示菜单");
        System.out.println("2.增加一组进程，并设置进程相关信息");
        System.out.println("3.启动模拟调度(随机选择进程)");
        System.out.println("4.启动模拟调度(最短进程优先)");
        // System.out.println("5.");
    }

    public static void main(String[] args) {
        ProcessScheduleApp.menu();
        // 功能选项
        String choice;
        // 进程集合

        // 生成随机数，决定当前进程是否执行
        // 若为1则执行进程
        // 若为0则不执行进程
        int randomIsProcess;
        int randomProcessNum;
        long randomProcessTime;

        List<Process> processList = new ArrayList<>();
        ProcessIntervalSet<Process> processProcessIntervalSet = new ProcessIntervalSet<>();
        while (true) {
            System.out.println("请输入您的选项:");
            Scanner in = new Scanner(System.in);
            choice = in.nextLine();
            switch (choice) {
                case "1":
                    ProcessScheduleApp.menu();
                    break;
                case "2":
                    long addPid;
                    String addName;
                    long addMinTime;
                    long addMaxTime;
                    System.out.println("请输入进程名称:");
                    addName = in.nextLine();
                    System.out.println("请输入进程ID:");
                    addPid = Long.parseLong(in.nextLine());
                    System.out.println("请输入进程最短执行时间:");
                    addMinTime = Long.parseLong(in.nextLine());
                    System.out.println("请输入进程最长执行时间:");
                    addMaxTime = Long.parseLong(in.nextLine());
                    processList.add(new Process(addPid,addName,addMinTime,addMaxTime));
                    break;
                case "3":

                    System.out.println("模拟调度(随机选择进程):");
                    System.out.println("时间\t进程名字\t进程ID");
                    for(long time=0;!processList.isEmpty();time++)
                    {
                        randomIsProcess = Math.abs(new Random().nextInt()) % 2;
                        if(randomIsProcess==0)
                        {
                            System.out.println(time+"\t空\t空");
                            continue;
                        }
                        randomProcessNum = Math.abs(new Random().nextInt()) % processList.size();
                        //randomProcessNum = (new Random()).nextInt(processList.size());
                        // 进程随机时间
                        randomProcessTime =(long)Math.abs(new Random().nextInt()) % (processList.get(randomProcessNum).getMaxTime() - processProcessIntervalSet.lastTimeProcess(processList.get(randomProcessNum)));
                        //randomProcessTime =(long)(1+(new Random()).nextInt((int)(processList.get(randomProcessNum).getMaxTime() - processProcessIntervalSet.lastTimeProcess(processList.get(randomProcessNum)))));
                        for(long j = time;j<time + randomProcessTime;j++)
                        {
                            System.out.println(j+"\t"+processList.get(randomProcessNum).getName()+"\t"+processList.get(randomProcessNum).getPid());
                        }
                        try {
                            processProcessIntervalSet.insert(time,time+randomProcessTime,processList.get(randomProcessNum),false);
                        } catch (IntervalConflictException e) {
                            e.printStackTrace();
                        } catch (IntervalBlankException e) {
                            e.printStackTrace();
                        } catch (IntervalPeriodicException e) {
                            e.printStackTrace();
                        }
                        long lastTimeProcess = processProcessIntervalSet.lastTimeProcess(processList.get(randomProcessNum));

                        if(lastTimeProcess >= processList.get(randomProcessNum).getMinTime() &&
                                lastTimeProcess <= processList.get(randomProcessNum).getMaxTime())
                        {
                            processList.remove(randomProcessNum);
                        }
                        time += randomProcessTime;
                        time --;
                    }
                    break;
                case "4":
                    System.out.println("模拟调度(最短进程优先):");
                    System.out.println("时间\t进程名字\t进程ID");
                    for(long time=0;!processList.isEmpty();time++)
                    {
                        randomIsProcess = Math.abs(new Random().nextInt()) % 2;
                        if(randomIsProcess==0)
                        {
                            System.out.println(time+"\t空\t空");
                            continue;
                        }

                        long minTime = processList.get(0).getMaxTime()-  processProcessIntervalSet.lastTimeProcess(processList.get(0));
                        Process minTimeprocess=processList.get(0);

                        for(Process process:processList)
                        {
                            if(minTime>( process.getMaxTime()-  processProcessIntervalSet.lastTimeProcess(process)))
                            {
                                minTime= process.getMaxTime()-  processProcessIntervalSet.lastTimeProcess(process);
                                minTimeprocess = process;
                            }
                        }

                        randomProcessNum = processList.indexOf(minTimeprocess);
                        //randomProcessNum = Math.abs(new Random().nextInt()) % processList.size();
                        //randomProcessNum = (new Random()).nextInt(processList.size());
                        // 进程随机时间
                        randomProcessTime =(long)Math.abs(new Random().nextInt()) % (processList.get(randomProcessNum).getMaxTime() - processProcessIntervalSet.lastTimeProcess(processList.get(randomProcessNum)));
                        //randomProcessTime =(long)(1+(new Random()).nextInt((int)(processList.get(randomProcessNum).getMaxTime() - processProcessIntervalSet.lastTimeProcess(processList.get(randomProcessNum)))));
                        for(long j = time;j<time + randomProcessTime;j++)
                        {
                            System.out.println(j+"\t"+processList.get(randomProcessNum).getName()+"\t"+processList.get(randomProcessNum).getPid());
                        }
                        try {
                            processProcessIntervalSet.insert(time,time+randomProcessTime,processList.get(randomProcessNum),false);
                        } catch (IntervalConflictException e) {
                            e.printStackTrace();
                        } catch (IntervalBlankException e) {
                            e.printStackTrace();
                        } catch (IntervalPeriodicException e) {
                            e.printStackTrace();
                        }
                        long lastTimeProcess = processProcessIntervalSet.lastTimeProcess(processList.get(randomProcessNum));

                        if(lastTimeProcess >= processList.get(randomProcessNum).getMinTime() &&
                                lastTimeProcess <= processList.get(randomProcessNum).getMaxTime())
                        {
                            processList.remove(randomProcessNum);
                        }
                        time += randomProcessTime;
                        time --;
                    }
                    // System.out.println("空闲率");
                    //System.out.println(APIs.calcFreeTimeRatio(processProcessIntervalSet));
                    break;
                case "0":
                    return;
                default:
                    break;


            }


        }


    }



}
