package org.jinhostudy.swproject.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jinhostudy.swproject.utils.CalendarUtil
import org.jinhostudy.swproject.databinding.ItemCalendar7daysBinding
import org.jinhostudy.swproject.listener.OnItemClickListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainCalendarAdapter : RecyclerView.Adapter<MainCalendarAdapter.ViewHolder>(),OnItemClickListener {
    val key_list=ArrayList<Int>()
    var list=HashMap<Int,String>()
    var mlistener:OnItemClickListener ?=null
    inner class ViewHolder(private val binding: ItemCalendar7daysBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data:Int){
            binding.calendar7daysDate.text=data.toString()
            binding.calendar7daysDay.text=list.getValue(data)
            val cal=Calendar.getInstance()
            var day=SimpleDateFormat("E").format(Date(cal.timeInMillis))
            when(day){
                "일"->day= "일요일"
                "월"->day= "월요일"
                "화"->day= "화요일"
                "수"->day= "수요일"
                "목"->day= "목요일"
                "금"->day= "금요일"
                "토"->day= "토요일"
            }
            if(day==list.getValue(data) && SimpleDateFormat("d").format(Date(cal.timeInMillis)).toInt()==data)
                binding.calendar7daysDay.setBackgroundColor(Color.BLUE)
            binding.root.setOnClickListener {
                SetOnItemClickListener(it,adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MainCalendarAdapter.ViewHolder {
        return ViewHolder(ItemCalendar7daysBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MainCalendarAdapter.ViewHolder, position: Int) {
        holder.bind(key_list[position])
    }

    override fun getItemCount(): Int {
        return key_list.size
    }

    fun setItems(list:HashMap<Int,String>){
        this.list.clear()
        this.key_list.clear()
        key_list.addAll(list.keys)
        this.list=list
    }
    fun SetItemClickListener(listener: OnItemClickListener){
        this.mlistener=listener
    }
    override fun SetOnItemClickListener(v: View, pos: Int) {
        mlistener?.SetOnItemClickListener(v,pos)
    }
}