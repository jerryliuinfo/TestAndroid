package com.apache.android.bean;

/**
 * Created by 01370340 on 2018/5/6.
 */

public enum  Operation {
    PLUS("+"){
        @Override
        double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-"){
        @Override
        double apply(double x, double y) {
            return x- y;
        }
    },
    TIMES("*"){
        @Override
        double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/"){
        @Override
        double apply(double x, double y) {
            return  x / y;
        }
    };

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

   /* @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"symbol\":\"").append(symbol).append('\"');
        sb.append('}');
        return sb.toString();
    }*/
   //hehe


    //test1


    abstract double apply(double x, double y);


    public static void main(String[] args) {
        double x = 3.2;
        double y = 2.5;
        for (Operation operation : Operation.values()) {
            System.out.println(String.format("%s, %s, %s = %s",x, operation,y,operation.apply(x,y)));
        }
    }
}
