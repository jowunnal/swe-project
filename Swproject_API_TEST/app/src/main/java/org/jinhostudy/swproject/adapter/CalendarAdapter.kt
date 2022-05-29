package org.jinhostudy.swproject.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jinhostudy.swproject.database.entity.WaterInfo
import org.jinhostudy.swproject.databinding.ItemCalendarBinding
import org.jinhostudy.swproject.listener.OnItemClickListener

class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.ViewHolder>(),OnItemClickListener {
    var list = ArrayList<String>()
    var mlistener:OnItemClickListener?=null
    var list_Water=ArrayList<Boolean>()
    inner class ViewHolder(private val itemCalendarBinding: ItemCalendarBinding) : RecyclerView.ViewHolder(itemCalendarBinding.root) {
        fun bind(data : String,pos:Int){
            if(data!="0"){
                itemCalendarBinding.tvCalendarItem.text=data
            }
            else{
                itemCalendarBinding.tvCalendarItem.text=""
            }

            if(list_Water.isNotEmpty()){
                if(!list_Water[pos]){
                    itemCalendarBinding.calendarMonthsWater.setBackgroundColor(Color.BLUE)
                }
                else
                    itemCalendarBinding.calendarMonthsWater.setBackgroundColor(Color.WHITE)
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
        holder.bind(list[position],position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItems(list:List<String>){
        this.list.clear()
        this.list.addAll(list)
    }
    fun getItem(pos:Int):String{
        return list[pos]
    }
    fun setWaterInfo(waterInfo: List<WaterInfo>) {
        list_Water.clear()
        for(data in list){
            if(data=="0")
                list_Water.add(true)
        }
        for(data in waterInfo){
            val cal_water=data.user_today_mount-data.user_input_mount
            if(cal_water>0)
                this.list_Water.add(false)
            else
                this.list_Water.add(true)
        }


    }

    override fun SetOnItemClickListener(v: View, pos: Int) {
        mlistener?.SetOnItemClickListener(v,pos)
    }
    fun setItemClickListener(listener:OnItemClickListener){
        this.mlistener=listener
    }
}