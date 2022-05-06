package org.jinhostudy.swproject.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import org.jinhostudy.swproject.R
import org.jinhostudy.swproject.adapter.MainCalendarAdapter
import org.jinhostudy.swproject.databinding.MainFragmentBinding
import org.jinhostudy.swproject.listener.OnItemClickListener
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

        adapter.SetItemListener(object : OnItemClickListener{
            override fun SetOnItemClickListener(v: View, pos: Int) {
                navController.navigate(R.id.action_mainFragment_to_calendarView)
            }
        })
        /*binding.button2.setOnClickListener{
            binding.progressBar.progress= ((binding.editTextTextPersonName3.text.toString().toDouble())/(Math.pow((binding.editTextTextPersonName2.text.toString().toDouble()/100.0), 2.0))).toInt()
        }*/




        binding.pieChart.setUsePercentValues(true)
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(15f, "단백질"))
        entries.add(PieEntry(20f, "탄수화물"))
        entries.add(PieEntry(30f, "지방"))
        entries.add(PieEntry(20f, "나트륨"))
        entries.add(PieEntry(15f, "당류"))

        val colorsitems = java.util.ArrayList<Int>()
        for(c in ColorTemplate.VORDIPLOM_COLORS)colorsitems.add(c)
        for(c in ColorTemplate.JOYFUL_COLORS)colorsitems.add(c)
        for(c in ColorTemplate.LIBERTY_COLORS)colorsitems.add(c)
        colorsitems.add(ColorTemplate.getHoloBlue())
        val pieDataSet = PieDataSet(entries, "")
        pieDataSet.apply {
            colors = colorsitems
            valueTextColor = Color.BLACK
            valueTextSize = 16f
        }
        val pieData = PieData(pieDataSet)
        binding.pieChart.apply {
            data = pieData
            description.isEnabled = false
            isRotationEnabled = false
            centerText = "영양성분"
            setEntryLabelColor(Color.BLACK)
            animateY(1400, Easing.EaseInOutQuad)
            animate()
        }
        binding.pieChart.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_userFoodFragment)
        }
    }
}