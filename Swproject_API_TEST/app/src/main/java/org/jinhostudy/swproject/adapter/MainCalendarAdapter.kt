package org.jinhostudy.swproject.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jinhostudy.swproject.CalendarUtil
import org.jinhostudy.swproject.databinding.ItemCalendar7daysBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class MainCalendarAdapter : RecyclerView.Adapter<MainCalendarAdapter.ViewHolder>() {
    val list=ArrayList<String>()

    class ViewHolder(private val binding: ItemCalendar7daysBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data:String){
            binding.calendar7daysTv.text=data
            if(CalendarUtil.getToday(GregorianCalendar())==data)
                binding.calendar7daysTv.setBackgroundColor(Color.BLUE)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MainCalendarAdapter.ViewHolder {
        return ViewHolder(ItemCalendar7daysBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MainCalendarAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItems(list:List<String>){
        this.list.clear()
        this.list.addAll(list)
    }
}