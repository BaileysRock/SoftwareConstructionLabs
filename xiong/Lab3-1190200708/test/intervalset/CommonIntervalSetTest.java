package intervalset;

import intervalset.exception.IntervalConflictException;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class CommonIntervalSetTest {

    @Test
    public void checkRep() {
        CommonIntervalSet<String> commonIntervalSet = new CommonIntervalSet();
        String label1 = "1";
        try {
            commonIntervalSet.insert(1,2,label1);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        }
        commonIntervalSet.checkRep();
    }

    @Test
    public void insert() {
        CommonIntervalSet<String> commonIntervalSet = new CommonIntervalSet();
        String label1 = "1";
        try {
            commonIntervalSet.insert(1,2,label1);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        }
        commonIntervalSet.checkRep();
    }

    @Test
    public void labels() {

        CommonIntervalSet<String> commonIntervalSet = new CommonIntervalSet();
        String label1 = "1";
        String label2 = "2";
        try {
            commonIntervalSet.insert(1,2,label1);
            commonIntervalSet.insert(2,3,label2);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        }
        Set<String> labelsSet = new HashSet<>();
        labelsSet.add(label1);
        labelsSet.add(label2);
        assert  commonIntervalSet.labels().equals(labelsSet);
    }

    @Test
    public void remove() {
        CommonIntervalSet<String> commonIntervalSet = new CommonIntervalSet();
        String label1 = "1";
        String label2 = "2";
        try {
            commonIntervalSet.insert(1,2,label1);
            commonIntervalSet.insert(2,3,label2);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        }
        Set<String> labelsSet = new HashSet<>();
        labelsSet.add(label1);
        commonIntervalSet.remove(label2);
        assert  commonIntervalSet.labels().equals(labelsSet);
    }

    @Test
    public void start() {

        CommonIntervalSet<String> commonIntervalSet = new CommonIntervalSet();
        String label1 = "1";
        String label2 = "2";
        try {
            commonIntervalSet.insert(1,2,label1);
            commonIntervalSet.insert(2,3,label2);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        }
        Set<String> labelsSet = new HashSet<>();
        labelsSet.add(label1);
        commonIntervalSet.remove(label2);
        assert  commonIntervalSet.start(label1) == 1;
    }

    @Test
    public void end() {
        CommonIntervalSet<String> commonIntervalSet = new CommonIntervalSet();
        String label1 = "1";
        String label2 = "2";
        try {
            commonIntervalSet.insert(1,2,label1);
            commonIntervalSet.insert(2,3,label2);
        } catch (IntervalConflictException e) {
            e.printStackTrace();
        }
        Set<String> labelsSet = new HashSet<>();
        labelsSet.add(label1);
        commonIntervalSet.remove(label2);
        assert  commonIntervalSet.end(label1) == 2;
    }
}