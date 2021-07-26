package intervalset;



public class Interval {


    // Abstraction function:
    // af(start,end)一个从start到end的时间段
    // Representation invariant:
    // start > 0 && end > 0 && start < end
    // Safety from rep exposure:
    // 使用private、final修饰变量


    private final long start;

    private final long end;

    private final boolean periodicity;

    /**
     * 构造函数
     * @param start 时间段的起始时间
     * @param end 时间段的结束时间
     * @param periodicity 时间段是否是周期性的
     */
    public Interval(long start, long end, boolean periodicity) {
        this.start = start;
        this.end = end;
        this.periodicity = periodicity;
        checkRep();
    }

    public Interval(long start, long end) {
        this.start = start;
        this.end = end;
        this.periodicity = false;
        checkRep();
    }


    /**
     * 获得时间段的起始时间
     * @return 时间段的起始时间
     */
    public long getStart() {
        return this.start;
    }


    /**
     * 获得时间段的结束时间
     * @return 时间段的结束时间
     */
    public long getEnd() {
        return this.end;
    }


    /**
     * 获得时间段是否是周期性
     * @return 时间段是否是周期性的
     */
    public boolean getperiodicity() {
        return this.periodicity;
    }


    /**
     * 检查不变量，即检查RI是否为True
     * start>0 && this.end>start
     */
    private void checkRep() {
        assert this.start >= 0;
        assert this.end > this.start;
    }


}
