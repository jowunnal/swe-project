package org.jinhostudy.swproject.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WaterInfo(var user_input_mount : Int,var user_today_mount:Int,var user_recom_mount:Int,@PrimaryKey var water_id:Int)
