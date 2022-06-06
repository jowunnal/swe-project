package org.jinhostudy.swproject.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jinhostudy.swproject.database.entity.FoodInfo
import org.jinhostudy.swproject.databinding.ItemUserfoodBinding
import org.jinhostudy.swproject.listener.OnItemClickListener

class FoodAdapter(): RecyclerView.Adapter<FoodAdapter.ViewHolder>(),OnItemClickListener {
    var list= ArrayList<FoodInfo>()
    var mlistener:OnItemClickListener?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUserfoodBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun setItems(list:List<FoodInfo>){
        this.list.clear()
        this.list.addAll(list)
    }

    fun getItem(pos:Int):FoodInfo{
        return list[pos]
    }

    inner class ViewHolder(private val binding:ItemUserfoodBinding) : RecyclerView.ViewHolder(binding.root) {
        val foodDistinguish = binding.mealTextView
        val foodName = binding.menuTextView

        fun bind(item: FoodInfo){
            foodDistinguish.text = item.food_distinguish
            foodName.text = item.food_name

            binding.root.setOnClickListener {
                SetOnItemClickListener(it,adapterPosition)
            }
        }

    }

    override fun SetOnItemClickListener(v: View, pos: Int) {
        this.mlistener?.SetOnItemClickListener(v,pos)
    }
    fun setItemClickListener(listener: OnItemClickListener){
        this.mlistener=listener
    }
}