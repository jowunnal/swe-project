package org.jinhostudy.swproject.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import org.jinhostudy.swproject.adapter.CalendarAdapter
import org.jinhostudy.swproject.databinding.CalendarBinding
import org.jinhostudy.swproject.listener.OnItemClickListener
import org.jinhostudy.swproject.viewmodel.CalendarViewModel
import org.jinhostudy.swproject.viewmodel.CalendarViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment :Fragment(){
    var _binding : CalendarBinding ?= null
    val binding get() = _binding !!
    lateinit var adapter: CalendarAdapter
    val calendarViewModel:CalendarViewModel by activityViewModels{CalendarViewModelFactory(requireActivity().application)}
    var numb_flag=0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CalendarBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter= CalendarAdapter()
        binding.recyclerView2.adapter=adapter
        binding.recyclerView2.layoutManager=GridLayoutManager(activity,7)
        val cal=GregorianCalendar()

        calculateMonths(cal,0) //달력화면을 보여주는 메소드  아래에서도 중복으로 사용하기때문에 묘둘화


        binding.calendarMonthsLeft.setOnClickListener { // 해당달에서 이전달로 이동
            numb_flag-=1
            calculateMonths(cal,numb_flag)
        }
        binding.calendarMonthsRight.setOnClickListener { // 해당달에서 다음달로 이동
            numb_flag+=1
            calculateMonths(cal,numb_flag)
        }

        adapter.setItemClickListener(object :OnItemClickListener{ // 달력이 클릭되었을때 해당날짜로 전체데이터갱신
            override fun SetOnItemClickListener(v: View, pos: Int) {
                val day= binding.calendarMonthsHeader.text as String +"-"+adapter.getItem(pos)
                calendarViewModel.changeDays(day)
                findNavController().popBackStack()
            }
        })

    }
    // calendarViewModel로부터 해당날짜값을 받아와서 보여주고, 해당달의 물정보를 가져와서 adapter에 넘김
    @SuppressLint("NotifyDataSetChanged")
    fun calculateMonths(cal:GregorianCalendar, numb_flag:Int){
        val nlist= calendarViewModel.makeday(cal,numb_flag)
        binding.calendarMonthsHeader.text=SimpleDateFormat("yyyy-MM").format(calendarViewModel.times)
        CoroutineScope(Dispatchers.Main).launch {
            val data=async(Dispatchers.IO) {
                calendarViewModel.indicateToCalendar(binding.calendarMonthsHeader.text.toString()+"-"+nlist.first().toString(),
                    binding.calendarMonthsHeader.text.toString()+"-"+nlist.last().toString(),nlist)
            }
            adapter.setItems(nlist,data.await())
            adapter.notifyDataSetChanged()
        }
    }
}
