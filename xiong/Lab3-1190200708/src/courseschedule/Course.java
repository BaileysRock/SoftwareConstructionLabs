package courseschedule;

import java.util.Objects;

public class Course {


    // Abstraction function:
    // 由CourseID、CourseName、TeacherName、Place、studyTime映射的课程
    // 包含课程的ID、课程的名字、教师的名字、授课地点、每周学时
    // Representation invariant:
    // CourseID、CourseName、TeacherName、Place不为空
    // studyTime 为偶数且大于0
    // Safety from rep exposure:
    // 使用private、final修饰变量
    // 采用防御性复制


    private final String CourseID;
    private final String CourseName;
    private final String TeacherName;
    private final String Place;
    private final int studyTime;

    /**
     * Constructor
     * 构造函数
     * @param CourseID 课程ID
     * @param CourseName 课程名字
     * @param TeacherName 教师名字
     * @param Place 地点
     * @param studyTime 周学时数
     */
    public Course(String CourseID,String CourseName,String TeacherName,String Place,int studyTime) {
        this.CourseID = CourseID;
        this.CourseName = CourseName;
        this.Place = Place;
        this.TeacherName = TeacherName;
        this.studyTime = studyTime;
    }


    //get方法对private进行获取
    public String getCid() {
        return CourseID;
    }

    public String getCname() {
        return CourseName;
    }
    public String getTname() {
        return TeacherName;
    }
    public String getPlace() {
        return Place;
    }
    public int getStudyNum() {
        return studyTime;
    }




    @Override
    public boolean equals(Object object) {
        if(this == object) return true;
        if(object == null) return false;
        Course CourseObject = (Course) object;
        return Objects.equals(this.CourseName, CourseObject.CourseName) &&
                Objects.equals(this.TeacherName, CourseObject.TeacherName) &&
                Objects.equals(this.Place, CourseObject.Place) &&
                Objects.equals(this.CourseID, CourseObject.CourseID);

    }

    @Override
    public int hashCode() {
        return this.CourseName.hashCode()
                * this.CourseID.hashCode()
                * this.TeacherName.hashCode()
                * this.Place.hashCode()*this.studyTime;
    }

}
