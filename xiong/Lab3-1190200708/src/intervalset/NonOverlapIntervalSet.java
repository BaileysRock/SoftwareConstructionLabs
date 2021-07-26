package intervalset;

public interface NonOverlapIntervalSet<L> {

    /**
     * 在所有时间段中，检查没有重叠时间段
     *
     * @return 无重叠则返回 true，否则返回 false
     */
    public boolean checkNoOverlap();

    /**
     * 检查 [start, end) 与现有的时间段没有重叠
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 无重叠则返回 true，否则返回 false
     */
    public boolean checkNoOverlap(long start, long end);

}
