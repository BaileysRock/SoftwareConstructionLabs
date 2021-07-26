package intervalset;

public interface NoBlankIntervalSet<L> {
    /**
     * 检查是否存在空白时间
     * @param start 起始时间
     * @param end 结束时间
     * @return 无重叠返回true，否则返回false
     */
    public boolean checkNoBlank(long start, long end);
}
