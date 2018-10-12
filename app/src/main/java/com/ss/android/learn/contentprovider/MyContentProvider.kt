package com.ss.android.learn.contentprovider

import android.content.ContentProvider
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.database.sqlite.SQLiteDatabase
import android.content.UriMatcher
import android.content.ContentValues
import com.ss.android.learn.contentprovider.DbOpenHelper


class MyContentProvider : ContentProvider() {
    val AUTHORITY = "com.example.liuwangshu.mooncontentprovide.GameProvider"
    val GAME_CONTENT_URI = Uri.parse("content://$AUTHORITY/game")
    private val mUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private lateinit var mDb: SQLiteDatabase
    private var mContext: Context? = null
    private var table: String? = null

    init {
        mUriMatcher.addURI(AUTHORITY, "game", 0);
    }

    override fun onCreate(): Boolean {
        table = DbOpenHelper.GAME_TABLE_NAME
        mContext = context
        initProvider()
        return false
    }

    private fun initProvider() {
        mDb = DbOpenHelper(mContext!!).writableDatabase
        Thread(Runnable {
            mDb.execSQL("delete from " + DbOpenHelper.GAME_TABLE_NAME)
            mDb.execSQL("insert into game values(1,'九阴真经ol','最好玩的武侠网游');")
        }).start()
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val table = DbOpenHelper.GAME_TABLE_NAME
        return mDb.query(table, projection, selection, selectionArgs, null, sortOrder, null)
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        mDb.insert(table, null, values)
        mContext?.contentResolver?.notifyChange(uri, null)
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }
}