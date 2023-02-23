package com.demo.roomdatabasedemo.db

class SubscriberRepository(private val dao: SubscriberDao) {
    val subscribers = dao.getAllSubscribers()

    suspend fun insert(subscriber: Subscriber) {
        dao.addNewSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber) {
        dao.UpdateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber) {
        dao.DeleteSubscriber(subscriber)
    }

    suspend fun deleteAll() {
        dao.DeleteAllSubscribers()
    }
}