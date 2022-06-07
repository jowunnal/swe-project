package org.jinhostudy.swproject.api

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import org.jinhostudy.swproject.adapter.ApiAdapter
import org.jinhostudy.swproject.database.entity.FoodInfo
import org.jinhostudy.swproject.databinding.ApiTestBinding
import org.jinhostudy.swproject.listener.OnItemClickListener
import org.jinhostudy.swproject.viewmodel.*


class ApiFragment : Fragment() {
    val calendarViewModel: CalendarViewModel by activityViewModels{ CalendarViewModelFactory(requireActivity().application) }
    val apiViewModel:ApiViewModel by activityViewModels{ApiViewModelFactory(requireActivity().application)}
    var _binding: ApiTestBinding? = null
    val binding get() = _binding!!
    private val apiAdapter by lazy { ApiAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ApiTestBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewFoodList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = apiAdapter
        }
        binding.textviewFoodName.text = apiViewModel.foodName
        val service = MyRetrofit.iRetrofit
        CoroutineScope(Dispatchers.Main).launch {
            val response=apiViewModel.requestAPI(service)
            if (response.isSuccessful) { // 응답이 성공적으로 받아졋으면
                val result = response.body()?.body?.items // items참조해서
                result?.let {
                    if(it.isEmpty()){
                        Toast.makeText(requireActivity(),"해당 음식명으로 검색된 음식이 없습니다.",Toast.LENGTH_LONG).show()
                    }
                    else {
                        apiAdapter.setItems(it) //adapter에 넘겨서 리사이클러뷰에 보여주기
                        apiAdapter.notifyDataSetChanged()
                    }
                }
            } else { //응답이 실패하면 오류문구 출력
                Log.e("TAG", "ERROR" + response.code().toString())
            }
        }

        //DB Insert
        apiAdapter.setItemClickListener(object : OnItemClickListener{
            override fun SetOnItemClickListener(v: View, pos: Int) {
                val item=apiAdapter.getItem(pos)
                val foodName = item.dESCKOR.toString()
                val food_serving_wt = item.sERVINGWT.toString().replace("N/A","0").toInt()
                val food_kcal = item.nUTRCONT1.toString().replace("N/A","0").toDouble().toInt()
                val food_carbo = item.nUTRCONT2.toString().replace("N/A","0").toDouble().toInt()
                val food_protein = item.nUTRCONT3.toString().replace("N/A","0").toDouble().toInt()
                val food_fat = item.nUTRCONT4.toString().replace("N/A","0").toDouble().toInt()
                val food_sweet = item.nUTRCONT5.toString().replace("N/A","0").toDouble().toInt()
                val food_sodium = item.nUTRCONT6.toString().replace("N/A","0").toDouble().toInt()
                val food_date = calendarViewModel.getDays()

                apiViewModel.saveFoodInfo(FoodInfo(0,foodName,
                    food_serving_wt,
                    food_kcal,
                    food_carbo,
                    food_protein,
                    food_fat,food_sweet,food_sodium,apiViewModel.foodDistinguish,food_date))

                findNavController().popBackStack()
            }
        })
    }
}