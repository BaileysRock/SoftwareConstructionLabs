package courseschedule;

/**
 * 课程 Course 是一个 immutable 的 ADT，
 * <p>
 * 课程的信息包括课程 ID、课程名称、教师名字、上课地点、周学时数
 */
public class Course {
    // Abstraction function:
    //   AF() = 一门课程，信息包括：
    //   课程 ID、课程名称、教师名字、上课地点、周学时数
    // Representation invariant:
    //   课程 ID 非空，
    //   课程名称非空，
    //   教师名字非空，
    //   上课地点非空，
    //   周学时数为正整数
    // Safety from rep exposure:
    //   字段使用 private 和 final 修饰

    /**
     * 课程 ID
     */
    private final String id;

    /**
     * 课程名称
     */
    private final String name;

    /**
     * 教师名字
     */
    private final String teacher;

    /**
     * 上课地点
     */
    private final String place;

    /**
     * 周学时数
     */
    private final int period;

    /**
     * 初始化课程信息
     *
     * @param id      课程 ID
     * @param name    课程名称
     * @param teacher 教师名字
     * @param place   上课地点
     * @param period  周学时数
     */
    public Course(final String id,
                  final String name,
                  final String teacher,
                  final String place,
                  final int period) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.place = place;
        this.period = period;
    }

    /**
     * 检查 RI 是否为 true
     * <p>
     * 课程 ID 非空，
     * 课程名称非空，
     * 教师名字非空，
     * 上课地点非空，
     * 周学时数为正整数
     */
    private void checkRep() {
        assert id != null;
        assert name != null;
        assert teacher != null;
        assert place != null;
        assert period > 0;
    }

    /**
     * @return 获取课程 ID
     */
    public String getId() {
        return id;
    }

    /**
     * @return 获取课程名称
     */
    public String getName() {
        return name;
    }

    /**
     * @return 获取教师名字
     */
    public String getTeacher() {
        return teacher;
    }

    /**
     * @return 获取上课地点
     */
    public String getPlace() {
        return place;
    }

    /**
     * @return 获取周学时数
     */
    public int getPeriod() {
        return period;
    }

    @Override
    public String toString() {
        return "课程 ID：" + id + "\n" +
                "课程名称：" + name + "\n" +
                "教师名字：" + teacher + "\n" +
                "上课地点：" + place + "\n" +
                "周学时数：" + period;
    }
}
