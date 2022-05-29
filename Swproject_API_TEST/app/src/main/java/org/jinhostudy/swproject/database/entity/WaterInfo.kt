package org.jinhostudy.swproject.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WaterInfo(val user_input_mount : Int,val user_today_mount:Int,val user_recom_mount:Int,@PrimaryKey val water_id:Int,val water_date:String)
