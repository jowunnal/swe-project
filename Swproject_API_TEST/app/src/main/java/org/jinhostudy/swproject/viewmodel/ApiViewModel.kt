package org.jinhostudy.swproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import org.jinhostudy.swproject.database.PlannerDatabase
import org.jinhostudy.swproject.database.dao.PlannerDao

class ApiViewModel(application: Application) : AndroidViewModel(application) {
    private var db = PlannerDatabase.getInstance(application)
    private var plannerDao:PlannerDao=db!!.plannerDao()


}

