package org.jinhostudy.swproject.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import org.jinhostudy.swproject.R
import org.jinhostudy.swproject.data.Meal
import org.jinhostudy.swproject.adapter.MealPagerAdapter2
import org.jinhostudy.swproject.api.ApiFragment
import org.jinhostudy.swproject.databinding.UserfoodBinding

class FoodFragment : Fragment() {


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

        val items = arrayOf("아침", "점심", "저녁")
        val myAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)

        binding.bldSpinner.adapter = myAdapter

        binding.mealViewPager2.adapter = MealPagerAdapter2(
            listOf(
                Meal("아침",
                    "김치찌개"),
                Meal("점심",
                    "짜장면"),
                Meal("저녁",
                    "치킨")
            )
        )

        binding.searchFoodAppCompatButton.setOnClickListener {
            findNavController().navigate(R.id.action_userFoodFragment_to_apiTest)
        }



//        binding.bldSpinner.onItemSelectedListener =
//            object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
//
//
//                    //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
//                    when (position) {
//                        0 -> {
//                            binding.searchFoodAppCompatButton.setOnClickListener {
//
//
////                            mealViewPager2.adapter = MealPagerAdapter2(
////                                listOf(
////                                    Meal(items[0], ""))
////
////                            )
//                            }
//                        }
//                        1 -> {
//                            binding.searchFoodAppCompatButton.setOnClickListener {
////                            mealViewPager2.adapter = MealPagerAdapter2(
////                                listOf(
////                                    Meal(items[1], ""))
////
////                            )
//                            }
//                        }
//
//                        else -> {
//                            binding.searchFoodAppCompatButton.setOnClickListener {
////                            mealViewPager2.adapter = MealPagerAdapter2(
////                                listOf(
////                                    Meal(items[2], ""))
////
////                            )
//                            }
//                        }
//                    }
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    TODO("Not yet implemented")
//                }
//            }


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
    }

}