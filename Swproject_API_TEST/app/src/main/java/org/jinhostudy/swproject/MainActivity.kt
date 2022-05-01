package org.jinhostudy.swproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.*
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
}