package org.jinhostudy.swproject.database

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PlannerApplication:Application() {
    val aplicationScope= CoroutineScope(SupervisorJob())
    val database by lazy{PlannerDatabase.getInstance(this)}
    val repository by lazy { PlannerRepository(database.plannerDao()) }
}