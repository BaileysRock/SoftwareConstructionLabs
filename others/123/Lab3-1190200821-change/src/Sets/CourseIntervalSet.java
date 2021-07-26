package Sets;

import interval.IntervalSet;
import interval.MultiIntervalSet;
import derector.PeriodicMultiIntervalSet;

/**
 * ��װ����Ϊ�γ̱���������Թ���
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