package org.jinhostudy.swproject.api

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jinhostudy.swproject.adapter.MyAdapter
import org.jinhostudy.swproject.database.PlannerDatabase
import org.jinhostudy.swproject.database.dao.PlannerDao
import org.jinhostudy.swproject.database.entity.FoodInfo
import org.jinhostudy.swproject.databinding.ApiTestBinding
import org.jinhostudy.swproject.fragment.FoodFragment


class ApiFragment : Fragment() {


    var _binding: ApiTestBinding? = null
    val binding get() = _binding!!
    private val myAdapter by lazy { MyAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ApiTestBinding.inflate(inflater, container, false)
        return binding.root
    }
    lateinit var db : PlannerDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = PlannerDatabase.getInstance(requireContext())

        //여기서 부터 DB Insert
        var food = FoodInfo()

        fun insertFoIn(){

        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = myAdapter
        }

        binding.button.setOnClickListener {

            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                binding.editTextTextPersonName.windowToken,
                0
            ) //버튼눌려지면 edittext로열린 키패드닫기
            val service = MyRetrofit.iRetrofit
            val text =
                binding.editTextTextPersonName.text.toString() //버튼이눌려졋을때 텍스트뷰에있는문자 받아서 api에요청해서 같은이름의 식품만가져오기

            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getData(
                    "glwquL+djUJqzmtsCEnu1fnleZGUti9NAlVWTpONdIBdupJ054tojg6azBWVmp8KOiGZHUwlRSv3Af5hCKVudw==",
                    text, "json"
                ) // 키값넣어서 api 요청해서 응답받아 변수에할당하고
                async(Dispatchers.Main) {// UI작업을위해 main 스레드로
                    if (response.isSuccessful) { // 응답이 성공적으로 받아졋으면
                        val result = response.body()?.body?.items // items참조해서
                        result?.let {
                            myAdapter.setItems(it) //adapter에 넘겨서 리사이클러뷰에 보여주기
                            myAdapter.notifyDataSetChanged()
                        }
                    } else { //응답이 실패하면 오류문구 출력
                        Log.e("TAG", "ERROR" + response.code().toString())
                    }
                }
            }
        }



    }

}