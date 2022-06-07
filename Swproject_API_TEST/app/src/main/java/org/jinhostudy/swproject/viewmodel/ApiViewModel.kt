package org.jinhostudy.swproject.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.*
import org.jinhostudy.swproject.api.FoodDTO
import org.jinhostudy.swproject.api.IRetrofit
import org.jinhostudy.swproject.api.Item
import org.jinhostudy.swproject.api.MyRetrofit

import org.jinhostudy.swproject.database.PlannerDatabase
import org.jinhostudy.swproject.database.entity.FoodInfo
import retrofit2.Response

class ApiViewModel(application: Application) : AndroidViewModel(application) {
    var foodDistinguish = "아침"
    var foodName=""
    var foodInfo=FoodInfo(1,"",0,0,0,0,0,0,0,"","")

    //spinner 선택시 아침,점심,저녁 구별
    fun saveFoodInfo(foodInfo: FoodInfo){
        this.foodInfo=foodInfo
    }
    fun loadFoodInfo():FoodInfo{
        return foodInfo
    }

    suspend fun requestAPI(service: IRetrofit): Response<FoodDTO> {
        val response = viewModelScope.async {
            service.getData(
                "glwquL+djUJqzmtsCEnu1fnleZGUti9NAlVWTpONdIBdupJ054tojg6azBWVmp8KOiGZHUwlRSv3Af5hCKVudw==",
                foodName, "json"
            ) // 키값넣어서 api 요청해서 응답받아 변수에할당하고
        }
        return response.await()
    }
}

class ApiViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ApiViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ApiViewModel(application) as T
        }
        throw IllegalArgumentException("unknown Viewmodel class")
    }
}

