package derector;

import java.util.NoSuchElementException;
import java.util.Set;

import interval.IntervalConflictException;
import interval.IntervalSet;
/**
 * IntervalSet装饰器
 * @author liyu
 *
 * @param <L>
 */
public abstract class IntervalSetDerector<L> implements IntervalSet<L> {
	
	protected final IntervalSet<L> intervalSet;
	
	//constructor
	public IntervalSetDerector(IntervalSet<L> intervalSet) {
		this.intervalSet = intervalSet;
	}
	//delegation方法对功能进行实现
	@Override
	public void insert(long start, long end, L label) throws IntervalConflictException {
		// TODO Auto-generated method stub
		intervalSet.insert(start, end, label);
	}
	
	@Override
	public Set<L> labels() {
		// TODO Auto-generated method stub
		return intervalSet.labels();
	}
	
    @Override
 	public boolean remove(L label) {
		// TODO Auto-generated method stub
		return intervalSet.remove(label);
	}

	@Override
	public long start(L label) throws NoSuchElementException {
		// TODO Auto-generated method stub
		return intervalSet.start(label);
	}
	
	@Override
	public long end(L label) throws NoSuchElementException {
		// TODO Auto-generated method stub
		return intervalSet.end(label);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getClass().getName()+"[intervalSet="+this.intervalSet+"]";
	}
}