package APP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import API.API;
import Parser.Parser;
import Resource.Employee;
import Sets.DutyIntervalSet;
import Timelot.Timelot;
import interval.IntervalConflictException;
import interval.Pair;

public class DutyRosterApp {
	
	
	public static void menu () {
		System.out.println("欢迎使用排班表app，下面是相关功能对应的数字\n");
		System.out.println("1.设置值班表的相关信息；");
		System.out.println("2.添加需要在值班阶段中进行值班的员工和其个人信息；");
		System.out.println("3.删除员工的相关信息；");
		System.out.println("4.随机生成值班表；");
		System.out.println("5.手动生成值班表；");
		System.out.println("6.查看排班表是否被填满；");
		System.out.println("7.查看已经设置好的排班表；");
		System.out.println("8.从外部文件解析相关员工信息：");
		System.out.println("9.删除值班表中某一员工的值班信息；");
		System.out.println("10.退出排班表app");
	} 
	
	public static void main(String []args) throws ParseException, NoSuchElementException, IntervalConflictException, IOException{
		String begin = "2021-6-11";
		String end = "2021-7-11";
		long oneday =60*60*24*1000;
		menu();
		SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd");
		boolean con = true;
		Timelot dutyTimelot = new Timelot(begin, end);
		DutyIntervalSet<Employee> dutyIntervalSet = new DutyIntervalSet<Employee>(dutyTimelot.getBegin(), dutyTimelot.getEnd());
		Set<Employee> employeeSet = new HashSet<Employee>();
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String choice;
		String fileentry,fileoneline;
		System.out.println("最开始请先设置值班表相关信息（输入1）");
		while(con) {
			String[] temp;
			Employee myEmployee;
			System.out.println("请输入你想进行的相关操作：");
			choice = sc.nextLine();
			switch (choice) {
			case "1":
				System.out.println("请输入值班阶段的开始时间：");
				 begin = sc.nextLine();
				System.out.println("请输入值班阶段的结束时间：");
				 end = sc.nextLine();
				dutyTimelot = new Timelot(begin, end);
			    dutyIntervalSet =new DutyIntervalSet<Employee>(dutyTimelot.getBegin(), dutyTimelot.getEnd());
			    System.out.println("时间设置完成！");
				break;
			case "2":
				System.out.println("请输入待加入的员工信息（姓名,职务,电话号码）：");
				try {
					temp = sc.nextLine().split(",");
					myEmployee = new Employee(temp[0], temp[1], temp[2]);
					employeeSet.add(myEmployee);
					System.out.println("添加员工信息成功！\n");
				} catch (ArrayIndexOutOfBoundsException e) {
					// TODO: handle exception
					System.out.println("输入格式有误，未用，分割，请在此执行2操作\n");
					break;
				}
				break;
			case "3":
				System.out.println("请输入要删除的员工信息（姓名）：");
				String delName = sc.nextLine();
				for(Employee label:dutyIntervalSet.labels()) {
					if(label.getName().equals(delName))
						{
						System.out.println("不允许删除有值班任务的员工！\n");
						break;
						}
					else {
						for(Employee em:employeeSet) {
					    if(em.getName().equals(delName)) {
						employeeSet.remove(em);
						System.out.println("删除成功！");
					}
					    else System.out.println("删除了不存在的员工；");
					}
				}
				
				}
				break;
			case "4":
				int i=0;
				
				long newbegin =dutyTimelot.getBegin()+i*oneday;
				long newend = dutyTimelot.getBegin()+(i+1)*oneday;
				Employee lastEmployee = null;
				for(Employee e:employeeSet) {
					if(dutyTimelot.getBegin()+(i+1)*oneday<=dutyTimelot.getEnd()) {
					newbegin =dutyTimelot.getBegin()+i*oneday;
					newend = dutyTimelot.getBegin()+(i+1)*oneday;
					dutyIntervalSet.insert​(newbegin,newend,e);
					lastEmployee = e;
					i++;
					}
					else break;
				}
				if(!dutyIntervalSet.blankSet().isEmpty()) {
					dutyIntervalSet.insert​(newend, dutyTimelot.getEnd(), lastEmployee);
				}
				System.out.println("已经随机生成值班表；");
				
				break;
			case "5":
				System.out.println("请输入选择员工的姓名：");
				String ename = sc.nextLine();
				for(Employee empl: employeeSet) {
					if(empl.getName().equals(ename)) {
						System.out.println("找到了被选中的员工\n");
						System.out.println("请输入员工值班的起始时间和结束时间：");
						temp = sc.nextLine().split(",");
						Timelot dutyTime = new Timelot(temp[0], temp[1]);
						dutyIntervalSet.insert​(dutyTime.getBegin(), dutyTime.getEnd(), empl);
						System.out.println("值班任务添加完成，请用功能6查看值班表是否存在空闲；\n");
						break;
					}
				}
				
				break;
			case "6":
				Set<Pair<Long, Long>> set = dutyIntervalSet.blankSet();
				if(set.isEmpty()) System.out.println("值班表已满！");
				else {
					
					System.out.println("值班表有空闲，空闲时间段是：\n");
					Iterator<Pair<Long, Long>> it = set.iterator();
					while(it.hasNext())
					{
						Pair<Long, Long> blankPair = it.next();
						System.out.println("["+sd.format(new Date(blankPair.getKey()))+","+sd.format(new Date(blankPair.getValue()))+"]\n");
						
					}
					System.out.println("空闲时间占比是："+API.calcFreeTimeRatio(dutyIntervalSet));
				}
				break;
			case "7":
				for(Employee emplo:dutyIntervalSet.labels()) {
					System.out.println("值班开始日期\t值班结束日期\t值班人员姓名\t值班人员职位\t值班人员电话\n");
					for(int j =0;j <dutyIntervalSet.intervals​(emplo).labels().size();j++ ) {
					System.out.println(sd.format(new Date(dutyIntervalSet.intervals​(emplo).start(j)))+"\t"+sd.format(new Date(dutyIntervalSet.intervals​(emplo).end(j)))+"\t"
							+emplo.getName()+"\t"+emplo.getPosition()+"\t"+emplo.getTelephone()+"\n");
					}
				}
				
				break;
			case "8":
				System.out.println("下面提供8个文件可供读取文件(命名格式为test+您要选取的文件序号.txt)；");
				System.out.println("请输入你想要读取值班计划项的文件名(eg test1.txt)：");
				String tempname= sc.nextLine();
				BufferedReader thisfile=new BufferedReader(new FileReader("src/txt/"+tempname));
				fileentry="";
				Parser tempparser=new Parser();
				boolean flag=true;
				while((fileoneline=thisfile.readLine())!=null) { 
					if(fileoneline.equals("")) {  
						continue;
					}
					else {
						fileentry=fileentry+fileoneline+"\n";
					}
				}
				//System.out.print(fileentry);
				flag=tempparser.checkParttenCorrect(fileentry);
				if(flag==false) {
					System.out.println("文件中的信息字符串不符合要求,请读入其他文件\n");
					break;
				}
				else {
					System.out.println("文件中的信息字符串符合要求\n");
					Pattern p0 = Pattern.compile("(\\d{4}\\-\\d{2}\\-\\d{2}),(\\d{4}\\-\\d{2}\\-\\d{2})");
					Matcher m = p0.matcher(tempparser.getAllinformation("Period", fileentry));
					m.find();
					dutyTimelot = new Timelot(m.group(1), m.group(2));
					dutyIntervalSet =new DutyIntervalSet<Employee>(dutyTimelot.getBegin(), dutyTimelot.getEnd()+oneday);
					String newfile =fileentry.replace("\n", "").replace("\r", "").replace("　", "").replace(" ", "");
					Pattern p1 = Pattern.compile("([A-Za-z]+\\{[A-Za-z\s]+),(\\d{3}\\-\\d{4}\\-\\d{4})");
					Matcher m1 = p1.matcher(tempparser.getAllinformation("Employee", newfile));
					while(m1.find()) {
					temp = m1.group(1).split("\\{");
					Employee nEmployee = new Employee(temp[0], temp[1], m1.group(2));
					employeeSet.add(nEmployee);
					}
					
					Pattern p2 = Pattern.compile("([A-Za-z]+\\{\\d{4}\\-\\d{2}\\-\\d{2}),(\\d{4}\\-\\d{2}\\-\\d{2})");
					Matcher m2 = p2.matcher(tempparser.getAllinformation("Roster", newfile));
					//System.out.print(tempparser.getAllinformation("Roster", newfile));
					while(m2.find()) {
					temp = m2.group(1).split("\\{");
					for(Employee e : employeeSet) {
						if(e.getName().equals(temp[0]))
						{
							Timelot aTimelot = new Timelot(temp[1], m2.group(2));
							dutyIntervalSet.insert​(aTimelot.getBegin(), aTimelot.getEnd()+oneday, e);
							break;
						}
					}
				}
				 System.out.println("信息添加成功！");
			}
				break;
	
			case "9":
				System.out.println("请输入要删除的员工信息（姓名）：");
				int res=0;
				String DelDuty = sc.nextLine();
				for(Employee label:dutyIntervalSet.labels()) {
					if(label.getName().equals(DelDuty))
						{
						dutyIntervalSet.remove(label);
						System.out.println("删除成功！\n");
						res =1;
						}
				}
				if(res==0) System.out.println("未在值班表中找到要删除的员工。");
				break;
			case "10":
				
				for(Employee employee :employeeSet) {
					if(dutyIntervalSet.labels().contains(employee))
					{
						dutyIntervalSet.remove(employee);
					}
				}
				System.out.println("退出app成功，欢迎下次使用！");
				employeeSet.clear();
				con = false;
				break;
			default:
				System.out.println("输入的指令有错误，请重新输入！\n");
				break;
			}
		}
	}
}
