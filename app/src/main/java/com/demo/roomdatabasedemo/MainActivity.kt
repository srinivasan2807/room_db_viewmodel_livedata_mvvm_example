package com.demo.roomdatabasedemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.roomdatabasedemo.databinding.ActivityMainBinding
import com.demo.roomdatabasedemo.db.Database
import com.demo.roomdatabasedemo.db.Subscriber
import com.demo.roomdatabasedemo.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: SubscriberViewModel
    private lateinit var viewModelFactory: SubscriberViewModelFactory
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = Database.getInstance(application).subscriberDao
        val repository = SubscriberRepository(dao)
        viewModelFactory = SubscriberViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SubscriberViewModel::class.java]
        binding.mySubscriberViewModel = viewModel
        binding.lifecycleOwner = this
        displayAllUsers()
        viewModel.eventMessage.observe(this) {
            it.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayAllUsers() {
        viewModel.subscribers.observe(this) { subscriberList ->
            val adapter = SubscriberAdapter(subscriberList) { subscriberSelectedItem: Subscriber ->
                listItemClicked(
                    subscriberSelectedItem
                )
            }
            binding.subscriberList.layoutManager = LinearLayoutManager(this)
            binding.subscriberList.itemAnimator = DefaultItemAnimator()
            binding.subscriberList.adapter = adapter
        }
    }

    private fun listItemClicked(subscriber: Subscriber) {
        viewModel.initUpdateOrDelete(subscriber)
    }
}