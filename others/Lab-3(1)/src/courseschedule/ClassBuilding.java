package courseschedule;

/**
 * 教学楼
 */
public enum ClassBuilding {
    ZHENG_XIN,      // 正心楼
    CHENG_YI,       // 诚意楼
    ZHI_ZHI,        // 致知楼
    GE_WU;          // 格物楼

    @Override
    public String toString() {
        switch (this) {
            case ZHENG_XIN:
                return "正心楼";
            case CHENG_YI:
                return "诚意楼";
            case ZHI_ZHI:
                return "致知楼";
            case GE_WU:
                return "格物楼";
            default:
                return "";
        }
    }
}
