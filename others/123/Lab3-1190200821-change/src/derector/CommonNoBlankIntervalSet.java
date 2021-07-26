package derector;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import interval.IntervalSet;
import interval.MultiIntervalSet;
import interval.Pair;

public class CommonNoBlankIntervalSet<L> extends MultiIntervalSetDecorator<L> implements NoBlankIntervalSet<L>{
	
	private final long startTime,endTime;
	
	//constructor
	public CommonNoBlankIntervalSet(long startTime, long endTime, IntervalSet<L> intervalSet) {
		super(MultiIntervalSet.init(intervalSet));
		this.startTime = startTime;
		this.endTime = endTime;
		checkRep(); 
	}
	//checkRep
	private void checkRep() {
		assert this.endTime > this.startTime;
	}
	
	@Override
	public Set<Pair<Long, Long>> blankSet() {
		checkRep();
		Set<Pair<Long, Long>> blankSet = new HashSet<>();
		blankSet.add(new Pair<>(this.startTime, this.endTime));
		
		for(L label: this.labels()) {
			for(int i =0;i<this.intervals​(label).labels().size();i++) {
			Pair<Long, Long> nowPair = new Pair<>(this.intervals​(label).start(i), 
					this.intervals​(label).end(i));
			
			Set<Pair<Long, Long>> newSet = new HashSet<>(blankSet);
			for(Pair<Long, Long> blankPair: newSet) {
				if(nowPair.getKey()>=blankPair.getValue()||
						blankPair.getKey()>=nowPair.getValue());
				else {
					blankSet.remove(blankPair);
					
					if(nowPair.getKey() > blankPair.getKey()) {
						blankSet.add(new Pair<>(blankPair.getKey(),
								nowPair.getKey()));
					}
					
					if(nowPair.getValue() < blankPair.getValue()) {
						blankSet.add(new Pair<>(nowPair.getValue(),
								blankPair.getValue()));
					}
				}
			}
		}
		}
		checkRep();
		return blankSet;
	}
	/**
	 * 重写的toString方法
	 */
	@Override
	public String toString() {
		return "[startTime="+new Date(this.startTime)+
				", endTime="+new Date(this.endTime)+
				", intervalSet="+this.multiIntervalSet+"]";
	}
}