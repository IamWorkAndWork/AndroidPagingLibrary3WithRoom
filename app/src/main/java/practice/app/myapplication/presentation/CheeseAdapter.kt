package practice.app.myapplication.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import practice.app.myapplication.R
import practice.app.myapplication.data.model.Cheese

class CheeseAdapter : PagingDataAdapter<Cheese, CheeseAdapter.CheeseViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Cheese>() {
            override fun areItemsTheSame(oldItem: Cheese, newItem: Cheese): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cheese, newItem: Cheese): Boolean {
                return oldItem == newItem
            }
        }
    }

    var onClickItem: ((Cheese) -> Unit)? = null

    override fun onBindViewHolder(holder: CheeseViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheeseViewHolder {
        return CheeseViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cheese_item, parent, false),
            onClickItem
        )
    }

    inner class CheeseViewHolder(
        itemView: View,
        private val onClickItem: ((Cheese) -> Unit)?
    ) : RecyclerView.ViewHolder(itemView) {

        private val nameView = itemView.findViewById<TextView>(R.id.name)
        var cheese: Cheese? = null

        init {
            itemView.setOnClickListener {
                cheese?.let {
                    onClickItem?.invoke(it)
                }
            }
        }

        fun bindTo(cheese: Cheese?) {
            this.cheese = cheese
            nameView.text = cheese?.name
        }

    }
}