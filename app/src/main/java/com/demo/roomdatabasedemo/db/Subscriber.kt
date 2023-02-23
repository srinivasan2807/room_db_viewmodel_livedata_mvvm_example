package com.demo.roomdatabasedemo.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriber_data_table")
data class Subscriber(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subscriberId")
    val subscriber_id: Int,
    @ColumnInfo(name = "subscriberName")
    var subscriber_name: String,
    @ColumnInfo(name = "subscriberEmail")
    var subscriber_email: String
)