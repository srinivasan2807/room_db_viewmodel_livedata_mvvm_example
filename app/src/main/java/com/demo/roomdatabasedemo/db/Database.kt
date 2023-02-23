package com.demo.roomdatabasedemo.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@androidx.room.Database(entities = [Subscriber::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract val subscriberDao: SubscriberDao

    companion object {
        @Volatile
        private var INSTANCE: Database? = null
        fun getInstance(context: Context): Database {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context = context,
                        klass = Database::class.java,
                        name = "subscriberDatabase"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}