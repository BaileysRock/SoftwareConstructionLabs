package Sets;

import derector.NoOverlapIntervalSet;
import interval.CommonMultiIntervalSet;
import interval.IntervalSet;
import interval.MultiIntervalSet;

/**
 * 用装配器为进程表加上不重叠功能
 * @author liyu
 *
 * @param <L>
 */
public class ProcessIntervalSet<L> extends CommonMultiIntervalSet<L> implements MultiIntervalSet<L> {
	
	public ProcessIntervalSet() {
		this(IntervalSet.empty());
	}
	
	public ProcessIntervalSet(IntervalSet<L> initial) {
		super(new NoOverlapIntervalSet<>(initial));
	}
	
}