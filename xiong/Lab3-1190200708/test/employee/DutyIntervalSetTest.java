package employee;

import intervalset.exception.IntervalBlankException;
import intervalset.exception.IntervalConflictException;
import intervalset.exception.IntervalPeriodicException;
import org.junit.Test;

import static org.junit.Assert.*;

public class DutyIntervalSetTest {

    @Test
    public void checkRep() {
        String name = "name";
        String position = "position";
        String phonenumber = "13112345678";
        Employee employee = new Employee(name,position,phonenumber);
        DutyIntervalSet<Employee> dutyIntervalSet = new DutyIntervalSet<>(1,10);

        try {
            dutyIntervalSet.insert(1,10,employee,false);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        } catch (IntervalBlankException e) {
            e.printStackTrace();
        } catch (IntervalPeriodicException e) {
            e.printStackTrace();
        }
        dutyIntervalSet.checkRep();
    }


    @Test
    public void showFreeTime() {
        String name = "name";
        String position = "position";
        String phonenumber = "13112345678";
        Employee employee = new Employee(name,position,phonenumber);

        String name1 = "name1";
        String position1 = "position1";
        String phonenumber1 = "13112345679";
        Employee employee1 = new Employee(name1,position1,phonenumber1);
        DutyIntervalSet<Employee> dutyIntervalSet = new DutyIntervalSet<>(20201220,20210110);

        try {
            dutyIntervalSet.insert(20201220,20201228,employee,false);
            dutyIntervalSet.insert(20210103,20210110,employee1,false);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        } catch (IntervalBlankException e) {
            e.printStackTrace();
        } catch (IntervalPeriodicException e) {
            e.printStackTrace();
        }
        dutyIntervalSet.showFreeTime();


    }

    @Test
    public void showTimeTable() {
        String name = "name";
        String position = "position";
        String phonenumber = "13112345678";
        Employee employee = new Employee(name,position,phonenumber);

        String name1 = "name1";
        String position1 = "position1";
        String phonenumber1 = "13112345679";
        Employee employee1 = new Employee(name1,position1,phonenumber1);
        DutyIntervalSet<Employee> dutyIntervalSet = new DutyIntervalSet<>(20201220,20210110);

        try {
            dutyIntervalSet.insert(20201220,20201228,employee,false);
            dutyIntervalSet.insert(20210103,20210110,employee1,false);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        } catch (IntervalBlankException e) {
            e.printStackTrace();
        } catch (IntervalPeriodicException e) {
            e.printStackTrace();
        }
        dutyIntervalSet.showTimeTable();
    }

    @Test
    public void insert() {
        String name = "name";
        String position = "position";
        String phonenumber = "13112345678";
        Employee employee = new Employee(name,position,phonenumber);

        String name1 = "name1";
        String position1 = "position1";
        String phonenumber1 = "13112345679";
        Employee employee1 = new Employee(name1,position1,phonenumber1);
        DutyIntervalSet<Employee> dutyIntervalSet = new DutyIntervalSet<>(20201220,20210110);

        try {
            dutyIntervalSet.insert(20201220,20201228,employee,false);
            dutyIntervalSet.insert(20210103,20210110,employee1,false);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        } catch (IntervalBlankException e) {
            e.printStackTrace();
        } catch (IntervalPeriodicException e) {
            e.printStackTrace();
        }
        dutyIntervalSet.showTimeTable();
    }

    @Test
    public void checkNoBlank() {
        String name = "name";
        String position = "position";
        String phonenumber = "13112345678";
        Employee employee = new Employee(name,position,phonenumber);

        String name1 = "name1";
        String position1 = "position1";
        String phonenumber1 = "13112345679";
        Employee employee1 = new Employee(name1,position1,phonenumber1);
        DutyIntervalSet<Employee> dutyIntervalSet = new DutyIntervalSet<>(20201220,20210110);

        try {
            dutyIntervalSet.insert(20201220,20201228,employee,false);
            dutyIntervalSet.insert(20210103,20210110,employee1,false);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        } catch (IntervalBlankException e) {
            e.printStackTrace();
        } catch (IntervalPeriodicException e) {
            e.printStackTrace();
        }
        // dutyIntervalSet.showTimeTable();


        assert dutyIntervalSet.checkNoBlank(20201220,20201220);
    }

    @Test
    public void checkNoOverlap() {
        String name = "name";
        String position = "position";
        String phonenumber = "13112345678";
        Employee employee = new Employee(name,position,phonenumber);

        String name1 = "name1";
        String position1 = "position1";
        String phonenumber1 = "13112345679";
        Employee employee1 = new Employee(name1,position1,phonenumber1);
        DutyIntervalSet<Employee> dutyIntervalSet = new DutyIntervalSet<>(20201220,20210110);

        try {
            dutyIntervalSet.insert(20201220,20201228,employee,false);
            dutyIntervalSet.insert(20210103,20210110,employee1,false);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        } catch (IntervalBlankException e) {
            e.printStackTrace();
        } catch (IntervalPeriodicException e) {
            e.printStackTrace();
        }
        // dutyIntervalSet.showTimeTable();
        assert !dutyIntervalSet.checkNoOverlap(20201220,20210110);

    }

    @Test
    public void testCheckNoOverlap() {
        String name = "name";
        String position = "position";
        String phonenumber = "13112345678";
        Employee employee = new Employee(name,position,phonenumber);

        String name1 = "name1";
        String position1 = "position1";
        String phonenumber1 = "13112345679";
        Employee employee1 = new Employee(name1,position1,phonenumber1);
        DutyIntervalSet<Employee> dutyIntervalSet = new DutyIntervalSet<>(20201220,20210110);

        try {
            dutyIntervalSet.insert(20201220,20201228,employee,false);
            dutyIntervalSet.insert(20210103,20210110,employee1,false);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        } catch (IntervalBlankException e) {
            e.printStackTrace();
        } catch (IntervalPeriodicException e) {
            e.printStackTrace();
        }
        // dutyIntervalSet.showTimeTable();
        assert !dutyIntervalSet.checkNoOverlap(20201220,20210110);
    }

    @Test
    public void checkNoPeriodic() {
        String name = "name";
        String position = "position";
        String phonenumber = "13112345678";
        Employee employee = new Employee(name,position,phonenumber);

        String name1 = "name1";
        String position1 = "position1";
        String phonenumber1 = "13112345679";
        Employee employee1 = new Employee(name1,position1,phonenumber1);
        DutyIntervalSet<Employee> dutyIntervalSet = new DutyIntervalSet<>(20201220,20210110);

        try {
            dutyIntervalSet.insert(20201220,20201228,employee,false);
            dutyIntervalSet.insert(20210103,20210110,employee1,false);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        } catch (IntervalBlankException e) {
            e.printStackTrace();
        } catch (IntervalPeriodicException e) {
            e.printStackTrace();
        }
        // dutyIntervalSet.showTimeTable();
        assert dutyIntervalSet.checkNoPeriodic();
    }
}