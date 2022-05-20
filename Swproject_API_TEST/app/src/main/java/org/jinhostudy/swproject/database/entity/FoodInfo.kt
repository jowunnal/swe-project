package org.jinhostudy.swproject.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FoodInfo(@PrimaryKey val food_name:String, val food_serving_wt:Int, val food_kcal:Int, val food_carbo:Int, val food_protein:Int, val food_fat:Int, val food_sweet:Int, val food_sodium:Int)
