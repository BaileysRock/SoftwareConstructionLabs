package intervalset;

import intervalset.exceptions.IntervalConflictException;

import java.util.NoSuchElementException;
import java.util.Set;

/**
 * IntervalSet 委托
 */
public abstract class IntervalSetDelegation<L>
        implements IntervalSet<L> {

    /**
     * 委托对象
     */
    protected final IntervalSet<L> intervalSet;

    /**
     * 根据 intervalSet 初始化委托
     */
    public IntervalSetDelegation(IntervalSet<L> intervalSet) {
        this.intervalSet = intervalSet;
    }

    @Override
    public void insert(long start, long end, L label)
            throws IntervalConflictException {
        intervalSet.insert(start, end, label);
    }

    @Override
    public Set<L> labels() {
        return intervalSet.labels();
    }

    @Override
    public boolean remove(L label) {
        return intervalSet.remove(label);
    }

    @Override
    public long start(L label)
            throws NoSuchElementException {
        return intervalSet.start(label);
    }

    @Override
    public long end(L label)
            throws NoSuchElementException {
        return intervalSet.end(label);
    }
}
