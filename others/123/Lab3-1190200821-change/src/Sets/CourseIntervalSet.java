package Sets;

import interval.IntervalSet;
import interval.MultiIntervalSet;
import derector.PeriodicMultiIntervalSet;

/**
 * 用装配器为课程表加上周期性功能
 * @author liyu
 *
 * @param <L>
 */
public class CourseIntervalSet<L> extends PeriodicMultiIntervalSet<L> implements MultiIntervalSet<L> {
	
	public CourseIntervalSet(long period) {
		super(period, MultiIntervalSet.empty());
	}
	
	public CourseIntervalSet(long period, IntervalSet<L> initial) {
		super(period, MultiIntervalSet.init(initial));
	}
	
}