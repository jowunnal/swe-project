package org.jinhostudy.swproject.database.dao

import androidx.room.Dao
import androidx.room.Query
import org.jinhostudy.swproject.database.entity.FoodInfo

@Dao
interface PlannerDao {
    @Query("select * from FoodInfo")
    suspend fun getFoodInfo():List<FoodInfo>
}