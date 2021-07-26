package courseschedule;

/**
 * 可用上课时间
 */
public enum CourseTime {
    MORNING_12,     // 上午 1-2  节， 8-10 时
    MORNING_34,     // 上午 3-4  节，10-12 时
    AFTERNOON_56,   // 下午 5-6  节，13-15 时
    AFTERNOON_78,   // 下午 7-8  节，15-17 时
    NIGHT_910;      // 晚上 9-10 节，19-21 时

    /**
     * @return 获取上课时间的开始时间（单位为小时）
     */
    public int getStart() {
        switch (this) {
            case MORNING_12:
                return 8;
            case MORNING_34:
                return 10;
            case AFTERNOON_56:
                return 13;
            case AFTERNOON_78:
                return 15;
            case NIGHT_910:
                return 19;
            default:
                return 0;
        }
    }

    /**
     * @return 获取上课时间的结束时间（单位为小时）
     */
    public int getEnd() {
        switch (this) {
            case MORNING_12:
                return 10;
            case MORNING_34:
                return 12;
            case AFTERNOON_56:
                return 15;
            case AFTERNOON_78:
                return 17;
            case NIGHT_910:
                return 21;
            default:
                return 0;
        }
    }
}
