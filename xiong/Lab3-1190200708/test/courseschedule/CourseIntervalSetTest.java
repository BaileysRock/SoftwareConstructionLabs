package courseschedule;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class CourseIntervalSetTest {

    @Test
    public void freeRatio() {
        CourseIntervalSet<Course> courseIntervalSet = new CourseIntervalSet<>(0,18);
        String CourseID = "id";
        String CourseName = "name";
        String TeacherName = "teacherName";
        String Place = "place";
        int studyTime = 2;
        Course course = new Course(CourseID,CourseName,TeacherName,Place,studyTime);
        courseIntervalSet.insert(0,2 * 60 * 60 * 1000,course,2);
        String CourseID1 = "id1";
        String CourseName1 = "name1";
        String TeacherName1 = "teacherName1";
        String Place1 = "place1";
        int studyTime1 = 4;
        Course course1 = new Course(CourseID1,CourseName1,TeacherName1,Place1,studyTime1);
        courseIntervalSet.insert(6 * 60 * 60 * 1000,8 * 60 * 60 * 1000,course1,4);
        assert courseIntervalSet.freeRatio()==0.5;
    }

    @Test
    public void conflictRatio() {

        CourseIntervalSet<Course> courseIntervalSet = new CourseIntervalSet<>(0,18);
        String CourseID = "id";
        String CourseName = "name";
        String TeacherName = "teacherName";
        String Place = "place";
        int studyTime = 2;
        Course course = new Course(CourseID,CourseName,TeacherName,Place,studyTime);
        courseIntervalSet.insert(0,2 * 60 * 60 * 1000,course,2);
        String CourseID1 = "id1";
        String CourseName1 = "name1";
        String TeacherName1 = "teacherName1";
        String Place1 = "place1";
        int studyTime1 = 4;
        Course course1 = new Course(CourseID1,CourseName1,TeacherName1,Place1,studyTime1);
        courseIntervalSet.insert(4 * 60 * 60 * 1000,8 * 60 * 60 * 1000,course1,4);
        String CourseID12 = "id12";
        String CourseName12 = "name12";
        String TeacherName12 = "teacherName12";
        String Place12 = "place12";
        int studyTime12 = 4;
        Course course12 = new Course(CourseID12,CourseName12,TeacherName12,Place12,studyTime12);
        courseIntervalSet.insert(6 * 60 * 60 * 1000,8 * 60 * 60 * 1000,course12,4);
        assert courseIntervalSet.conflictRatio()==0.25;
    }

    @Test
    public void getAllCourseList() {
        CourseIntervalSet<Course> courseIntervalSet = new CourseIntervalSet<>(0,18);
        String CourseID = "id";
        String CourseName = "name";
        String TeacherName = "teacherName";
        String Place = "place";
        int studyTime = 2;
        Course course = new Course(CourseID,CourseName,TeacherName,Place,studyTime);
        courseIntervalSet.insert(0,2 * 60 * 60 * 1000,course,2);
        String CourseID1 = "id1";
        String CourseName1 = "name1";
        String TeacherName1 = "teacherName1";
        String Place1 = "place1";
        int studyTime1 = 4;
        Course course1 = new Course(CourseID1,CourseName1,TeacherName1,Place1,studyTime1);
        courseIntervalSet.insert(6 * 60 * 60 * 1000,8 * 60 * 60 * 1000,course1,4);
        List<Course> courseList = new ArrayList<>();
        courseList.add(course);
        courseList.add(course1);
        courseIntervalSet.addCourse(CourseID,CourseName,TeacherName,Place,studyTime);
        assert courseIntervalSet.getAllCourseList().size()==1;
    }

    @Test
    public void getWeeksNum() {

        CourseIntervalSet<Course> courseIntervalSet = new CourseIntervalSet<>(0,18);
        assert courseIntervalSet.getWeeksNum()==18;
    }

    @Test
    public void getStart() {
        CourseIntervalSet<Course> courseIntervalSet = new CourseIntervalSet<>(0,18);
        assert courseIntervalSet.getStart().equals(new Date(0));
    }

    @Test
    public void addCourse() {
        CourseIntervalSet<Course> courseIntervalSet = new CourseIntervalSet<>(0,18);
        String CourseID = "id";
        String CourseName = "name";
        String TeacherName = "teacherName";
        String Place = "place";
        int studyTime = 2;
        Course course = new Course(CourseID,CourseName,TeacherName,Place,studyTime);
        courseIntervalSet.insert(0,2 * 60 * 60 * 1000,course,2);
        String CourseID1 = "id1";
        String CourseName1 = "name1";
        String TeacherName1 = "teacherName1";
        String Place1 = "place1";
        int studyTime1 = 4;
        Course course1 = new Course(CourseID1,CourseName1,TeacherName1,Place1,studyTime1);
        courseIntervalSet.insert(6 * 60 * 60 * 1000,8 * 60 * 60 * 1000,course1,4);
        List<Course> courseList = new ArrayList<>();
        courseList.add(course);
        courseList.add(course1);
        courseIntervalSet.addCourse(CourseID,CourseName,TeacherName,Place,studyTime);
        assert courseIntervalSet.getAllCourseList().size()==1;
    }

    @Test
    public void insert() {
        CourseIntervalSet<Course> courseIntervalSet = new CourseIntervalSet<>(0,18);
        String CourseID = "id";
        String CourseName = "name";
        String TeacherName = "teacherName";
        String Place = "place";
        int studyTime = 2;
        Course course = new Course(CourseID,CourseName,TeacherName,Place,studyTime);
        courseIntervalSet.insert(0,2 * 60 * 60 * 1000,course,2);
        String CourseID1 = "id1";
        String CourseName1 = "name1";
        String TeacherName1 = "teacherName1";
        String Place1 = "place1";
        int studyTime1 = 4;
        Course course1 = new Course(CourseID1,CourseName1,TeacherName1,Place1,studyTime1);
        courseIntervalSet.insert(4 * 60 * 60 * 1000,8 * 60 * 60 * 1000,course1,4);
        String CourseID12 = "id12";
        String CourseName12 = "name12";
        String TeacherName12 = "teacherName12";
        String Place12 = "place12";
        int studyTime12 = 4;
        Course course12 = new Course(CourseID12,CourseName12,TeacherName12,Place12,studyTime12);
        courseIntervalSet.insert(6 * 60 * 60 * 1000,8 * 60 * 60 * 1000,course12,4);
        //courseIntervalSet.insert(8 * 60 * 60 * 1000,10 * 60 * 60 * 1000,course12,4);
        assert courseIntervalSet.conflictRatio()==0.25;
    }
}