package org.jinhostudy.swproject.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserInfo(@PrimaryKey val user_id:String, val user_name:String, val user_age:Int, val user_height:Double, val user_weight:Double, val user_sex:String,val user_date:String)
