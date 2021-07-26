package courseschedule;

/**
 * 星期
 */
public enum Week {
    MON,    // 星期一
    TUE,    // 星期二
    WED,    // 星期三
    THU,    // 星期四
    FRI,    // 星期五
    SAT,    // 星期六
    SUN;    // 星期天

    /**
     * @return 转换星期为 int 型
     */
    int convert() {
        switch (this) {
            case MON:
                return 1;
            case TUE:
                return 2;
            case WED:
                return 3;
            case THU:
                return 4;
            case FRI:
                return 5;
            case SAT:
                return 6;
            case SUN:
                return 7;
            default:
                return 0;
        }
    }
}
