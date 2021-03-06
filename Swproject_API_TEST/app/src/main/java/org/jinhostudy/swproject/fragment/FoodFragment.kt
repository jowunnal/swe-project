package org.jinhostudy.swproject.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
import org.jinhostudy.swproject.adapter.FoodAdapter
import org.jinhostudy.swproject.database.entity.FoodInfo
import org.jinhostudy.swproject.databinding.UserfoodBinding
import org.jinhostudy.swproject.listener.OnItemClickListener
import org.jinhostudy.swproject.viewmodel.*
import java.util.regex.Pattern

class FoodFragment : Fragment() {
    val apiViewModel: ApiViewModel by activityViewModels{ ApiViewModelFactory(requireActivity().application) }
    private val foodManageViewModel: FoodManageViewModel by lazy { FoodManageViewModel(requireActivity().application) }
    private val calendarViewModel: CalendarViewModel by activityViewModels{CalendarViewModelFactory(requireActivity().application)}
    lateinit var foodAdapter: FoodAdapter
    var foodList = ArrayList<FoodInfo>()
    var kcal = 0
    var carbo = 0
    var protein = 0
    var fat = 0
    var sweet = 0
    lateinit var selectedFoodInfo:FoodInfo


    var _binding : UserfoodBinding ?=null
    val binding get() = _binding !!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= UserfoodBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pieChart.setUsePercentValues(true)

        val items = arrayOf("??????", "??????", "??????")
        val myAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)

        binding.bldSpinner.adapter = myAdapter
        //????????? ???????????? ??????
        foodAdapter=FoodAdapter()
        foodAdapter.setItems(foodList)

        //?????????????????? ?????? ??? ??????
        binding.recyclerViewFood.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        //????????? ??????
        binding.recyclerViewFood.adapter = foodAdapter
        showPieChart(0,0,0,0,0)
        if(apiViewModel.loadFoodInfo().food_id==0) // api???????????? ??????????????? ?????????
            foodManageViewModel.inputFoodInfo(apiViewModel.loadFoodInfo()) //????????????????????? ??????

        binding.buttonNavigateToApi.setOnClickListener {//???????????? ?????????
            when {
                binding.foodSearchEditText.text.isNotEmpty() && Pattern.matches("^[0-9a-zA-Z]*$",binding.foodSearchEditText.text) -> {
                    Toast.makeText(requireActivity(),"????????? ???????????? ?????????.",Toast.LENGTH_LONG).show()
                }
                binding.foodSearchEditText.text.isNotEmpty() && Pattern.matches("^[???-???]*$",binding.foodSearchEditText.text)-> { //????????? ??????????????? ?????????
                    apiViewModel.foodName=binding.foodSearchEditText.text.toString() //????????????????????? ????????????????????? ????????????
                    findNavController().navigate(R.id.action_userFoodFragment_to_apiTest) //????????????
                }
                else -> Toast.makeText(requireActivity(),"???????????? ?????? ??????????????????!",Toast.LENGTH_SHORT).show()
            }
        }

        //????????? ????????? ????????? ??? ???????????? ??????
        foodManageViewModel.getFoodAll(calendarViewModel.getDays()).observe(viewLifecycleOwner, Observer {
            if(it != null){
                CoroutineScope(Dispatchers.Main).launch{
                    val c_kcal=async{foodManageViewModel.calculateKcal(calendarViewModel.getDays())}
                    val c_nut=async{foodManageViewModel.calculateNut(calendarViewModel.getDays())}
                    kcal=c_kcal.await()
                    carbo=c_nut.await().food_carbo
                    sweet=c_nut.await().food_sweet
                    protein =c_nut.await().food_protein
                    fat=c_nut.await().food_fat
                    binding.calorieTextView.text = kcal.toString()
                    showPieChart(kcal,carbo,sweet,protein,fat)

                    foodAdapter.setItems(foodManageViewModel.getFoodInfo(calendarViewModel.getDays(),apiViewModel.foodDistinguish))
                    foodAdapter.notifyDataSetChanged()
                }
            }

        })

        foodAdapter.setItemClickListener(object :OnItemClickListener{ //?????????????????? ???????????? ?????????????????? ??????????????? ?????????
            override fun SetOnItemClickListener(v: View, pos: Int) {
                selectedFoodInfo=foodAdapter.getItem(pos)

                binding.buttonFoodDelete.setOnClickListener { //????????????
                    foodManageViewModel.deleteFoodInfo(selectedFoodInfo.food_name,apiViewModel.foodDistinguish,calendarViewModel.getDays())
                }
            }
        })

        //???????????? ????????? ?????? ??????, ??????, ?????? ????????? ?????????????????? ?????????
       binding.bldSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    CoroutineScope(Dispatchers.Main).launch{
                        foodAdapter.setItems(foodManageViewModel.getFoodInfo(calendarViewModel.getDays(),items[position]))
                        foodAdapter.notifyDataSetChanged()
                    }
                    apiViewModel.foodDistinguish=items[position]
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Toast.makeText(requireContext(), "??????,??????,?????? ??? ????????? ????????? ?????????.",Toast.LENGTH_SHORT).show()
                }
            }
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