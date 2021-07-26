package derector;

import java.util.NoSuchElementException;
import java.util.Set;

import interval.IntervalConflictException;
import interval.IntervalSet;
import interval.MultiIntervalSet;
/**
 * MultiSet的装饰器
 * @author liyu
 *
 * @param <L>
 */
public abstract class MultiIntervalSetDecorator<L> implements MultiIntervalSet<L> {
	
	protected final MultiIntervalSet<L> multiIntervalSet;
	//constructor
	public MultiIntervalSetDecorator(MultiIntervalSet<L> multiIntervalSet) {
		this.multiIntervalSet = multiIntervalSet;
	}
	//delegation方法
	@Override
	public void insert​(long start, long end, L label) throws IntervalConflictException {
		this.multiIntervalSet.insert​(start, end, label);
	}
	
	@Override
	public boolean remove(L label) {
		return this.multiIntervalSet.remove(label);
	}
	
	@Override
	public Set<L> labels() {
		return this.multiIntervalSet.labels();
	}
	
	@Override
	public IntervalSet<Integer> intervals​(L label) throws NoSuchElementException {
		return this.multiIntervalSet.intervals​(label);
	}
	
	@Override
	public String toString() {
		return getClass().getName()+"[multiIntervalSet="+this.multiIntervalSet+"]";
	}
	
}