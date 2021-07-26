package processschedule;

import API.APIs;
import processschedule.Process;
import intervalset.*;
import intervalset.exception.IntervalBlankException;
import intervalset.exception.IntervalConflictException;
import intervalset.exception.IntervalPeriodicException;

import java.util.ArrayList;
import java.util.HashMap;

public class ProcessIntervalSet<Process> extends CommonMultiIntervalSet<Process> implements IProcessIntervalSet<Process> {


    // Abstraction function:
    // 由nois、npis的映射
    // 排班系统：没有重叠时间、无周期性
    // Representation invariant:
    // 没有重叠时间、无周期性
    // Safety from rep exposure:
    // 使用private、final修饰变量
    // 采用防御性复制


    /**
     * 无重叠时间
     */
    private final NonOverlapMultiIntervalSetImpl<Process> nois;

    /**
     * 无周期时间段委托
     */
    private final NonPeriodicMultiIntervalSetImpl<Process> npis;




    /**
     * 初始化进程调度器
     */
    public ProcessIntervalSet()
    {
        this.nois = new NonOverlapMultiIntervalSetImpl<>(this.MultiIntervalMap);
        this.npis = new NonPeriodicMultiIntervalSetImpl<>(this.MultiIntervalMap);

        checkRep();

    }




    /**
     * 求程序当前执行的时间
     * @param process 待求的进程
     * @return 执行的时间
     */
    public long lastTimeProcess(Process process)
    {
        long lastTime = 0;
        if(MultiIntervalMap.size()==0)
            return 0;
        if(!MultiIntervalMap.containsKey(process))
            return 0;
        if(MultiIntervalMap.get(process).size()==0)
            return 0;
        for(Interval interval:MultiIntervalMap.get(process))
        {
            lastTime += interval.getEnd()-interval.getStart();
        }
        return lastTime;
    }



    @Override
    public boolean insert(long start, long end, Process label, boolean finish)  throws IntervalConflictException, IntervalBlankException, IntervalPeriodicException
    {
        if (!checkNoOverlap(start, end)) {
            throw new IntervalConflictException("Time is overlapping!");
        }
        if (MultiIntervalMap.containsKey(label)) {
            MultiIntervalMap.get(label).add(new Interval(start, end, false));
        } else {
            ArrayList<Interval> arrayList = new ArrayList<>();
            arrayList.add(new Interval(start, end, false));
            this.MultiIntervalMap.put(label, arrayList);
        }
        if (!checkNoOverlap()) {
            throw new IntervalConflictException("Time is overlapping!");
        } else if (!checkNoPeriodic()) {
            throw new IntervalPeriodicException("Existential periodicity!");
        }
        return true;
    }


    @Override
    public boolean checkNoOverlap() {
        return this.nois.checkNoOverlap();
    }

    @Override
    public boolean checkNoOverlap(long start, long end) {
        return this.nois.checkNoOverlap(start, end);
    }

    @Override
    public boolean checkNoPeriodic() {
        return this.npis.checkNoPeriodic();
    }
}
