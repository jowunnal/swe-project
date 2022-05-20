package org.jinhostudy.swproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import org.jinhostudy.swproject.utils.CalendarUtil
import org.jinhostudy.swproject.adapter.CalendarAdapter
import org.jinhostudy.swproject.database.dao.PlannerDao
import org.jinhostudy.swproject.databinding.CalendarBinding
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment :Fragment(){
    var _binding : CalendarBinding ?= null
    val binding get() = _binding !!
    lateinit var adapter: CalendarAdapter
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
        val plannerDao:PlannerDao


        var nlist= CalendarUtil.makeday(cal,0)
        binding.textView14.text=SimpleDateFormat("yyyy-MM-dd").format(CalendarUtil.times)
        adapter.setItems(nlist)

        binding.textView23.setOnClickListener {
            numb_flag-=1
            nlist= CalendarUtil.makeday(cal,numb_flag)
            binding.textView14.text=SimpleDateFormat("yyyy-MM-dd").format(CalendarUtil.times)
            adapter.setItems(nlist)
            adapter.notifyDataSetChanged()
        }
        binding.textView24.setOnClickListener {
            numb_flag+=1
            nlist= CalendarUtil.makeday(cal,numb_flag)
            binding.textView14.text=SimpleDateFormat("yyyy-MM-dd").format(CalendarUtil.times)
            adapter.setItems(nlist)
            adapter.notifyDataSetChanged()
        }
    }
}
