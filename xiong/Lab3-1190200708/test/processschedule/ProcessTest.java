package processschedule;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProcessTest {

    @Test
    public void getPid() {
        long pid = 1;
        String name = "process";
        long min= 10;
        long max= 12;
        Process process = new Process(pid,name,min,max);
        assert process.getPid()==pid;

    }

    @Test
    public void getName() {
        long pid = 1;
        String name = "process";
        long min= 10;
        long max= 12;
        Process process = new Process(pid,name,min,max);
        assert process.getName().equals(name);
    }

    @Test
    public void getMinTime() {
        long pid = 1;
        String name = "process";
        long min= 10;
        long max= 12;
        Process process = new Process(pid,name,min,max);
        assert process.getMinTime()==min;
    }

    @Test
    public void getMaxTime() {
        long pid = 1;
        String name = "process";
        long min= 10;
        long max= 12;
        Process process = new Process(pid,name,min,max);
        assert process.getMaxTime()==max;
    }
}