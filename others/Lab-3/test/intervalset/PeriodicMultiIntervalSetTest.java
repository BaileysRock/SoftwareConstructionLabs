package intervalset;

import intervalset.exceptions.IntervalConflictException;
import org.junit.Test;

public class PeriodicMultiIntervalSetTest {

    @Test
    public void insert() {
        PeriodicMultiIntervalSet<String> periodicMultiIntervalSet =
                new PeriodicMultiIntervalSet<>(
                        35,
                        MultiIntervalSet.empty()
                );

        try {
            periodicMultiIntervalSet.insert(0, 1, "周一一二节");
            periodicMultiIntervalSet.insert(11, 12, "周三三四节");
        } catch (IntervalConflictException e) {
            assert false;
        }
    }
}
