package com.apache.android.bean;

/**
 * Created by 01370340 on 2018/4/30.
 */

public class Complex {
    private final double re;
    private final double im;


    private Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public static Complex valueOf(double re, double im){
        return new Complex(re,im);
    }
}
