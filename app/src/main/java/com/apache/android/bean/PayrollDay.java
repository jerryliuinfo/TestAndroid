package com.apache.android.bean;

import java.util.EnumSet;

/**
 * Created by 01370340 on 2018/5/6.
 */

public enum  PayrollDay {
    MONDAY(PayType.WEEKDAY),
    TUESDAY(PayType.WEEKDAY),
    WEDNESDAY(PayType.WEEKDAY),
    THURSDAY(PayType.WEEKDAY),
    FRIDAY(PayType.WEEKDAY),
    SATURDAY(PayType.WEEKEND),
    SUNDAY(PayType.WEEKEND);

    private final PayType payType;

    PayrollDay(PayType payType) {
        this.payType = payType;

    }

    double pay(double hoursWorked, double payRater){
        System.out.println(ordinal());
        return payType.pay(hoursWorked,payRater);
    }

    private enum PayType{
        WEEKDAY{
            @Override
            double overTimePay(double hrs, double payRate) {
                return hrs <= HOURS_PER_SHIFT? 0: (hrs - HOURS_PER_SHIFT) * payRate / 2;
            }
        },
        WEEKEND{
            @Override
            double overTimePay(double hrs, double payRate) {
                return hrs * payRate / 2;
            }
        },
        SPECAIL{
            @Override
            double overTimePay(double hrs, double payRate) {
                return hrs * payRate;
            }
        }
        ;
        public static final int HOURS_PER_SHIFT = 8;



        abstract double overTimePay(double hrs, double payRate);

        double pay(double hoursWorked, double payRater){
            double basePay = hoursWorked * payRater;
            return basePay + overTimePay(hoursWorked,payRater);
        }
    }

    public static void main(String[] args) {
        PayrollDay day = PayrollDay.SUNDAY;
        System.out.println(day.pay(8,16f));
        EnumSet enumSet;

    }
}
