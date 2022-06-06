package org.jinhostudy.swproject.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FoodInfo(@PrimaryKey(autoGenerate = true) val food_id:Int,
                    val food_name:String,
                    val food_serving_wt:Int,
                    var food_kcal:Int,
                    var food_carbo:Int,
                    var food_protein:Int,
                    var food_fat:Int,
                    var food_sweet:Int,
                    var food_sodium:Int,
                    val food_distinguish:String,
                    val food_date:String)
