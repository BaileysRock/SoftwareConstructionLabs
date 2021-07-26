package intervalset;

import intervalset.exceptions.IntervalConflictException;

import java.util.NoSuchElementException;
import java.util.Set;

/**
 * MultiIntervalSet 委托
 */
public abstract class MultiIntervalSetDelegation<L>
        implements MultiIntervalSet<L> {

    /**
     * 委托对象
     */
    protected final MultiIntervalSet<L> multiIntervalSet;

    /**
     * 根据 intervalSet 初始化委托
     */
    public MultiIntervalSetDelegation(MultiIntervalSet<L> multiIntervalSet) {
        this.multiIntervalSet = multiIntervalSet;
    }

    @Override
    public void insert(long start, long end, L label)
            throws IntervalConflictException {
        this.multiIntervalSet.insert(start, end, label);
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
    public IntervalSet<Integer> intervals(L label)
            throws NoSuchElementException {
        return this.multiIntervalSet.intervals(label);
    }
}
