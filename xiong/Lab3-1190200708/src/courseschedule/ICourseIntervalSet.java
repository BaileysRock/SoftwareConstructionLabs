package courseschedule;

import java.util.Date;
import java.util.List;

public interface ICourseIntervalSet <L> {


    /**
     *
     * @param start 开始时间 毫秒
     * @param end 结束时间 毫秒
     * @param label 标签
     * @param studyTime 总分配学时
     * @return 是否插入成功
     */
    public boolean insert(long start,long end,L label,long studyTime);


    /**
     * 获得所有课程的列表
     * @return 所有课程的列表
     */
    public List<Course> getAllCourseList();




    /**
     * 添加课程
     * @param CourseID 课程ID
     * @param CourseName 课程名称
     * @param TeacherName 教师姓名
     * @param Place 授课地点
     * @param studyTime 周学时
     * @return 是否成功添加
     */
    public boolean addCourse(String CourseID,String CourseName,String TeacherName,String Place,int studyTime);


    /**
     * 返回开始时间
     * @return 返回学期开始时间
     */
    public Date getStart();


    /**
     * 学期周数
     * @return 返回周数
     */
    public int getWeeksNum();

}
