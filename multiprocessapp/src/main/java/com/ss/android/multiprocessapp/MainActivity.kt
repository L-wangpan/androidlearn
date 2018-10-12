package com.ss.android.multiprocessapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ContentValues
import android.net.Uri
import android.util.Log


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val uri = Uri.parse("content://com.example.liuwangshu.mooncontentprovide.GameProvider")
        val mContentValues = ContentValues()
        mContentValues.put("_id", 2)
        mContentValues.put("name", "大航海时代ol")
        mContentValues.put("describe", "最好玩的航海网游")
        contentResolver.insert(uri, mContentValues)
        val gameCursor = contentResolver.query(uri, arrayOf("name", "describe"), null, null, null)
        while (gameCursor!!.moveToNext()) {
            val mGame = Game(gameCursor.getString(0), gameCursor.getString(1))
            Log.i(TAG, mGame.gameName + "---" + mGame.gameDescribe)
        }
    }
}
