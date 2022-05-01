package org.jinhostudy.swproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import org.jinhostudy.swproject.CalendarUtil
import org.jinhostudy.swproject.R
import org.jinhostudy.swproject.adapter.MainCalendarAdapter
import org.jinhostudy.swproject.databinding.MainFragmentBinding
import java.util.*

class MainFragment : Fragment() {
    var _binding:MainFragmentBinding ?= null
    val binding get() = _binding!!
    lateinit var navController:NavController
    lateinit var adapter: MainCalendarAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= MainFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)

        adapter= MainCalendarAdapter()
        binding.mainCalendar.adapter=adapter
        binding.mainCalendar.layoutManager=GridLayoutManager(activity,7)
        adapter.setItems(listOf("일요일","월요일","화요일","수요일","목요일","금요일","토요일"))
        adapter.notifyDataSetChanged()
        binding.button2.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_calendarView)
        }

        //navController.navigate(R.id.action_mainFragment_to_calendarView)
    }
}