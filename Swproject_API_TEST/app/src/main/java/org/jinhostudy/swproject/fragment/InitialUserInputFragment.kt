package org.jinhostudy.swproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.jinhostudy.swproject.R
import org.jinhostudy.swproject.databinding.InitialuserinputBinding
import org.jinhostudy.swproject.viewmodel.InitalUserViewModel

class InitialUserInputFragment :Fragment() {
    var _binding : InitialuserinputBinding?=null
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
            initalUserViewModel.inputUserFirstData(1,binding.userInputAge.text.toString().toInt(),binding.userInputHeight.text.toString().toInt(),binding.userInputWeight.text.toString().toInt())
            findNavController().popBackStack()
        }
    }
}