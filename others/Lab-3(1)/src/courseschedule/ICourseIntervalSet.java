package courseschedule;

public interface ICourseIntervalSet {
    /**
     * 在当前对象中插入新的时间段和标签
     *
     * @param start 开始时间
     * @param end   结束时间
     * @param label 标签
     */
    public void insert(long start, long end, Course label);
}
