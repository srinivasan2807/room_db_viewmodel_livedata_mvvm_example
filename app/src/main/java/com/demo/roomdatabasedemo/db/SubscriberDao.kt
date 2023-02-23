package com.demo.roomdatabasedemo.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDao {
    @Insert
    suspend fun addNewSubscriber(subscriber: Subscriber)

    @Update
    suspend fun UpdateSubscriber(subscriber: Subscriber)

    @Delete
    suspend fun DeleteSubscriber(subscriber: Subscriber)

    @Query("DELETE FROM subscriber_data_table")
    fun DeleteAllSubscribers()

    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribers(): LiveData<List<Subscriber>>
}