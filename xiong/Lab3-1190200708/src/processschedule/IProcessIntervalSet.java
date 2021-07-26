package processschedule;

import intervalset.NoBlankIntervalSet;
import intervalset.NonOverlapIntervalSet;
import intervalset.NonPeriodicIntervalSet;
import intervalset.exception.IntervalBlankException;
import intervalset.exception.IntervalConflictException;
import intervalset.exception.IntervalPeriodicException;

public interface IProcessIntervalSet <L> extends NonOverlapIntervalSet<L>, NonPeriodicIntervalSet<L> {



    public boolean insert(long start, long end, L label, boolean finish)  throws IntervalConflictException, IntervalBlankException, IntervalPeriodicException;




}
