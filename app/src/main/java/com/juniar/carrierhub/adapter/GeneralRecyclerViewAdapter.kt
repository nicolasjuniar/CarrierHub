@file:JvmName("GeneralRecyclerViewAdapter")

package com.juniar.carrierhub.adapter

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.SparseBooleanArray


/**
 * Created by voen on 05/01/18.
 */
class GeneralRecyclerViewAdapter<T>(@LayoutRes private val resId: Int, private val rooms: MutableList<T>, private val listener: (T, position: Int, View) -> Unit, private val viewHolderBindAction: (T, View) -> Unit) : RecyclerView.Adapter<GeneralRecyclerViewAdapter.GeneralViewHolder<T>>() {
    private val selectedItems: SparseBooleanArray? = null
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = GeneralViewHolder<T>(LayoutInflater.from(parent?.context).inflate(resId, parent, false))

    override fun onBindViewHolder(holder: GeneralViewHolder<T>?, position: Int) {
        holder?.bind(rooms[position], listener, viewHolderBindAction)
    }

    override fun getItemCount() = rooms.size

    class GeneralViewHolder<T>(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(any: T, listener: (T, position: Int, View) -> Unit, viewHolderBindAction: (T, View) -> Unit) = with(itemView) {
            viewHolderBindAction(any, itemView)
            setOnClickListener { listener(any, adapterPosition, itemView) }
        }
    }

}
