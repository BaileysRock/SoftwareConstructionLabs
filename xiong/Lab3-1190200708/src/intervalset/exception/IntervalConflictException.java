package intervalset.exception;

/**
 * 时间段之间重叠抛出的异常
 */

public class IntervalConflictException extends Exception {


    public IntervalConflictException(String ExceptionMessage) {
        super(ExceptionMessage);
    }

}
