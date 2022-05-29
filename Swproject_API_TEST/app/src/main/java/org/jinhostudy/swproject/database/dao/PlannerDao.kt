package org.jinhostudy.swproject.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import org.jinhostudy.swproject.database.entity.FoodInfo
import org.jinhostudy.swproject.database.entity.WaterInfo

@Dao
interface PlannerDao {
    @Query("select * from FoodInfo")
    suspend fun getFoodInfo():List<FoodInfo>

    @Insert(onConflict = REPLACE)//기본키 같으면 덮어씀
    fun insertFoodInfo(foodInfo: FoodInfo)

    @Query("update waterinfo set user_input_mount=(select user_input_mount from WaterInfo where water_date like :day)+100 where water_date like :day")
    suspend fun plusDrink(day:String)

    @Query("update waterinfo set user_input_mount=(select user_input_mount from WaterInfo where water_date like :day)-100 where water_date like :day")
    suspend fun minusDrink(day:String)

    @Query("update waterinfo set user_today_mount=(select user_today_mount from WaterInfo where water_date like :day)+100 where water_date like :day")
    suspend fun plusDrinkGoal(day:String)

    @Query("update waterinfo set user_today_mount=(select user_today_mount from WaterInfo where water_date like :day)-100 where water_date like :day")
    suspend fun minusDrinkGoal(day:String)

    @Query("select user_input_mount from waterinfo where water_date like :day")
    fun getDrink(day:String): LiveData<Int>

    @Query("select user_today_mount from waterinfo where water_date like :day")
    fun getDrinkGoal(day:String):LiveData<Int>


    //update WaterInfo set water_date=((SELECT datetime('now','localtime'))) where water_id=1 or water_id=2
    //update waterinfo set water_date=(select date('now')) where water_id=1
}