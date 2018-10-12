package com.ss.android.multiprocessapp

import android.os.Parcel
import android.os.Parcelable

class Game : Parcelable {
    var gameName: String
    var gameDescribe: String

    constructor(gameName: String, gameDescribe: String) {
        this.gameName = gameName
        this.gameDescribe = gameDescribe
    }

    protected constructor(`in`: Parcel) {
        gameName = `in`.readString()
        gameDescribe = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(gameName)
        dest.writeString(gameDescribe)
    }

    companion object CREATOR : Parcelable.Creator<Game> {
        override fun createFromParcel(parcel: Parcel): Game {
            return Game(parcel)
        }

        override fun newArray(size: Int): Array<Game?> {
            return arrayOfNulls(size)
        }
    }

}