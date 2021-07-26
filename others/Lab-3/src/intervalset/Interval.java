package intervalset;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 时间段，immutable 类型
 */
public class Interval {
    // Abstraction function:
    //   AF(start, end) = 一个从 start 开始，到 end 结束的时间段
    // Representation invariant:
    //   start >= 0, end >= start
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

    public Interval(long start, long end) {
        this.start = start;
        this.end = end;

        checkRep();
    }

    public Interval(String start, String end)
            throws ParseException {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd");
        this.start = simpleDateFormat.parse(start).getTime();
        this.end = simpleDateFormat.parse(end).getTime();

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
     * 检查 RI 是否为 true
     * <p>
     * start >= 0, end >= start
     */
    private void checkRep() {
        assert start >= 0;
        assert end >= start;
    }

    @Override
    public String toString() {
        return "[" + start + ", " + end + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == this.getClass()) {
            return this.hashCode() == obj.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) ((int) this.start * this.end);
    }
}
