package com.ss.android.learn.activity;

import android.os.Bundle;

import com.ss.android.annotation.MyAnnotation;
import com.ss.android.learn.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.ss.android.learn.contentprovider.MyContentProvider;


@MyAnnotation
public class MainActivity1 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MyContentProvider();
    }
}
