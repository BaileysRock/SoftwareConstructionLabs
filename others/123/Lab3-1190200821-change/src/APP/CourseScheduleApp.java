package APP;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import API.API;
import Resource.Course;
import Sets.CourseIntervalSet;
import Timelot.Timelot;
import interval.IntervalConflictException;
import interval.IntervalSet;

public class CourseScheduleApp {

	
	public static void menu () {
		System.out.println("欢迎使用课程表app，下面是相关功能对应的数字\n");
		System.out.println("1.设置课程表的相关信息；(学期开始日期，学期周数)");
		System.out.println("2.添加设定学期中应该进行的课程和课程相关信息；");
		System.out.println("3.删除指定课程的相关信息；");
		System.out.println("4.手动生成课程表；");
		System.out.println("5.查看未被安置的课程信息；");
		System.out.println("6.查看已经设置好的课程表；");
		System.out.println("7.查看每周空闲时间占比；");
		System.out.println("8.查看每周的课程重复时间比例；");
		System.out.println("9.查看特定日期的课表结果：");
		System.out.println("10.退出课程表app");
	} 
	public static void main(String []args) throws ParseException, IntervalConflictException{
		String begin = "2021-6-11";
		String end = "2021-7-11";
		long oneday =60*60*24*1000;
		int week =4;
		SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd");
		boolean con = true;
		Timelot courseTimelot = new Timelot(begin, end);
		CourseIntervalSet<Course> courseIntervalSet = new CourseIntervalSet<Course>(35) ;
		Set<Course> courseSet  = new HashSet<Course>();
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String choice;
		menu();
		System.out.println("最开始请先设置课程表相关的信息（输入1）");
		while(con)
		{
			String[] temp;
			Course newCourse;
			System.out.println("请输入你想进行的相关操作：");
			choice = sc.nextLine();
			switch (choice) {
			case "1":
				System.out.println("请输入本次学期的开始时间(格式为yyyy-MM-dd)：");
				 begin = sc.nextLine();
				System.out.println("请输入本次学期的周数：");
				 week = Integer.valueOf(sc.next());
				 sc.nextLine();
				courseTimelot = new Timelot(begin, end);
				long endLong = courseTimelot.getBegin()+week*7*oneday;
				courseTimelot = new Timelot(begin, sd.format(new Date(endLong)));
				System.out.println("添加成功！");
				break;
            case "2":
            	System.out.println("请输入待加入的课程信息（课程Id,课程名,教师姓名，上课地点）：");
            	temp = sc.nextLine().split(",");
            	
            	System.out.println("请输入待加入的课程信息（周学时数）：");
            	int shours= sc.nextInt();
            	while(shours % 2!=0) {
            		System.out.println("周学时数必须为偶数,请重新输入课程信息");
            		shours = sc.nextInt();
            	}
            	newCourse = new Course(temp[0], temp[1], temp[2], temp[3],shours);
            	courseSet.add(newCourse);
            	System.out.println("课程添加成功！");
            	
				break;
            case "3":
            	int flag =0;
				System.out.println("请输入你想删除的课程ID:");
			    String delId = sc.nextLine();
			    for(Course delc :courseSet) {
			    	if(delc.getCid().equals(delId)) {
			    		courseSet.remove(delc);
			    	    flag = 1;
			    	}
			    }
			    if(flag ==1) System.out.println("删除相关课程成功！");
			    else System.out.println("未找到您想删除的课程！");
			    break;
            case "4":
            	int find =0;
            	System.out.println("请输入你想设置的课程ID:");
            	String setId = sc.nextLine();
            	for(Course setc :courseSet) {
			    	if(setc.getCid().equals(setId)) {
			    	find =1;
			    	for(int i=0;i<(setc.getStudyNum()/2);i++) {
			    	System.out.println("请输入你想设置的课程上课时间是星期几（1-7）:");
			    	int cweekday = sc.nextInt();
			    	System.out.println("请输入你想设置的课程具体开始上课的课节数(一天最多只有5节课输入1，3，5，7，9)：");
			    	int cb = sc.nextInt();
			    	 sc.nextLine();
            		courseIntervalSet.insert​(5*(cweekday-1)+cb/2, 5*(cweekday-1)+cb/2+1, setc);
             		System.out.println("添加信息成功");
			    	}
			    }
			    }
            	if(find == 0) System.out.println("未找到相关课程！");
            	
				break;
            case "5":
            	System.out.println("未被添加进课表的课程信息如下：\n");
            	System.out.println("课程id\t课程名\t教师姓名\t上课教室\t周学时数\n");
            	for(Course c :courseSet) {
            	 if(courseIntervalSet.labels().contains(c));
            	 else System.out.println(c.getCid()+"\t"+c.getCname()+"\t"+c.getTname()+"\t"+c.getPlace()+"\t"+c.getStudyNum()+"\n");
            	}
				break;
            case "6":
            	System.out.println("已经被添加进课表的课程信息如下：\n");
            	System.out.println("课程id\t课程名\t教师姓名\t上课教室\t周学时数\n");
            	for(Course c :courseSet) {
            	 if(courseIntervalSet.labels().contains(c))
            		 System.out.println(c.getCid()+"\t"+c.getCname()+"\t"+c.getTname()+"\t"+c.getPlace()+"\t"+c.getStudyNum()+"\n");
            	}
				break;
            case "7":
            	System.out.println("每周空闲时间占比：");
            	System.out.print(API.calcFreeTimeRatio(courseIntervalSet));
				break;
            case "8":
            	System.out.println("每周课程重复时间时间占比：");
            	System.out.println(API.calcConflictRatio(courseIntervalSet));
				break;
            case "9":
				System.out.println("请输入你想查看课表的日期（yyyy-MM-dd）：");
				String dayString = sc.nextLine();
				Timelot transTimelot = new Timelot(dayString, end);
				long  weekday = ((transTimelot.getBegin()-courseTimelot.getBegin())/oneday)%7;
				System.out.println("当日课表如下：\n");
				for(Course c :courseIntervalSet.labels()) {
					for(int i = 0;i<c.getStudyNum()/2;i++) {
					IntervalSet<Integer> test = courseIntervalSet.intervals​(c);
					if(test.start(i)== 5*(weekday)) {
						System.out.println( "8:00-10:00"+"\t"+c.getCname()+"\n");
					}
					else if(courseIntervalSet.intervals​(c).start(i)==5*(weekday)+1 ) {
						System.out.println( "10:00-12:00"+"\t"+c.getCname()+"\n");
					}
					else if(courseIntervalSet.intervals​(c).start(i)== 5*(weekday)+2) {
						System.out.println( "13:00-15:00"+"\t"+c.getCname()+"\n");
					}
					else if(courseIntervalSet.intervals​(c).start(i)== 5*(weekday)+3) {
						System.out.println( "15:00-17:00"+"\t"+c.getCname()+"\n");
					}
					else if(courseIntervalSet.intervals​(c).start(i)== 5*(weekday)+4) {
						System.out.println( "19:00-21:00"+"\t"+c.getCname()+"\n");
					}
					}
				}
				
				break;
            case "10":
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
