package Sets;

import derector.CommonNoBlankIntervalSet;
import derector.NoBlankIntervalSet;
import derector.NoOverlapIntervalSet;
import interval.IntervalSet;

/**
 * 用装配器为值班表加上查空功能和不重叠功能
 * @author liyu
 *
 * @param <L>
 */
public class DutyIntervalSet<L> extends CommonNoBlankIntervalSet<L> implements NoBlankIntervalSet<L>{
	
	public DutyIntervalSet(long startTime, long endTime) {
		super(startTime, endTime, new NoOverlapIntervalSet<>(IntervalSet.empty()));
	}
}
