package com.ss.android.learn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ss.android.annotation.MyAnnotation
import com.ss.android.learn.contentprovider.MyContentProvider

@MyAnnotation
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MyContentProvider()
    }
}
