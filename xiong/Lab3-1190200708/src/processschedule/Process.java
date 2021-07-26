package processschedule;

public class Process {


    // Abstraction function:
    // 由pid、name、minTime、maxTime映射的进程
    // 包含进程的pid、name、minTime、maxTime
    // Representation invariant:
    // 0<minTime<maxTime
    // pid >= 0
    // name != null
    // Safety from rep exposure:
    // 使用private、final修饰变量
    // 采用防御性复制


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
     * 检查不变量
     */
    private void checkRep() {
        assert minTime < maxTime;
    }


    /**
     * 构造函数
     *
     * @param pid     进程号
     * @param name    进程名字
     * @param minTime 最短执行时间
     * @param maxTime 最长执行时间
     */
    public Process(final long pid, final String name, final long minTime, final long maxTime) {
        this.pid = pid;
        this.name = name;
        this.minTime = minTime;
        this.maxTime = maxTime;
        checkRep();
    }


    /**
     * 获取进程号
     *
     * @return 返回进程号
     */
    public long getPid() {
        return pid;
    }

    /**
     * 获取进程名称
     *
     * @return 返回进程名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取进程的最短执行时间
     *
     * @return 返回进程最短执行时间
     */
    public long getMinTime() {
        return minTime;
    }

    /**
     * 获取最长执行时间
     *
     * @return 返回进程最长执行时间
     */
    public long getMaxTime() {
        return maxTime;
    }


}
