package com.demo.roomdatabasedemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.demo.roomdatabasedemo.databinding.RowSubscriberItemBinding
import com.demo.roomdatabasedemo.db.Subscriber

class SubscriberAdapter(private var list: List<Subscriber>, private val clickListener: (Subscriber) -> Unit) :
    RecyclerView.Adapter<SubscriberDataViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberDataViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<RowSubscriberItemBinding>(
            layoutInflater,
            R.layout.row_subscriber_item,
            parent,
            false
        )
        return SubscriberDataViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SubscriberDataViewHolder, position: Int) {
        val subscriberItem = list[position]
        holder.bind(subscriberItem,clickListener)
    }
}

class SubscriberDataViewHolder(val binding: RowSubscriberItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(subscriber: Subscriber,clickListener: (Subscriber) -> Unit) {
        binding.textName.text = subscriber.subscriber_name
        binding.textEmail.text = subscriber.subscriber_email
        binding.llDetailsLayout.setOnClickListener{
            clickListener(subscriber)
        }
    }
}