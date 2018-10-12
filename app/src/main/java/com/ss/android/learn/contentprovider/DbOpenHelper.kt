package com.ss.android.learn.contentprovider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbOpenHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    private val CREATE_GAME_TABLE = "create table if not exists $GAME_TABLE_NAME(_id integer primary key,name TEXT, describe TEXT)"
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_GAME_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        private val DB_NAME = "game_provider.db"
        internal val GAME_TABLE_NAME = "game"
        private val DB_VERSION = 1
    }
}