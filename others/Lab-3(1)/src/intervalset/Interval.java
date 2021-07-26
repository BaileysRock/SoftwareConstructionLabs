package intervalset;

/**
 * 时间段，immutable 类型
 */
public class Interval {
    // Abstraction function:
    //   AF(start, end) = 一个从 start 开始，到 end 结束的时间段
    // Representation invariant:
    //   start > 0, end > start
    // Safety from rep exposure:
    //   字段使用 private 和 final 修饰

    /**
     * 开始时间
     */
    private final long start;

    /**
     * 结束时间
     */
    private final long end;

    /**
     * 是否周期性重复
     */
    private final boolean periodic;

    public Interval(long start, long end, boolean periodic) {
        this.start = start;
        this.end = end;
        this.periodic = periodic;

        checkRep();
    }

    /**
     * @return 返回时间段开始时间
     */
    public long getStart() {
        return start;
    }

    /**
     * @return 返回时间段结束时间
     */
    public long getEnd() {
        return end;
    }

    /**
     * @return 返回时间段是否周期性重复
     */
    public boolean isPeriodic() {
        return periodic;
    }

    @Override
    public String toString() {
        return "[" + start + ", " + end + "], periodic = " + periodic;
    }

    /**
     * 检查 RI 是否为 true
     * <p>
     * start > 0, end > start
     */
    private void checkRep() {
        assert start > 0;
        assert end > start;
    }
}
