package intervalset;

import intervalset.exception.IntervalConflictException;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CommonMultiIntervalSetTest {

    @Test
    public void checkRep() {
        CommonMultiIntervalSet<String> commonMultiIntervalSet = new CommonMultiIntervalSet<>();
        String label1 = new String("ab");
        String label2 = new String("ab");
        String label3= new String("ab");
        try {
            commonMultiIntervalSet.insert(5,6,label1);
            commonMultiIntervalSet.insert(2,3,label2);
            commonMultiIntervalSet.insert(3,4,label3);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        }
        Set<String> labelsSet = new HashSet<>();
        labelsSet.add(label1);
        labelsSet.add(label2);
        labelsSet.add(label3);
        // assert commonMultiIntervalSet.labels().equals(labelsSet);
        commonMultiIntervalSet.checkRep();

    }

    @Test
    public void insert() {

        CommonIntervalSet<String> commonIntervalSet = new CommonIntervalSet<>();
        String label4 = new String("pc");
        try {
            commonIntervalSet.insert(1,2,label4);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        }

        CommonMultiIntervalSet<String> commonMultiIntervalSet = new CommonMultiIntervalSet<>(commonIntervalSet);
        String label1 = new String("ab");
        String label2 = new String("ab");
        String label3= new String("ab");
        try {
            commonMultiIntervalSet.insert(5,6,label1);
            commonMultiIntervalSet.insert(2,3,label2);
            commonMultiIntervalSet.insert(3,4,label3);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        }
        Set<String> labelsSet = new HashSet<>();
        labelsSet.add(label1);
        labelsSet.add(label2);
        labelsSet.add(label3);
        labelsSet.add(label4);



        assert commonMultiIntervalSet.labels().equals(labelsSet);
    }

    @Test
    public void labels() {
        CommonMultiIntervalSet<String> commonMultiIntervalSet = new CommonMultiIntervalSet<>();
        String label1 = new String("ab");
        String label2 = new String("ab");
        String label3= new String("ab");
        try {
            commonMultiIntervalSet.insert(5,6,label1);
            commonMultiIntervalSet.insert(2,3,label2);
            commonMultiIntervalSet.insert(3,4,label3);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        }
        Set<String> labelsSet = new HashSet<>();
        labelsSet.add(label1);
        labelsSet.add(label2);
        labelsSet.add(label3);
        assert commonMultiIntervalSet.labels().equals(labelsSet);
    }

    @Test
    public void remove() {
        CommonMultiIntervalSet<String> commonMultiIntervalSet = new CommonMultiIntervalSet<>();
        String label1 = new String("ab");
        String label2 = new String("ab");
        String label3= new String("ab");

        try {
            commonMultiIntervalSet.insert(5,6,label1);
            commonMultiIntervalSet.insert(2,3,label2);
            commonMultiIntervalSet.insert(3,4,label3);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        }
        assert commonMultiIntervalSet.remove(label1);

    }

    @Test
    public void intervals() {
        CommonMultiIntervalSet<String> commonMultiIntervalSet = new CommonMultiIntervalSet<>();
        String label = new String("ab");
        try {
            commonMultiIntervalSet.insert(5,6,label);
            commonMultiIntervalSet.insert(2,3,label);
            commonMultiIntervalSet.insert(3,4,label);
            commonMultiIntervalSet.insert(1,2,label);
            commonMultiIntervalSet.insert(7,8,label);
            commonMultiIntervalSet.insert(9,10,label);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        }
        IntervalSet<Integer> intervalSet = commonMultiIntervalSet.intervals(label);
        IntervalSet<Integer> intervalSet1 = new CommonIntervalSet<>();
        try {
            intervalSet1.insert(1,2,new Integer(0));
            intervalSet1.insert(2,3,new Integer(1));
            intervalSet1.insert(3,4,new Integer(2));
            intervalSet1.insert(5,6,new Integer(3));
            intervalSet1.insert(7,8,new Integer(4));
            intervalSet1.insert(9,10,new Integer(5));
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        }
        assert intervalSet.start(0) == 1;
        assert intervalSet.start(1) == 2;
        assert intervalSet.start(2) == 3;
        assert intervalSet.start(3) == 5;
        assert intervalSet.start(4) == 7;
        assert intervalSet.start(5) == 9;
        assert intervalSet.end(0) == 2;
        assert intervalSet.end(1) == 3;
        assert intervalSet.end(2) == 4;
        assert intervalSet.end(3) == 6;
        assert intervalSet.end(4) == 8;
        assert intervalSet.end(5) == 10;
    }
    @Test
    public void IntervalConflictException()
    {
        CommonMultiIntervalSet<String> commonMultiIntervalSet = new CommonMultiIntervalSet<>();
        String label = new String("ab");
        try {
            commonMultiIntervalSet.insert(5,6,label);
            commonMultiIntervalSet.insert(2,3,label);
            commonMultiIntervalSet.insert(2,3,label);
        } catch (IntervalConflictException e) {
            //e.printStackTrace();
        }

    }


}