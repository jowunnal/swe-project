package org.jinhostudy.swproject.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import org.jinhostudy.swproject.database.entity.FoodInfo
import org.jinhostudy.swproject.database.entity.WaterInfo

@Dao
interface PlannerDao {
    @Query("select * from FoodInfo")
    suspend fun getFoodInfo():List<FoodInfo>

    @Query("update waterinfo set user_input_mount=(select user_input_mount from WaterInfo where water_date like (select date('now')))+100 where water_date like (select date('now'))")
    suspend fun plusDrink()

    @Query("update waterinfo set user_input_mount=(select user_input_mount from WaterInfo where water_date like (select date('now')))-100 where water_date like (select date('now'))")
    suspend fun minusDrink()

    @Query("update waterinfo set user_today_mount=(select user_today_mount from WaterInfo where water_date like (select date('now')))+100 where water_date like (select date('now'))")
    suspend fun plusDrinkGoal()

    @Query("update waterinfo set user_today_mount=(select user_today_mount from WaterInfo where water_date like (select date('now')))-100 where water_date like (select date('now'))")
    suspend fun minusDrinkGoal()

    @Query("select user_input_mount from waterinfo where water_date like (select date('now'))")
    fun getDrink(): LiveData<Int>

    @Query("select user_today_mount from waterinfo where water_date like (select date('now'))")
    fun getDrinkGoal():LiveData<Int>

    @Query("insert into waterinfo(user_input_mount,user_today_mount,user_recom_mount,water_date) values (0,0,2000,(select date('now')))")
    suspend fun insertDays()

    @Query("select * from waterinfo where water_date like (select date('now'))")
    suspend fun checkDays():WaterInfo

    //update WaterInfo set water_date=((SELECT datetime('now','localtime'))) where water_id=1 or water_id=2
    //update waterinfo set water_date=(select date('now')) where water_id=1
}