package com.apache.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;

import com.apache.android.util.NLog;

/**
 * Created by 01370340 on 2018/4/14.
 */

public class KnowledgeActivity extends AppCompatActivity{
    public static final String TAG = KnowledgeActivity.class.getSimpleName();
    public static void launch(Activity from) {
        Intent intent = new Intent(from, KnowledgeActivity.class);
        from.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_knowledge);
        final ImageView iv_clear_bg = (ImageView) findViewById(R.id.iv_clear_bg);
        iv_clear_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_clear_bg.setBackgroundColor(0);
            }
        });
        ImageView imageView = new ImageView(this);

        String time = DateUtils.formatDateTime(this,System.currentTimeMillis(),DateUtils.FORMAT_SHOW_TIME);
        String date =DateUtils.formatDateTime(this,System.currentTimeMillis(),DateUtils.FORMAT_SHOW_DATE);
        String week =DateUtils.formatDateTime(this,System.currentTimeMillis(),DateUtils.FORMAT_SHOW_WEEKDAY);

        String fileSize = Formatter.formatFileSize(this,(long)(1024* 1024 *7.6));
        NLog.d(TAG, "time = %s, date = %s, week = %s, fileSize = %s",time,date,week,fileSize);

    }
}
