package derector;

import java.util.Set;

import interval.Pair;



public interface NoBlankIntervalSet<L> {
	/**
	 * 
	 * @return 空闲时间集（如果不存在则返回空）
	 */
	public Set<Pair<Long, Long>> blankSet();
}