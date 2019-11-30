package com.example.aplustea

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [PersonalInfo::class], version = 1)
abstract class BubbleDB : RoomDatabase(){
    abstract fun bubbleDAO():BubbleDAO

    companion object {
        private var INSTANT: BubbleDB? = null

        fun getDBObject(context: Context): BubbleDB? {
            if (INSTANT == null) {
                synchronized(BubbleDB::class.java) {
                    INSTANT = databaseBuilder(context, BubbleDB::class.java, "bubbleDB")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANT
        }
    }
}