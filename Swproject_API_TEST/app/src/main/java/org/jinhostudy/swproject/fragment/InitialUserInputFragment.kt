package org.jinhostudy.swproject.fragment

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.jinhostudy.swproject.R
import org.jinhostudy.swproject.databinding.InitialuserinputBinding
import org.jinhostudy.swproject.viewmodel.InitalUserViewModel
import java.text.SimpleDateFormat
import java.util.*

class InitialUserInputFragment :Fragment() {
    private var _binding : InitialuserinputBinding?=null
    val binding get() = _binding!!
    lateinit var initalUserViewModel: InitalUserViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= InitialuserinputBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initalUserViewModel= InitalUserViewModel(requireActivity().application)
        binding.buttonUserstore.setOnClickListener{

            if(binding.userInputAge.text.toString().isEmpty()){
                binding.userInputAge.setText("0")
            }
            if(binding.userInputHeight.text.toString().isEmpty()){
                binding.userInputHeight.setText("0")
            }
            if(binding.userInputWeight.text.toString().isEmpty()){
                binding.userInputWeight.setText("0")
            }

            initalUserViewModel.inputUserFirstData(binding.userInputAge.text.toString().toInt(),binding.userInputHeight.text.toString().toInt(),binding.userInputWeight.text.toString().toInt(),SimpleDateFormat("yyyy-MM-dd").format(
                Date(System.currentTimeMillis())))
            findNavController().popBackStack()
        }
    }
}