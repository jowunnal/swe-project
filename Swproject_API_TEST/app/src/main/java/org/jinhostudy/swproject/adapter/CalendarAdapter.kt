package org.jinhostudy.swproject.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jinhostudy.swproject.databinding.ItemCalendarBinding
import org.jinhostudy.swproject.listener.OnItemClickListener

class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.ViewHolder>(),OnItemClickListener {
    var list = ArrayList<String>() // 리사이클러뷰 내부 아이템을 저장하는 list변수
    var mlistener:OnItemClickListener?=null //리스너객체
    var list_Water=ArrayList<Boolean>() // 달력내에 물정보의 목표량-섭취량이 >0 이면, false 아니면, true로 두어 false이면 파란색줄을치기위한 list변수

    inner class ViewHolder(private val itemCalendarBinding: ItemCalendarBinding) : RecyclerView.ViewHolder(itemCalendarBinding.root) {
        fun bind(data : String,pos:Int){ //inner class로 리사이클러뷰를 구현하려면 반드시 필요한 구현체 클래스라 클래스다이어그램에서 생략
            if(data!="0"){               // bind()내부에 리사이클러뷰 내부의 아이템뷰 각각에대해서 oncreateView()에서 반환된 메모리상에 객체화된 xml화면구성 뷰들을
                itemCalendarBinding.tvCalendarItem.text=data // 직접 접근하여 텍스트나 색깔 을 지정(set)하고, 리스너구현체가 이곳에서 구현됨됨
           }
            else{
                itemCalendarBinding.tvCalendarItem.text=""
            }

            if(list_Water.isNotEmpty()){

                if(!list_Water[pos]){//파란색은 덜먹었다는표시
                    itemCalendarBinding.calendarMonthsWater.setBackgroundColor(Color.BLUE)
                }
                else// 색깔이없으면 목표량이상만큼 물을섭취했다는 표시
                    itemCalendarBinding.calendarMonthsWater.setBackgroundColor(Color.WHITE)
            }
            if(data!="0"){// 연결된 리스너객체를통해 리스너인터페이스 구현체를 호출하여 리스너구현
                itemCalendarBinding.root.setOnClickListener {
                    SetOnItemClickListener(it,adapterPosition)
                }
            }
        }

    }
    // 리사이클러뷰 내의 아이템뷰 전체를 create하는 객체생성시 최초한번 수행
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCalendarBinding.inflate(LayoutInflater.from(parent.context)))
    }
    // 리사이클러뷰내의 아이템뷰의 개수에서 +- 앞뒤로 일정개수만큼 미리 메모리에 할당해놓고, 스크롤링할때마다 이전부분은 메모리에서제거, 이후부분은 메모리에 할당
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position],position)
    }
    // 위의 메소드에 미리개수를 알아야하기때문에 필요한 아이템뷰 전체개수를 반환하는 메소드
    override fun getItemCount(): Int {
        return list.size
    }
    // 아이템뷰를 set하는 메소드
    fun setItems(list:List<String>,waterInfo: ArrayList<Boolean>){
        val dataList=ArrayList<String>()
        val waterList=ArrayList<Boolean>()

        dataList.addAll(list) //Deep Copy
        waterList.addAll(waterInfo)
        this.list.clear()
        list_Water.clear()
        this.list.addAll(dataList)
        list_Water.addAll(waterList)
    }
    fun getItem(pos:Int):String{ // 리사이클러뷰의 클릭된 위치에있는 값을 가져오기위해 위치(pos)에 대한 리스트내의 아이템반환
        return list[pos]
    }

    override fun SetOnItemClickListener(v: View, pos: Int) { //리사이클러뷰 클릭리스너 인터페이스 내부구현체
        mlistener?.SetOnItemClickListener(v,pos)
    }
    fun setItemClickListener(listener:OnItemClickListener){ // 외부의 리스너객체를 받아와 연결
        this.mlistener=listener
    }
}