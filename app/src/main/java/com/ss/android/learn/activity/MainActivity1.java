package com.ss.android.learn;

import android.os.Bundle;

import com.ss.android.annotation.MyAnnotation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


@MyAnnotation
public class MainActivity1 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
