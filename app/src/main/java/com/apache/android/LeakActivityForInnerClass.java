package com.apache.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by 01370340 on 2018/2/2.
 */

public class LeakActivityForInnerClass extends AppCompatActivity{
    
    public static void launch(Activity from) {
        Intent intent = new Intent(from, LeakActivityForInnerClass.class);
        from.startActivity(intent);
    }
    
    
    private static Inner sInner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_leak_inner_class);

        TextView textView = (TextView) findViewById(R.id.tv_text);
        textView.setText("内部类泄漏");

        sInner = new Inner();
    }


     class Inner{

     }
}
