package Sets;

import derector.CommonNoBlankIntervalSet;
import derector.NoBlankIntervalSet;
import derector.NoOverlapIntervalSet;
import interval.IntervalSet;

/**
 * ��װ����Ϊֵ�����ϲ�չ��ܺͲ��ص�����
 * @author liyu
 *
 * @param <L>
 */
public class DutyIntervalSet<L> extends CommonNoBlankIntervalSet<L> implements NoBlankIntervalSet<L>{
	
	public DutyIntervalSet(long startTime, long endTime) {
		super(startTime, endTime, new NoOverlapIntervalSet<>(IntervalSet.empty()));
	}
}
