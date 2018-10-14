package com.ss.android.learn.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ss.android.learn.R
import com.ss.android.learn.fragment.EditPhotoDetailFragment
import com.ss.android.learn.fragment.transaction
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var fragment = EditPhotoDetailFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.transaction {
            replace(R.id.frame, fragment)
        }
        initView()
    }

    private fun initView() {
        add.setOnClickListener {
            fragment.addTextView()
//            add.visibility = View.GONE
        }
        done.setOnClickListener {

        }
    }
}
