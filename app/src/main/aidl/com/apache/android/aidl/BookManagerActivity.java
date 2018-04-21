package com.apache.android.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.apache.android.R;
import com.apache.android.util.NLog;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 01370340 on 2018/4/21.
 */

public class BookManagerActivity extends AppCompatActivity {
    public static final String TAG = BookManagerActivity.class.getSimpleName();
    private Button btn_get_book;
    private Button btn_add_book;

    private IBookManager manager;

    private AtomicInteger mCount = new AtomicInteger(10);

    public static void launch(Activity from) {
        Intent intent = new Intent(from, BookManagerActivity.class);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        btn_get_book = (Button) findViewById(R.id.btn_get_book);
        btn_add_book = (Button) findViewById(R.id.btn_add_book);

        btn_get_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printBookList();
            }
        });
        btn_add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addBook();
            }
        });

        Intent intent = new Intent(this,BookManagerService.class);

        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            NLog.d(TAG, "onServiceConnected");

            manager = IBookManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            NLog.d(TAG, "onServiceDisconnected");
            manager = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }




    private void addBook(){
        try {
            if (manager == null){
                return;
            }
            Book book = new Book(mCount.getAndAdd(1), "Android进阶： "+mCount.get());
            manager.addBook(book);
            printBookList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void printBookList(){
        try {
            if (manager == null){
                return;
            }
            List<Book> list = manager.getBookList();
            NLog.d(TAG, "list = %s", list);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
