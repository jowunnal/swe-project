package org.jinhostudy.swproject.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jinhostudy.swproject.database.entity.WaterInfo
import org.jinhostudy.swproject.databinding.ItemCalendar7daysBinding
import org.jinhostudy.swproject.listener.OnItemClickListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainCalendarAdapter : RecyclerView.Adapter<MainCalendarAdapter.ViewHolder>(),OnItemClickListener {
    var list= ArrayList<HashMap<String, String>>() //key 값이 날짜, value 값이 요일
    var mlistener:OnItemClickListener ?=null
    var list_Water=ArrayList<Boolean>()
    inner class ViewHolder(private val binding: ItemCalendar7daysBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data:HashMap<String,String>,pos:Int){
            for(key in data.keys){ // 7일달력내에 일자+요일 나타내기
                binding.calendar7daysDate.text=key.split('-')[2]
                binding.calendar7daysDay.text=data[key]
            }

            //오늘의요일을 받아와서
            val cal= Calendar.getInstance()
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
            // 7일달력내에 오늘의 요일에 파란색깔 칠하기
            if(day==binding.calendar7daysDay.text && SimpleDateFormat("dd").format(Date(cal.timeInMillis))==binding.calendar7daysDate.text)
                binding.calendar7daysDay.setBackgroundColor(Color.BLUE)
            else
                binding.calendar7daysDay.setBackgroundColor(Color.WHITE)

            //7일달력내에 물의양이 목표량보다 덜먹었다면 파란색칠하기
            if(list_Water.isNotEmpty()){
                if(!list_Water[pos]){
                    binding.calendar7daysWater.setBackgroundColor(Color.BLUE)
                }
                else
                    binding.calendar7daysWater.setBackgroundColor(Color.WHITE)
            }


            //7일달력 클릭 리스너
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
        holder.bind(list[position],position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItems(list: ArrayList<HashMap<String, String>>,waterInfo: ArrayList<Boolean>){
        val dataList=ArrayList<HashMap<String,String>>()
        val waterList=ArrayList<Boolean>()
        dataList.addAll(list)
        waterList.addAll(waterInfo)

        this.list.clear()
        list_Water.clear()
        this.list.addAll(dataList)
        list_Water.addAll(waterList)
    }
    fun getItem(pos:Int): String{
        var data=""
        for(key in list[pos].keys){
            data= key
        }
        return data
    }

    fun SetItemClickListener(listener: OnItemClickListener){
        this.mlistener=listener
    }
    override fun SetOnItemClickListener(v: View, pos: Int) {
        mlistener?.SetOnItemClickListener(v,pos)
    }
}