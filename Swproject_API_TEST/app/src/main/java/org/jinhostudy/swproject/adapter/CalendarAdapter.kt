package org.jinhostudy.swproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jinhostudy.swproject.databinding.ItemCalendarBinding
import org.jinhostudy.swproject.listener.OnItemClickListener

class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.ViewHolder>(),OnItemClickListener {
    var list = ArrayList<Int>()
    var mlistener:OnItemClickListener?=null
    inner class ViewHolder(private val itemCalendarBinding: ItemCalendarBinding) : RecyclerView.ViewHolder(itemCalendarBinding.root) {
        fun bind(data : Int){
            if(data!=0){
                itemCalendarBinding.tvCalendarItem.text=data.toString()
            }
            else{
                itemCalendarBinding.tvCalendarItem.text=""
            }
            itemCalendarBinding.root.setOnClickListener {
                SetOnItemClickListener(it,adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCalendarBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItems(list:List<Int>){
        this.list.clear()
        this.list.addAll(list)
    }
    fun getItem(pos:Int):String{
        return list[pos].toString()
    }

    override fun SetOnItemClickListener(v: View, pos: Int) {
        mlistener?.SetOnItemClickListener(v,pos)
    }
    fun setItemClickListener(listener:OnItemClickListener){
        this.mlistener=listener
    }
}