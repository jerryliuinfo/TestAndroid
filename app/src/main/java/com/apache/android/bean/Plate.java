package com.apache.android.bean;

/**
 * Created by 01370340 on 2018/4/29.
 */

public class Plate<T> {
    private T item;

    public Plate(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }



    public static void main(String[] args) {

        Plate<? extends Fruit> extendsPlate = new Plate<>(new Apple());

        //extendsPlate.setItem(new Apple()); //error
        //extendsPlate.setItem(new Fruit()); //error

        //读出来的东西只能放在Fruit及它的基类里
        Fruit fruit = extendsPlate.getItem();
        Object obj = extendsPlate.getItem();
        //Apple apple = extendsPlate.getItem();//error


        Plate<? super Fruit> superPlate = new Plate<>(new Fruit());
        superPlate.setItem(new Fruit());
        superPlate.setItem(new Apple());

        //Apple superApple = superPlate.getItem(); //error
        //IFruit superFruit = superPlate.getItem(); //error
        Object superObject = superPlate.getItem();




    }




}
