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

        val items = arrayOf("아침", "점심", "저녁")
        val myAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)


        binding.bldSpinner.adapter = myAdapter

        //어압터 인스턴스 생성
        foodAdapter=FoodAdapter()
        foodAdapter.setItems(foodList)

        //리사이클러뷰 방향 등 설정
        binding.recyclerViewFood.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        //어답터 장착
        binding.recyclerViewFood.adapter = foodAdapter
        showPieChart(0,0,0,0,0)
        if(apiViewModel.loadFoodInfo().food_id==0) // api응답받은 음식정보가 잇다면
            foodManageViewModel.inputFoodInfo(apiViewModel.loadFoodInfo()) //데이터베이스에 삽입

        binding.searchFoodAppCompatButton.setOnClickListener {//검색버튼 클릭시
            when {
                binding.foodSearchEditText.text.isNotEmpty() && Pattern.matches("^[0-9a-zA-Z]*$",binding.foodSearchEditText.text) -> {
                    Toast.makeText(requireActivity(),"한글만 사용가능 합니다.",Toast.LENGTH_LONG).show()
                }
                binding.foodSearchEditText.text.isNotEmpty() && Pattern.matches("^[ㄱ-힣]*$",binding.foodSearchEditText.text)-> { //입력된 음식이름이 있다면
                    apiViewModel.loadFood(binding.foodSearchEditText.text.toString()) //해당음식이름을 컨트롤클래스에 가져가고
                    findNavController().navigate(R.id.action_userFoodFragment_to_apiTest) //화면전환
                }
                else -> Toast.makeText(requireActivity(),"음식명을 먼저 입력해주세요!",Toast.LENGTH_SHORT).show()
            }
        }

        // 식단구분(livedata) 에 따라 해당식단구분과 날짜에 따른 음식정보를 가져옴
        apiViewModel.livedata.observe(viewLifecycleOwner, Observer { s ->
            foodManageViewModel.getFoodDistinguish(calendarViewModel.getDays(),s).observe(viewLifecycleOwner,
                Observer {
                    foodAdapter.setItems(it)
                    foodAdapter.notifyDataSetChanged()
                })
        })

        //데이터 변동시 칼로리 와 영양성분 계산
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
                }
            }

        })

        foodAdapter.setItemClickListener(object :OnItemClickListener{ //선택된항목을 가져오는 리사이클러뷰 클릭리스너 구현체
            override fun SetOnItemClickListener(v: View, pos: Int) {
                selectedFoodInfo=foodAdapter.getItem(pos)
            }
        })
        binding.buttonFoodDelete.setOnClickListener { //삭제버튼
            foodManageViewModel.modifyFoodInfo(selectedFoodInfo.food_name,apiViewModel.livedata.value!!,calendarViewModel.getDays())
        }

        //스피너의 선택에 따라 아침, 점심, 저녁 에따른 식단구분값을 변경경
       binding.bldSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    apiViewModel.modifyDistinguish(items[position])
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Toast.makeText(requireContext(), "아침,점심,저녁 중 하나를 선택해 주세요.",Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun showPieChart(protein:Int,carbo:Int,fat:Int,sodium:Int,sweet:Int){
        binding.pieChart.setUsePercentValues(true)
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(protein.toFloat(), "단백질"))
        entries.add(PieEntry(carbo.toFloat(), "탄수화물"))
        entries.add(PieEntry(fat.toFloat(), "지방"))
        entries.add(PieEntry(sodium.toFloat(), "나트륨"))
        entries.add(PieEntry(sweet.toFloat(), "당류"))
        //아이템별 범위의 색
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