package com.demo.roomdatabasedemo

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.roomdatabasedemo.db.Subscriber
import com.demo.roomdatabasedemo.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscriberViewModel(private val subscriberRepository: SubscriberRepository) : ViewModel() {
    val subscribers = subscriberRepository.subscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    val subscriberInputName = MutableLiveData<String>()
    val subscriberInputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val subscriberMessage = MutableLiveData<Event<String>>()

    val eventMessage: LiveData<Event<String>>
        get() = subscriberMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "ClearAll"
    }

    fun saveOrUpdate() {
        if (isUpdateOrDelete) {
            subscriberToUpdateOrDelete.subscriber_name = subscriberInputName.value!!
            subscriberToUpdateOrDelete.subscriber_email = subscriberInputEmail.value!!
            update(subscriberToUpdateOrDelete)
        } else {
            val name = subscriberInputName.value
            val email = subscriberInputEmail.value
            when {
                name.isNullOrEmpty() -> subscriberMessage.value = Event("Enter a subscriber name")
                email.isNullOrEmpty() -> subscriberMessage.value = Event("Enter a subscriber email")
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    subscriberMessage.value = Event("Enter a valid email")
                }
                else -> {
                    val subscriber = Subscriber(0, name, email)
                    insert(subscriber)
                }
            }
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        } else {
            deleteAll()
        }
    }

    private fun insert(subscriber: Subscriber) = viewModelScope.launch(IO) {
        subscriberRepository.insert(subscriber)
        withContext(Main) {
            subscriberMessage.value = Event("Subscriber Inserted Successfully")
        }
    }

    private fun update(subscriber: Subscriber) = viewModelScope.launch(IO) {
        subscriberRepository.update(subscriber)
        withContext(Main) {
            subscriberInputName.value = subscriber.subscriber_name
            subscriberInputEmail.value = subscriber.subscriber_email
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            subscriberMessage.value = Event("Subscriber Updated Successfully")
        }
    }

    private fun delete(subscriber: Subscriber) = viewModelScope.launch(IO) {
        subscriberRepository.delete(subscriber)
        withContext(Main) {
            subscriberInputName.value = ""
            subscriberInputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            subscriberMessage.value = Event("Subscriber Deleted Successfully")
        }
    }

    private fun deleteAll() = viewModelScope.launch(IO) {
        subscriberRepository.deleteAll()
        withContext(Main) {
            subscriberMessage.value = Event("All records cleared Successfully")
        }
    }

    fun initUpdateOrDelete(subscriber: Subscriber) {
        subscriberInputName.value = subscriber.subscriber_name
        subscriberInputEmail.value = subscriber.subscriber_email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

}