package derector;

import interval.IntervalConflictException;
import interval.MultiIntervalSet;

/**
 * 同PeriodicIntervalSet一样，周期性功能装饰器
 * @author liyu
 *
 * @param <L>
 */
public class PeriodicMultiIntervalSet<L> extends MultiIntervalSetDecorator<L> implements MultiIntervalSet<L> {
	
	private final long period;
	
	//constructor
	public PeriodicMultiIntervalSet(long period, MultiIntervalSet<L> multiIntervalSet) {
		super(multiIntervalSet);
		this.period = period;
		checkRep();
	}
	//checkRep
	private void checkRep() {
		assert this.period > 0;
	}
	@Override
	public void insert​(long start, long end, L label) throws IntervalConflictException  {
		checkRep();
		long startMod = start %this.period, endMod = end % this.period;
		
		if(endMod < startMod) {
			super.insert​(0, endMod, label);
			super.insert​(startMod, this.period, label);
		}
		else super.insert​(startMod, endMod, label);
		checkRep();
	}
	
	@Override
	public String toString() {
		checkRep();
		return "[period="+this.period+
				", multiIntervalSet="+this.multiIntervalSet+"]";
	}
}