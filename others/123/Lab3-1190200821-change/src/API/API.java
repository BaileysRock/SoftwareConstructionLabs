package API;

import java.util.HashSet;
import java.util.Set;

import interval.IntervalSet;
import interval.Intervals;
import interval.MultiIntervalSet;

public abstract class API {
    /**
     * 求交集（相似的时间段）
     * @param s1 第一个intervalset
     * @param s2 第二个intervalSet
     * @return s1,s2的交集
     */
	private static Set<Intervals> similarIntervals(IntervalSet<Integer> s1, IntervalSet<Integer> s2) {
		int n =Math.min(s1.labels().size(),s2.labels().size());
		int index = 0;
		Set<Intervals> newInterval = new HashSet<>();
		for(int label1= 0;label1< n ;label1++) {
			long start = s1.start(label1);
			long end = s1.end(label1);
			while(index<n&&s2.end(index)<=start) index++;
			while(index<n&&s2.end(index)<end)
			{
				newInterval.add(new Intervals(Math.max(start, s2.start(index)), s2.end(index)));
				index++;
			}
			if(index>=n) break;
			if(s2.start(index)<end) 
			{
				newInterval.add(new Intervals(Math.max(start, s2.start(index)), end));
			}
		}
		
		return newInterval;
	}
	/**
	 * 求时间段的长度
	 * @param s1 一个时间段集合
	 * @return 长度
	 */
	
	public static double intervalTolen(Set<Intervals> s1) {
		double res =0.0;
		for(Intervals intervals:s1) {
			res += intervals.getEnd() - intervals.getStart() ;
		}
		return res;
	}
	/**
	 * 重载intervalTolen
	 * @param <L>
	 * @param multiIntervalSet
	 * @return
	 */
	
	public static <L> double intervalTolen(MultiIntervalSet<L> multiIntervalSet) {
		return (double)getMax(multiIntervalSet) - (double)getMin(multiIntervalSet);
	}

	private static <L> Long getMin(MultiIntervalSet<L> multiIntervalSet) {
		// TODO Auto-generated method stub
		long min = Long.MAX_VALUE;
		for(L label:multiIntervalSet.labels()) {
			IntervalSet<Integer> interval = multiIntervalSet.intervals​(label);
			for(Integer i:interval.labels()) {
				if(interval.start(i)<min) min = interval.start(i);
			}
		}
		return min;
	}
    /**
     * 求时间段的最大时间
     * @param <L>
     * @param multiIntervalSet
     * @return
     */
	private static<L> Long getMax(MultiIntervalSet<L> multiIntervalSet) {
		// TODO Auto-generated method stub
		long max = Long.MIN_VALUE;
		for(L label:multiIntervalSet.labels()) {
			IntervalSet<Integer> interval = multiIntervalSet.intervals​(label);
			for(Integer i:interval.labels()) {
				if(interval.end(i)>max) max = interval.end(i);
			}
		}
		return max;
	}
	/**
	 * 求两个集合的相似比
	 * @param <L>
	 * @param s1 第一个集合
	 * @param s2 第二个集合
	 * @return s1,s2的相似比
	 */
	public static <L> double Similarity(MultiIntervalSet<L> s1, MultiIntervalSet<L> s2) {
		long min = Math.min(getMin(s1), getMin(s2));
		long max = Math.max(getMax(s1), getMax(s2));
		
		double res = 0.0;
		for(L label: s1.labels()) {
			if(s2.labels().contains(label)) {
				res += intervalTolen(similarIntervals(s1.intervals​(label), s2.intervals​(label)));
			}
		}
		
		res /= (double)(max - min);
		return res;
	}
	/**
	 * 冲突比例计算
	 * @param <L>
	 * @param set 一个可以重叠的mulitSet
	 * @return 冲突占比
	 */
	
	public static <L> double calcConflictRatio(MultiIntervalSet<L> set) {
		Set<Intervals> conflictIntervals = new HashSet<Intervals>();
		for(L label1: set.labels()) {
			for(L label2: set.labels()) {
				if(label1.equals(label2)) continue;
			    Set<Intervals> intervals = similarIntervals(set.intervals​(label1), set.intervals​(label2));
			    for(Intervals conflict:conflictIntervals) {
			    	Set<Intervals> copySet = new HashSet<>(intervals);
			    	for(Intervals newIntervals:copySet) {
			    		if(conflict.getStart()>=newIntervals.getEnd()||conflict.getEnd()<=newIntervals.getStart());
			    		else {
			    		   intervals.remove(newIntervals);
			    		   if(newIntervals.getStart()<conflict.getStart()) {
			    			   intervals.add(new Intervals(newIntervals.getStart(),conflict.getStart()));
			    		   }
			    		   if(newIntervals.getEnd()>conflict.getEnd()) {
			    			   intervals.add(new Intervals(conflict.getEnd(), newIntervals.getEnd()));
			    		   }
			    		}
			    	}
			    }
			    conflictIntervals.addAll(intervals);
			}
		}
			return intervalTolen(conflictIntervals)/intervalTolen(set);
	}
	/**
	 * 利用重载进行复用
	 * @param <L>
	 * @param set
	 * @return
	 */
	public static <L> double calcConflictRatio(IntervalSet<L> set) {
		return calcConflictRatio(MultiIntervalSet.init(set));
	}
	/**
	 * 求空闲时间段的比例
	 * @param <L>
	 * @param set 
	 * @return 集合中空闲时间占比
	 */
   
	public static <L> double calcFreeTimeRatio(MultiIntervalSet<L> set) {
		long min = getMin(set);
		long max = getMax(set);
		
		double res = 0.0;
		Set<Intervals> freeIntervals = new HashSet<Intervals>();
		freeIntervals.add(new Intervals(min, max));
		for(L label:set.labels()) {
			IntervalSet<Integer> intervalSet = set.intervals​(label);
			for(Integer i: intervalSet.labels()) {
				long start = intervalSet.start(i);
				long end = intervalSet.end(i);
				Set<Intervals> copyIntervals = new HashSet<Intervals>(freeIntervals);
				for(Intervals free:copyIntervals) {
					if(free.getStart()>= end||free.getEnd()<=start) continue;
					else {
						freeIntervals.remove(free);
						if(free.getEnd()>end) freeIntervals.add(new Intervals(end, free.getEnd()));
						if(free.getStart()<start) freeIntervals.add(new Intervals(free.getStart(), start));
						
					}
				}
			}
		  
		}
		 res=intervalTolen(freeIntervals)/intervalTolen(set);
		 return res;
	}
	/**
	 * 重载进行复用
	 * @param <L>
	 * @param set
	 * @return
	 */
	public static <L> double calcFreeTimeRatio(IntervalSet<L> set) {
		return calcFreeTimeRatio(MultiIntervalSet.init(set));
	}
}

