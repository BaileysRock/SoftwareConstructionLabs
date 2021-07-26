package derector;

import interval.IntervalConflictException;

import interval.IntervalSet;
/**
 * 周期性装饰器
 * @author liyu
 *
 * @param <L>
 */

public class PeriodicIntervalSet<L> extends IntervalSetDerector<L> implements IntervalSet<L> {
	
	private final long period;
	
	public PeriodicIntervalSet(long period, IntervalSet<L> intervalSet) {
		super(intervalSet);
		this.period = period;
		checkRep();
	}
	
	private void checkRep() {
		assert this.period > 0;
	}
   /**
    * 重写insert方法
    */
	@Override
		public void insert(long start, long end, L label) throws IntervalConflictException {
			// TODO Auto-generated method stub
			super.insert(start, end, label);
			long startMod = start %this.period, endMod = end % this.period;
			if(startMod < 0) startMod += this.period;
			if(endMod < 0) endMod += this.period;
			
			if(endMod < startMod) {
				throw new IntervalConflictException("insert: Across Periods");
			}
			else super.insert(startMod, endMod, label);
			checkRep();
		}

	/**
	 * 重写tostring方法
	 */
	@Override
	public String toString() {
		return "[period="+this.period+
				", intervalSet="+this.intervalSet+"]";
	}
}