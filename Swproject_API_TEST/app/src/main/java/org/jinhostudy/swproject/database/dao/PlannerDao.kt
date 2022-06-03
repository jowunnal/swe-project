package org.jinhostudy.swproject.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.jinhostudy.swproject.database.entity.FoodInfo
import org.jinhostudy.swproject.database.entity.UserInfo
import org.jinhostudy.swproject.database.entity.WaterInfo

@Dao
interface PlannerDao {

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

    @Query("select * from waterinfo where water_date between :day1 and :day2")
    fun getDrinkAmongDays(day1:String,day2:String):MutableList<WaterInfo>

    @Query("select * from UserInfo")
    fun showUserInfo():LiveData<List<UserInfo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserInfo(userInfo: UserInfo)

    @Query("delete from UserInfo where user_age = :Selectage and user_height = :Selectheight and user_weight = :Selectweight")
    fun deleteUserInfo(Selectage :Int, Selectheight :Double, Selectweight :Double)

    @Query("insert into UserInfo(user_age,user_height,user_weight,user_date) values (:age,:height,:weight,:date) ")
    suspend fun inputFirstUserData(age:Int,height:Int,weight:Int,date:String)

    //@Insert(onConflict = OnConflictStrategy.IGNORE)
    //suspend fun setWater(waterInfo: ArrayList<WaterInfo>)
    @Query("insert into waterinfo(user_input_mount,user_today_mount,user_recom_mount,water_date) values (:input,:today,:recom,:date)")
    suspend fun setWater(input:Int,today:Int,recom:Int,date:String)

    //update WaterInfo set water_date=((SELECT datetime('now','localtime'))) where water_id=1 or water_id=2
    //update waterinfo set water_date=(select date('now')) where water_id=1
}