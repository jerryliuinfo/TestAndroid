package com.apache.android;

import android.os.Binder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_md5 = (Button) findViewById(R.id.btn_md5);
        btn_md5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LeakActivityForInnerClass.launch(MainActivity.this);
            }
        });



        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });
    }


    public static void main(String[] args) {
        //testThreadSync();
        testEqualsAndHashCode();
        testBubbleSort();

    }

    private static int [] array = new int[]{20,10,13,15,24};

    private static void testBubbleSort(){
        int length = array.length;
        int temp = 0;
        for (int i = 0; i < length; i++) {
            for (int j = i; j < length - i -1; j++){
                if (array[j] > array[j+1]){
                    temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }
        print();
    }

    private static void print(){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            buffer.append(array[i]).append("-");
        }
        System.out.println(buffer.toString());
    }


    private static void testEqualsAndHashCode(){
        Student s1 = new Student(1,"micro");
        Student s2 = new Student(1,"micro");
        Set<Student> set = new HashSet();
        set.add(s1);
        set.add(s2);
        System.out.println(set.size());
        System.out.println("s1 equals s2 = "+ s1.equals(s2));
    }

    private static void testThreadSync(){
        final Bank bank = new Bank();

        Thread tadd=new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while(true){
                    try {
                        Thread.sleep(new Random().nextInt(500));
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    bank.addMoney(100);
                    bank.lookMoney();
                    System.out.println("\n");

                }
            }
        });

        Thread tsub = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while(true){
                    bank.subMoney(100);
                    bank.lookMoney();
                    System.out.println("\n");
                    try {
                        Thread.sleep(new Random().nextInt(400));
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        tsub.start();

        tadd.start();
        Set set;
        ArrayList arrayList;
        LinkedList linkedList;
        Binder binder;
    }
}
