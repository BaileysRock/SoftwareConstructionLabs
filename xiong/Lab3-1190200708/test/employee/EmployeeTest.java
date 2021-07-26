package employee;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeTest {

    @Test
    public void checkRep() {
        String name = "name";
        String position = "position";
        String phonenumber = "13112345678";
        Employee employee = new Employee(name,position,phonenumber);
        employee.checkRep();
    }

    @Test
    public void testToString() {
        String name = "name";
        String position = "position";
        String phonenumber = "13112345678";
        Employee employee = new Employee(name,position,phonenumber);
        String pattern = new String("name\tposition\t13112345678");
        assert pattern.equals(employee.toString());
    }

    @Test
    public void getName() {
        String name = "name";
        String position = "position";
        String phonenumber = "13112345678";
        Employee employee = new Employee(name,position,phonenumber);
        assert employee.getName().equals(name);
    }

    @Test
    public void getPosition() {
        String name = "name";
        String position = "position";
        String phonenumber = "13112345678";
        Employee employee = new Employee(name,position,phonenumber);
        assert employee.getPosition().equals(position);
    }

    @Test
    public void getPhonenumber() {
        String name = "name";
        String position = "position";
        String phonenumber = "13112345678";
        Employee employee = new Employee(name,position,phonenumber);
        assert employee.getPhonenumber().equals(phonenumber);
    }
}