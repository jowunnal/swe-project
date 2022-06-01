package org.jinhostudy.swproject.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserInfo(@PrimaryKey(autoGenerate = true) val user_id:Int, val user_age:Int, val user_height:Double, val user_weight:Double,val user_date:String)

