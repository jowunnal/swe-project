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
    private var _binding:MainFragmentBinding ?= null
    val binding get() = _binding!!
    lateinit var navController:NavController
    lateinit var adapter: MainCalendarAdapter
    private val waterViewModel:WaterViewModel by activityViewModels{ WaterViewModelFactory(requireActivity().application) }
    val calendarViewModel:CalendarViewModel by activityViewModels{CalendarViewModelFactory(requireActivity().application)}
    private val userViewModel:UserViewModel by lazy{UserViewModel(requireActivity().application)}
    private val foodManageViewModel:FoodManageViewModel by lazy { FoodManageViewModel(requireActivity().application) }
    var kcal = 0
    var carbo = 0
    var protein = 0
    var fat = 0
    var sweet = 0

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

        /*var k=1 //????????? ???????????? ?????? 1980???~2060???
        var waterList=ArrayList<WaterInfo>()
        for(i in -500..500){
            val list=CalendarUtil.makeday(GregorianCalendar(),i)
            for(j in list){
                calendarViewModel.setCalendar(0,0,2000,SimpleDateFormat("yyyy-MM-").format(CalendarUtil.times)+j)
                val waterInfo=WaterInfo(0,0,2000,k,SimpleDateFormat("yyyy-MM-").format(CalendarUtil.times)+j.toString())
                waterList.add(waterInfo)
            }
        }*/

        //BMI?????? ???????????? BMI??? ???????????? ?????????,??????,?????????,??????,???????????? ?????? ?????????
        userViewModel.showUserInfo().observe(viewLifecycleOwner, Observer {
            if(it.isNullOrEmpty()){
                navController.navigate(R.id.action_mainFragment_to_initialUserInputFragment2)
            }
            if(it.isNotEmpty()){
                binding.progressBarBmi.max=35
                binding.progressBarBmi.progress= (it.last().user_weight/(it.last().user_height*it.last().user_height/10000)).toInt()
            }
            else{
                binding.progressBarBmi.progress=0
            }
        })

        //????????? ???????????? ????????????????????? ???????????????????????? ?????????
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

        adapter.SetItemClickListener(object : OnItemClickListener{ //??????????????? ????????? ???????????? ??????????????? ????????????
            override fun SetOnItemClickListener(v: View, pos: Int) {
                calendarViewModel.changeDays(adapter.getItem(pos))
            }
        })

        binding.buttonNavigateToCalendar.setOnClickListener { // ?????????????????? ??????
            navController.navigate(R.id.action_mainFragment_to_calendarView)
        }
        binding.progressBarWater.setOnClickListener { // ??????????????? ??????
            navController.navigate(R.id.action_mainFragment_to_waterFragment)
        }

        // ????????? ???????????? ????????? ??? ?????????????????? ????????? ????????????????????? ????????? ??????
        calendarViewModel.day.observe(viewLifecycleOwner, Observer { s ->
            waterViewModel.getDrinkGoal(s).observe(viewLifecycleOwner, Observer {
                binding.progressBarWater.max=it
            })
            waterViewModel.getDrink(s).observe(viewLifecycleOwner, Observer {
                binding.progressBarWater.progress=it
            })
        })


        binding.linearLayoutBMI.setOnClickListener { // ??????????????? ???????????? ??????
            navController.navigate(R.id.action_mainFragment_to_userFragment)
        }
        binding.foodInfoLayout.setOnClickListener { //???????????????????????? ??????
            navController.navigate(R.id.action_mainFragment_to_userFoodFragment)
        }

        calendarViewModel.day.observe(viewLifecycleOwner,Observer{ s ->
            foodManageViewModel.getFoodAll(s).observe(viewLifecycleOwner, Observer {
                if(it != null){
                    CoroutineScope(Dispatchers.Main).launch{
                        val c_kcal=async{foodManageViewModel.calculateKcal(calendarViewModel.getDays())}
                        val c_nut=async{foodManageViewModel.calculateNut(calendarViewModel.getDays())}
                        kcal=c_kcal.await()
                        carbo=c_nut.await().food_carbo
                        sweet=c_nut.await().food_sweet
                        protein =c_nut.await().food_protein
                        fat=c_nut.await().food_fat
                        showPieChart(kcal,carbo,sweet,protein,fat)
                    }
                    var food_breakfast=""
                    var food_lunch=""
                    var food_dinner=""
                    for(food in it){
                        when(food.food_distinguish){
                            "??????"->food_breakfast+=food.food_name
                            "??????"->food_lunch+=food.food_name
                            "??????"->food_dinner+=food.food_name
                        }
                    }
                    binding.tvBreakfast.text=food_breakfast
                    binding.tvLunch.text=food_lunch
                    binding.tvDinner.text=food_dinner
                }

            })
        })


    }
    fun showPieChart(protein:Int,carbo:Int,fat:Int,sodium:Int,sweet:Int){
        binding.pieChart.setUsePercentValues(true)
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(protein.toFloat(), "?????????"))
        entries.add(PieEntry(carbo.toFloat(), "????????????"))
        entries.add(PieEntry(fat.toFloat(), "??????"))
        entries.add(PieEntry(sodium.toFloat(), "?????????"))
        entries.add(PieEntry(sweet.toFloat(), "??????"))
        //???????????? ????????? ???
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
            centerText = "????????????"
            setEntryLabelColor(Color.BLACK)
            animateY(1400, Easing.EaseInOutQuad)
            animate()
        }
    }
}