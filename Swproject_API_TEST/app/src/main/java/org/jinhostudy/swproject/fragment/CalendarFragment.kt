package org.jinhostudy.swproject.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jinhostudy.swproject.R
import org.jinhostudy.swproject.utils.CalendarUtil
import org.jinhostudy.swproject.adapter.CalendarAdapter
import org.jinhostudy.swproject.database.dao.PlannerDao
import org.jinhostudy.swproject.databinding.CalendarBinding
import org.jinhostudy.swproject.listener.OnItemClickListener
import org.jinhostudy.swproject.viewmodel.CalendarViewModel
import org.jinhostudy.swproject.viewmodel.CalendarViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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


        var nlist= CalendarUtil.makeday(cal,0)
        binding.calendarMonthsHeader.text=SimpleDateFormat("yyyy-MM").format(CalendarUtil.times)
        adapter.setItems(nlist)
        calculateMonths(nlist)
        adapter.notifyDataSetChanged()


        binding.textView23.setOnClickListener {
            numb_flag-=1
            nlist= CalendarUtil.makeday(cal,numb_flag)
            binding.calendarMonthsHeader.text=SimpleDateFormat("yyyy-MM").format(CalendarUtil.times)
            adapter.setItems(nlist)
            calculateMonths(nlist)
            adapter.notifyDataSetChanged()
        }
        binding.textView24.setOnClickListener {
            numb_flag+=1
            nlist= CalendarUtil.makeday(cal,numb_flag)
            binding.calendarMonthsHeader.text=SimpleDateFormat("yyyy-MM").format(CalendarUtil.times)
            adapter.setItems(nlist)
            calculateMonths(nlist)
            adapter.notifyDataSetChanged()
        }

        adapter.setItemClickListener(object :OnItemClickListener{
            override fun SetOnItemClickListener(v: View, pos: Int) {
                val day= binding.calendarMonthsHeader.text as String +"-"+adapter.getItem(pos)
                calendarViewModel.changeDays(day)
                findNavController().popBackStack()
            }

        })
    }
    fun calculateMonths(nlist:ArrayList<String>){
        CoroutineScope(Dispatchers.IO).launch{adapter.setWaterInfo(calendarViewModel.indicateToCalendar(binding.calendarMonthsHeader.text.toString()+"-"+nlist.first().toString(),
            binding.calendarMonthsHeader.text.toString()+"-"+nlist.last().toString()))}

    }
}
