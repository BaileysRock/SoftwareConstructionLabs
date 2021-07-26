package intervalset;

import org.junit.Test;

import static org.junit.Assert.*;

public class IntervalTest {

    @Test
    public void getStart() {
        Interval interval = new Interval(1,2,false);
        assert interval.getStart()==1;
    }

    @Test
    public void getEnd() {

        Interval interval = new Interval(1,2);
        assert interval.getEnd()==2;
    }

    @Test
    public void getperiodicity() {
        Interval interval = new Interval(1,2,false);
        assert !interval.getperiodicity();
    }
}