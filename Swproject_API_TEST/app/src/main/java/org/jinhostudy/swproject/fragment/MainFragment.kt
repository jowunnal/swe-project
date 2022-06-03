package org.jinhostudy.swproject.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jinhostudy.swproject.R
import org.jinhostudy.swproject.adapter.MainCalendarAdapter
import org.jinhostudy.swproject.databinding.MainFragmentBinding
import org.jinhostudy.swproject.listener.OnItemClickListener
import org.jinhostudy.swproject.viewmodel.*
import java.util.*
import kotlin.collections.ArrayList

class MainFragment : Fragment() {
    var _binding:MainFragmentBinding ?= null
    val binding get() = _binding!!
    lateinit var navController:NavController
    lateinit var adapter: MainCalendarAdapter
    val waterViewModel:WaterViewModel by activityViewModels{ WaterViewModelFactory(requireActivity().application) }
    val calendarViewModel:CalendarViewModel by activityViewModels{CalendarViewModelFactory(requireActivity().application)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= MainFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)

        adapter= MainCalendarAdapter()
        binding.recyclerViewMainCalendar.adapter=adapter
        binding.recyclerViewMainCalendar.layoutManager=GridLayoutManager(activity,7)
        val day=calendarViewModel.getToday(Calendar.getInstance())
        val dateList=ArrayList<String>()
        for(data in day){
          dateList.add(data.keys.first().split('-')[2])
        }

        CoroutineScope(Dispatchers.IO).launch{
            adapter.setItems(day,calendarViewModel.indicateToCalendar(day.first().keys.first(),day.last().keys.first(),dateList))
        }
        adapter.notifyDataSetChanged()
        /*var k=1 //데이터 생성하는 코드 1980년~2050년
        var waterList=ArrayList<WaterInfo>()
        for(i in -500..500){
            val list=CalendarUtil.makeday(GregorianCalendar(),i)
            for(j in list){
                calendarViewModel.setCalendar(0,0,2000,SimpleDateFormat("yyyy-MM-").format(CalendarUtil.times)+j)
                val waterInfo=WaterInfo(0,0,2000,k,SimpleDateFormat("yyyy-MM-").format(CalendarUtil.times)+j.toString())
                waterList.add(waterInfo)
            }
        }*/
        val userViewModel=UserViewModel(requireActivity().application)

        userViewModel.showUserInfo().observe(viewLifecycleOwner, Observer {
            if(it.isNullOrEmpty()){
                navController.navigate(R.id.action_mainFragment_to_initialUserInputFragment2)
            }
        })


        calendarViewModel.day.observe(viewLifecycleOwner, Observer { s ->
            var date=s.split("-")
            val cal=Calendar.getInstance()
            cal.set(date[0].toInt(),date[1].toInt()-1,date[2].toInt())
            val day=calendarViewModel.getToday(cal)
            val dateList=ArrayList<String>()
            for(data in day){
                dateList.add(data.keys.first().split('-')[2])
            }
            CoroutineScope(Dispatchers.Main).launch{
                val data=async(Dispatchers.IO){
                    calendarViewModel.indicateToCalendar(day.first().keys.first(),day.last().keys.first(),dateList)
                }
                adapter.setItems(day,data.await())
                adapter.notifyDataSetChanged()
            }
        })

        adapter.SetItemClickListener(object : OnItemClickListener{
            override fun SetOnItemClickListener(v: View, pos: Int) {
                calendarViewModel.changeDays(adapter.getItem(pos))
            }
        })
        /*binding.button2.setOnClickListener{
            binding.progressBar.progress= ((binding.editTextTextPersonName3.text.toString().toDouble())/(Math.pow((binding.editTextTextPersonName2.text.toString().toDouble()/100.0), 2.0))).toInt()
        }*/
        binding.buttonNavigateToCalendar.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_calendarView)
        }
        binding.progressBarWater.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_waterFragment)
        }

        calendarViewModel.day.observe(viewLifecycleOwner, Observer { s ->
            waterViewModel.getDrinkGoal(s).observe(viewLifecycleOwner, Observer {
                binding.progressBarWater.max=it
                //Log.d("test","goalObserve: "+it.toString())
            })
           // Log.d("test","dayObserve: "+s.toString())
            waterViewModel.getDrink(s).observe(viewLifecycleOwner, Observer {
                binding.progressBarWater.progress=it
               // Log.d("test","drinkObserve: "+it.toString())
            })
        })

        binding.pieLayout.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_userFoodFragment)
        }

        binding.linearLayout.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_userFragment)
        }


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