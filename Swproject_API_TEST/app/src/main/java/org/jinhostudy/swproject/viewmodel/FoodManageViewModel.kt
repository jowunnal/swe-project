package org.jinhostudy.swproject.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jinhostudy.swproject.database.PlannerDatabase
import org.jinhostudy.swproject.database.entity.FoodInfo

class FoodManageViewModel(application: Application) : AndroidViewModel(application) {

    private var db = PlannerDatabase.getInstance(application)
    private var plannerDao = db.plannerDao()


    fun getFoodAll(day : String) = plannerDao.getFoodAll(day)
    fun inputFoodInfo(foodInfo: FoodInfo) = viewModelScope.launch(Dispatchers.IO) { plannerDao.insertFoodInfo(foodInfo) }
    fun getFoodDistinguish(date:String,distinguish:String)=plannerDao.getFoodInfo(date,distinguish)
    fun modifyFoodInfo(foodName:String,
                       foodDistinguish:String,
                       date:String, ) = viewModelScope.launch(Dispatchers.IO) { plannerDao.modifyFoodInfo(
                                                                foodName,
                                                                foodDistinguish,
                                                                date) }

    suspend fun calculateKcal(day: String):Int {
        var kcal=0
        val foodList= withContext(Dispatchers.IO) {  plannerDao.getFood(day)}
        for(food in foodList){
            kcal+=food.food_kcal
        }
        return kcal
    }

    suspend fun calculateNut(day:String):FoodInfo{
        var foodInfo= FoodInfo(0,"",0,0,0,0,0,0,0,"","")
        val foodList=withContext(Dispatchers.IO) {  plannerDao.getFood(day)}
        for(food in foodList){
            foodInfo.food_sodium+=food.food_sodium
            foodInfo.food_sweet+=food.food_sweet
            foodInfo.food_fat+=food.food_fat
            foodInfo.food_protein+=food.food_protein
            foodInfo.food_carbo+=food.food_carbo
        }
        return foodInfo
    }

}