package derector;

import java.util.HashMap;
import java.util.Map;

import interval.IntervalConflictException;
import interval.IntervalSet;
import interval.Pair;

/**
 * 
 * @author liyu
 *非重叠装饰器
 * @param <L> 
 */

public class NoOverlapIntervalSet<L> extends IntervalSetDerector<L> implements IntervalSet<L> {
	
	private final Map<L, Pair<Long, Long>> intervals = new HashMap<>();
	//constructor
	public NoOverlapIntervalSet(IntervalSet<L> intervalSet) {
		super(intervalSet);
		checkRep();
	}
	//checkRep
	private void checkRep() {
		for(L key: this.intervals.keySet()) {
			assert this.intervals.get(key).getKey() < this.intervals.get(key).getValue();
			for(L label: this.intervals.keySet()) {
				assert label.equals(key) || 
				this.intervals.get(key).getKey() >= this.intervals.get(label).getValue() || 
				this.intervals.get(label).getKey() >= this.intervals.get(key).getValue(); 
				
			}
		}
	}
	
	//重写insert方法
	@Override
	public void insert(long start, long end, L label) throws IntervalConflictException {
		// TODO Auto-generated method stub
		super.insert(start, end, label);
		for(L nowLabel: this.intervals.keySet()) {
			if(nowLabel.equals(label)) continue;
			Pair<Long, Long> intervalPair = this.intervals.get(nowLabel);
			if(start >= intervalPair.getValue() || 
					end <= intervalPair.getKey());
			else {
				super.remove(label);
				throw new IntervalConflictException("insert: Interval Conflict in Different Label");
			}
		}
		this.intervals.put(label, new Pair<>(start, end));
		checkRep();
	}
	//重写toString方法
	@Override
	public String toString() {
		return "[intervals="+this.intervals+
				", intervalSet="+this.intervalSet.toString()+"]";
	}
}