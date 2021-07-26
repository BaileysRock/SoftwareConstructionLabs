package courseschedule;

import org.junit.Test;

import static org.junit.Assert.*;

public class CourseTest {

    @Test
    public void getCid() {
        String CourseID = "id";
        String CourseName = "name";
        String TeacherName = "teacherName";
        String Place = "place";
        int studyTime = 2;
        Course course = new Course(CourseID,CourseName,TeacherName,Place,studyTime);
        assert course.getCid().equals(CourseID);
    }

    @Test
    public void getCname() {
        String CourseID = "id";
        String CourseName = "name";
        String TeacherName = "teacherName";
        String Place = "place";
        int studyTime = 2;
        Course course = new Course(CourseID,CourseName,TeacherName,Place,studyTime);
        assert course.getCname().equals(CourseName);
    }

    @Test
    public void getTname() {
        String CourseID = "id";
        String CourseName = "name";
        String TeacherName = "teacherName";
        String Place = "place";
        int studyTime = 2;
        Course course = new Course(CourseID,CourseName,TeacherName,Place,studyTime);
        assert course.getTname().equals(TeacherName);
    }

    @Test
    public void getPlace() {
        String CourseID = "id";
        String CourseName = "name";
        String TeacherName = "teacherName";
        String Place = "place";
        int studyTime = 2;
        Course course = new Course(CourseID,CourseName,TeacherName,Place,studyTime);
        assert course.getPlace().equals(Place);
    }

    @Test
    public void getStudyNum() {
        String CourseID = "id";
        String CourseName = "name";
        String TeacherName = "teacherName";
        String Place = "place";
        int studyTime = 2;
        Course course = new Course(CourseID,CourseName,TeacherName,Place,studyTime);
        assert course.getStudyNum()==studyTime;
    }

    @Test
    public void testEquals() {
        String CourseID = "id";
        String CourseName = "name";
        String TeacherName = "teacherName";
        String Place = "place";
        int studyTime = 2;
        Course course = new Course(CourseID,CourseName,TeacherName,Place,studyTime);
        Course course1 = new Course(CourseID,CourseName,TeacherName,Place,studyTime);

        assert course.equals(course1);
    }

    @Test
    public void testHashCode() {
        String CourseID = "id";
        String CourseName = "name";
        String TeacherName = "teacherName";
        String Place = "place";
        int studyTime = 2;
        Course course = new Course(CourseID,CourseName,TeacherName,Place,studyTime);
        assert course.hashCode() ==705917222;
    }
}