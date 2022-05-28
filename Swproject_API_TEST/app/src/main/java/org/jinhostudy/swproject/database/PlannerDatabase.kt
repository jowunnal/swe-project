package org.jinhostudy.swproject.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.jinhostudy.swproject.database.dao.PlannerDao
import org.jinhostudy.swproject.database.entity.FoodInfo
import org.jinhostudy.swproject.database.entity.UserInfo
import org.jinhostudy.swproject.database.entity.WaterInfo

@Database(entities = [FoodInfo::class, UserInfo::class, WaterInfo::class], version =1, exportSchema = true)
abstract class PlannerDatabase : RoomDatabase() {
    abstract fun plannerDao(): PlannerDao

    companion object{
        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }
        @Volatile
        private var DatabaseInstance:PlannerDatabase ?=null
        fun getInstance(context: Context): PlannerDatabase {
            return DatabaseInstance?: synchronized(PlannerDatabase::class){
                val instance=Room.databaseBuilder(context.applicationContext,PlannerDatabase::class.java,"Planner_Database").createFromAsset("database/Food_Planner.db").build()
                DatabaseInstance=instance
                instance
            }
        }
    }
}