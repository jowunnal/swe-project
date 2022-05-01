package org.jinhostudy.swproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jinhostudy.swproject.databinding.ItemCalendarBinding

class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    var list = ArrayList<Int>()
    class ViewHolder(private val itemCalendarBinding: ItemCalendarBinding) : RecyclerView.ViewHolder(itemCalendarBinding.root) {
        fun bind(data : Int){
            if(data!=0){
                itemCalendarBinding.textView15.text=data.toString()
            }
            else{
                itemCalendarBinding.textView15.text=""
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
}