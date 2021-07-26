package processschedule;

/**
 * 进程 Process 是一个 immutable 的 ADT
 * <p>
 * 进程的信息包括进程ID、进程名称、最短执行时间、最长执行时间
 */
public class Process {
    // Abstraction function:
    //   AF() = 一个进程，其信息包括 ID、名称、最短执行时间、最长执行时间
    // Representation invariant:
    //   ID 为正整数，名称非空，
    //   最短执行时间和最长执行时间为正整数，
    //   最长执行时间 > 最短执行时间
    // Safety from rep exposure:
    //   字段使用 private 和 final 修饰

    /**
     * 进程号
     */
    private final long pid;

    /**
     * 进程名称
     */
    private final String name;

    /**
     * 最短执行时间
     */
    private final long minTime;

    /**
     * 最长执行时间
     */
    private final long maxTime;

    /**
     * 进程状态
     */
    private String state;

    /**
     * 初始化进程信息
     *
     * @param pid     进程号
     * @param name    进程名称
     * @param minTime 最短执行时间
     * @param maxTime 最长执行时间
     * @param state   进程状态
     */
    public Process(final long pid,
                   final String name,
                   final long minTime,
                   final long maxTime,
                   final String state) {
        this.pid = pid;
        this.name = name;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.state = state;
    }

    /**
     * @return 获取进程号
     */
    public long getPid() {
        return pid;
    }

    /**
     * @return 获取进程名称
     */
    public String getName() {
        return name;
    }

    /**
     * @return 获取进程最短执行时间
     */
    public long getMinTime() {
        return minTime;
    }

    /**
     * @return 获取进程最长执行时间
     */
    public long getMaxTime() {
        return maxTime;
    }

    /**
     * @return 获取进程状态
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 修改进程状态
     */
    public void setState(String state) {
        this.state = state;
    }
}
