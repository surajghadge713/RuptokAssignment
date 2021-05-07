package com.surajappdeveloper.ruptokassignment.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Generic Adapter for set recycler view
 * @param T Response model
 * @param D Item Layout binding class
 * @property context Context object
 * @property mArrayList ArrayList<T>? model type of list
 * @property layoutResId Int layout name
 * @constructor
 */
abstract class GenericRecyclerViewAdapter<T, D>(
    val context: Context,
    private var mArrayList: ArrayList<T>?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract val layoutResId: Int

    abstract fun onBindData(model: T?, position: Int?, dataBinding: D?)

    abstract fun onItemClick(model: T?, position: Int?)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val dataBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutResId,
            parent,
            false
        )
        return ItemViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindData(
            mArrayList?.get(position), holder.layoutPosition,
            dataBinding = (holder as GenericRecyclerViewAdapter<*, *>.ItemViewHolder).mDataBinding as D
        )

        (holder.mDataBinding as ViewDataBinding).root.setOnClickListener {
            onItemClick(
                mArrayList?.get(position),
                position
            )
        }
    }

    override fun getItemCount(): Int {
        return mArrayList?.size ?: 0
    }

    fun getItem(position: Int): T? {
        return mArrayList?.get(position)
    }

    internal inner class ItemViewHolder(binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var mDataBinding: D = binding as D
    }

    fun addItemsRange(arrayList: ArrayList<T>?, startSize: Int?) {
        if (!arrayList.isNullOrEmpty() && startSize != null) {
            this.mArrayList?.addAll(arrayList)
            this.notifyItemRangeChanged(startSize, arrayList.size)
        }
    }

    fun getListSize(): Int? {
        return mArrayList?.size
    }

}
