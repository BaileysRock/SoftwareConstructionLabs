package APP;

import java.text.ParseException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import API.API;
import Resource.Process;
import Sets.ProcessIntervalSet;
import interval.IntervalConflictException;
import interval.IntervalSet;
import interval.MultiIntervalSet;

public class ProcessScheduleApp {

	public static void menu () {
		System.out.println("欢迎使用进程管理app，下面是相关功能对应的数字\n");
		System.out.println("1.增加一组进程，并设置进程相关信息；");
		System.out.println("2.随机模拟调度进程；");
		System.out.println("3.继续执行进程调度；");
		System.out.println("4.不选择执行任何进程；");
		System.out.println("5.显示当前时刻之前的进程调度结果；");
		System.out.println("6.执行最短时间进程调度模拟；");
		System.out.println("7.退出进程管理app；");
		} 
	public static void main(String []args) throws ParseException, IntervalConflictException{
		int now =0;
		String waitState = "wait";
		String runState  = "run";
		String hangState = "hung";
		String overState = "over";
		int i = 0;
		int rad =0;
		int isHanging = 0;
		Random rand = new Random();
		boolean con = true;
		IntervalSet<Process> init = IntervalSet.empty();
		ProcessIntervalSet<Process> processIntervalSet = new ProcessIntervalSet<Process>(init);
		Set<Process> processSet = new HashSet<Process>();
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String choice;
		menu();
		System.out.println("最开始请先设置一组进程（输入1）");
		while(con) {
			System.out.println("请输入您要进行的操作：");
			choice = sc.nextLine();
		switch (choice) {
		case "1":
			System.out.println("请输入您要添加的进程信息（进程名）：");
			String newName = sc.nextLine();
			System.out.println("请输入您要添加的进程信息（进程id)：");
			int newid = sc.nextInt();
			System.out.println("请输入您要添加的进程信息（进程最短执行时间）：");
			int newshort = sc.nextInt();
			System.out.println("请输入您要添加的进程信息（进程最长执行时间）：");
			int newlong = sc.nextInt();
			 sc.nextLine();
			Process newProcess = new Process(newid, newName, newshort, newlong,waitState);
			processSet.add(newProcess);
			System.out.println("添加成功！");
			break;
		case "2":
			System.out.println("随机进程模拟中..........");
			System.out.println("当前时刻执行的进程是：\n");
			System.out.println("进程id\t进程名\n");
			if(isHanging==1) {
				System.out.println("进程全部挂起，无法进行模拟，请先恢复进程。\n");
			}
			else{
				int allOver = 0;
				while(allOver != processSet.size()){
			    for(Process p :processSet) {
				if(p.getState().equals(waitState)) {
					int rd =rand.nextInt(p.getLongestTime()-1)+1;
				    rad = rand.nextInt(5)+now;
					for(i=rad;i<rad+rd;i++)
					p.setState(runState);
					System.out.println(p.getPid()+"\t"+p.getPname()+"\n");
				}
				processIntervalSet.insert​(rad,i,p);
				now = now +i;
				if(API.intervalTolen(MultiIntervalSet.init(processIntervalSet.intervals​(p)))<=p.getLongestTime()&&API.intervalTolen(MultiIntervalSet.init(processIntervalSet.intervals​(p)))>=p.getShortestTime()) {
					p.setState(overState);
					allOver++;
				}
				else {
					p.setState(waitState);
				}
			}
				}
		}
			break;
		case "3":
			System.out.println("进程恢复中.....");
			isHanging = 0;
			for(Process prun: processSet) {
				if(prun.getState().equals(hangState)) {
					prun.setState(runState);
				}
			}
			break;
		case "4":
			System.out.println("正在挂起所有进程......");
			isHanging = 1;
			for(Process prun: processSet) {
				if(prun.getState().equals(runState)) {
					prun.setState(hangState);
				}
			}
			break;
		case "5":
			System.out.println("当前时刻已经进行完的进程如下：\n");
			System.out.println("进程id\t进程名\t进程开始执行时间\t进程结束时间\n");
			for(Process pover: processSet) {
				if(pover.getState().equals(overState)) {
					for(Integer j:processIntervalSet.intervals​(pover).labels())
					System.out.println(pover.getPid()+"\t"+pover.getPname()+"\t"+processIntervalSet.intervals​(pover).start(j)+"\t"+processIntervalSet.intervals​(pover).end(j)+"\n");
				}
			}
			break;
		case "6":
			System.out.println("模拟最短时间进程调度中..........\n");
			Set<Process> sortSet = new TreeSet<Process>(new Comparator<Process>() {
	            @Override
	            public int compare(Process o1, Process o2) {
	                return o1.getLongestTime()-o2.getLongestTime();
	            }
	        });
	        sortSet.addAll(processSet);
	        
			if(isHanging==1) {
				System.out.println("进程全部挂起，无法进行模拟，请先恢复进程。\n");
			}
			else{
			System.out.println("当前时刻执行的进程是：\n");
			System.out.println("进程id\t进程名\n");
			   for(Process p :sortSet) {
				if(p.getState().equals(waitState)) {
					for(i=now;i<rand.nextInt(p.getLongestTime()-p.getShortestTime())+p.getShortestTime();i++)
					processIntervalSet.insert​(now,now+i,p);
					p.setState(runState);
					System.out.println(p.getPid()+"\t"+p.getPname()+"\n");
				}
				now = now +i;
				p.setState(overState);
			}
		}
			break;
		case "7":
			System.out.println("退出app成功，欢迎下次使用！");
        	con = false;
			break;
		default:
			System.out.println("输入的指令有错误，请重新输入！\n");
			break;
		}
		
	}
	}
}