package io.h3llo.appmarket.core

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> (var data: List<T>) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder<T>>(){

    abstract fun getViewHolder(parent: ViewGroup): BaseViewHolder<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return getViewHolder(parent)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(data[position])
    }

    fun update(nData: List<T>){
        data = nData
        notifyDataSetChanged()
    }

    fun reset() {
        data = emptyList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view){
        abstract fun bind(entity: T)
    }

}