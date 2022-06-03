package org.jinhostudy.swproject.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import org.jinhostudy.swproject.R
import org.jinhostudy.swproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var _binding :ActivityMainBinding ?=null
    val binding get () = _binding!!
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=ActivityMainBinding.inflate(layoutInflater) //view binding
        setContentView(binding.root)

        var NavHostFragment= supportFragmentManager.findFragmentById(R.id.FragmentContainer) as NavHostFragment
        navController = NavHostFragment.navController

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        var v: View ?= currentFocus
        if(v==null){
            v=View(this)
        }
        val input=getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(v.windowToken,0)
        return super.dispatchTouchEvent(ev)
    }
}