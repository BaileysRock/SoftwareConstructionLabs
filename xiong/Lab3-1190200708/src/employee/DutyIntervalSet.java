package employee;

import intervalset.*;
import intervalset.exception.*;

import java.util.*;

public class DutyIntervalSet<L> extends CommonIntervalSet<L> implements IDutyIntervalSet<L> {
    // Abstraction function:
    // 由nois、nbis、npis、start、end映射
    // 排班系统：没有空闲时间、没有重叠时间、无周期性
    // Representation invariant:
    // 没有空闲时间、没有重叠时间、无周期性
    // Safety from rep exposure:
    // 使用private、final修饰变量
    // 采用防御性复制

    // 无重叠时间委托
    private final NonOverlapIntervalSetImpl<L> nois;
    // 无空闲时间段委托
    private final NoBlankIntervalSetImpl<L> nbis;
    // 无周期时间段委托
    private final NonPeriodicIntervalSetImpl<L> npis;
    // 排班起始时间
    private final long start;
    // 排班结束时间
    private final long end;
//    // Employee
//    private final Set<Employee> employeeSet;
//    // Employee
//    private final Map<Employee, Interval> employeeIntervalMap;

    public DutyIntervalSet(long start, long end) {
        //设置delegation关系
        this.IntervalMap = new HashMap<>();
        this.nbis = new NoBlankIntervalSetImpl<>(this.IntervalMap);
        this.npis = new NonPeriodicIntervalSetImpl<>(this.IntervalMap);
        this.nois = new NonOverlapIntervalSetImpl<>(this.IntervalMap);
        this.start = start;
        this.end = end;
//        employeeSet = new HashSet<>();
//        employeeIntervalMap = new HashMap<>();
        //checkRep();
    }

    /**
     * 检查不变量
     */
    public void checkRep() {
        assert checkNoOverlap();
        assert checkNoBlank(this.start, this.end);
        assert checkNoPeriodic();
    }


//    public boolean deleteEmployee(Employee employee) {
//        if (employeeSet.remove(employee))
//            return true;
//        return false;
//    }
//
//
//    public boolean addEmployee(Employee employee) {
//        if (employeeSet.add(employee))
//            return true;
//        return false;
//    }
//
//
//    public boolean distribute(Employee employee, Interval interval) {
//        if (employeeIntervalMap.get(employee).getStart() == interval.getStart() ||
//                employeeIntervalMap.get(employee).getEnd() == interval.getEnd() ||
//                employeeSet.contains(employee))
//            return false;
//
//        employeeIntervalMap.put(employee, interval);
//        return true;
//    }


    /**
     * 检查当前日期的进位后的时间
     *
     * @param date
     * @return
     */
    public long dateCarryOver(long date) {
        long year = date / 10000;
        long month = (date - date / 10000 * 10000) / 100;
        long day = date - (date / 100) * 100;
        if (year % 100 == 0 && year % 400 != 0 && month == 2 && day == 28) {
            month = month + 1;
            day = 1;
        } else if (year % 4 == 0 && month == 2 && day == 29) {
            month = month + 1;
            day = 1;
        } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            if (day == 31) {
                month = month + 1;
                day = 1;
            }
        } else {
            if (day == 30) {
                month = month + 1;
                day = 1;
            }
        }
        if (month > 12) {
            month = month % 12;
            year = year + 1;
        }
        if (year * 10000 + month * 100 + day != date) {
            return year * 10000 + month * 100 + day;
        }
        return date + 1;
    }

    /**
     * 显示空闲时间
     */
    public void showFreeTime() {
        long carryOver;
        int countAll = 0;
        int countFree = 0;
        List<Long> timeList = new ArrayList<>();
        List<Long> freeTimeList = new ArrayList<>();
        for (Map.Entry<L, Interval> entry : this.IntervalMap.entrySet()) {
            for (long i = entry.getValue().getStart(); i < entry.getValue().getEnd(); i += 24 * 60 * 60 * 1000) {
                timeList.add(i);
            }
        }
        for (long i = start; i < end; i += 24 * 60 * 60 * 1000) {
            countAll++;
            if (!timeList.contains(i)) {
                countFree++;
                freeTimeList.add(i);
            }
        }

        System.out.println("当前未安排值班的时间为：");
        for (Long freetime : freeTimeList) {
            System.out.println(new Date(freetime));
        }
        double freeRatio = (double) countFree / (double) countAll;
        System.out.println("当前未安排时间的比例为" + freeRatio);
    }

    /**
     * 显示当前排班表
     */
    public void showTimeTable() {
        Set<Interval> IntervalSet = new HashSet<>();

        if (IntervalMap.size() == 0) {
            System.out.println("当前尚未排班");
            return;
        }

        long carryOver;
        System.out.println("日期\t\t\t\t\t\t值班人姓名\t职位\t手机号");
        for (long i = this.start; i < this.end; i += 24 * 60 * 60 * 1000) {
            for (Map.Entry<L, Interval> entry : IntervalMap.entrySet()) {
                if (entry.getValue().getStart() == i) {
                    for (long j = i; j < entry.getValue().getEnd(); j += 24 * 60 * 60 * 1000) {

                        System.out.println((new Date(j)).toString() + "\t" + entry.getKey().toString());
                        carryOver = dateCarryOver(j);
                        if (j + 1 != carryOver)
                            j = carryOver - 1;
                    }
                }
            }
            carryOver = dateCarryOver(i);
            if (i + 1 != carryOver)
                i = carryOver - 1;

        }


    }

    @Override
    public boolean insert(long start, long end, L label, boolean finish) throws IntervalConflictException, IntervalBlankException, IntervalPeriodicException {
        if (!checkNoOverlap(start, end)) {
            throw new IntervalConflictException("Time is overlapping!");
        }
        if (start < this.start || end > this.end) {
            return false;
        }
        this.IntervalMap.put(label, new Interval(start, end, false));
        if (!checkNoOverlap()) {
            throw new IntervalConflictException("Time is overlapping!");
        } else if (!checkNoPeriodic()) {
            throw new IntervalPeriodicException("Existential periodicity!");
        } else if (finish && !checkNoBlank(this.start, this.end)) {
            throw new IntervalBlankException("Existence of free time!");
        }
        return true;
    }

    @Override
    public boolean checkNoBlank(long start, long end) {
        return this.nbis.checkNoBlank(start, end);
    }

    @Override
    public boolean checkNoOverlap() {
        return this.nois.checkNoOverlap();
    }

    @Override
    public boolean checkNoOverlap(long start, long end) {
        return this.nois.checkNoOverlap(start, end);
    }

    @Override
    public boolean checkNoPeriodic() {
        return this.npis.checkNoPeriodic();
    }

}
