package derector;

import java.util.Set;

import interval.Pair;



public interface NoBlankIntervalSet<L> {
	/**
	 * 
	 * @return ����ʱ�伯������������򷵻ؿգ�
	 */
	public Set<Pair<Long, Long>> blankSet();
}