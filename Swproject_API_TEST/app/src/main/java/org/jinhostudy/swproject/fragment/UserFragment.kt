package org.jinhostudy.swproject.fragment

import android.content.Context
import android.os.Bundle
import android.text.Selection
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.jinhostudy.swproject.adapter.UserDeleteAdapter
import org.jinhostudy.swproject.database.entity.UserInfo
import org.jinhostudy.swproject.databinding.UserInfoBinding
import org.jinhostudy.swproject.listener.OnItemClickListener
import org.jinhostudy.swproject.viewmodel.UserViewModel

class UserFragment:Fragment() {
    var _binding: UserInfoBinding ?= null
    val binding get() = _binding !!
    lateinit var userViewModel: UserViewModel
    lateinit var userDeleteAdapter: UserDeleteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = UserInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = UserViewModel(requireActivity().application)
        userDeleteAdapter = UserDeleteAdapter()
        userViewModel.showUserInfo().observe(viewLifecycleOwner, Observer {
            userDeleteAdapter.setItems(it)
            userDeleteAdapter.notifyDataSetChanged()
            if(it.isNotEmpty()){
                binding.TextPresentuserage.text = String.format("나이: %s",it.last().user_age)
                binding.TextPresentUserheight.text = String.format("키: %s",it.last().user_height)
                binding.TextPresentUserweight.text = String.format( "몸무게: %s",it.last().user_weight)
                binding.TextPresentUserBMI.text = String.format("BMI: %.2f",it.last().user_weight/(it.last().user_height*it.last().user_height/10000))

            }
        })
        binding.recyclerViewRecord.adapter=userDeleteAdapter
        binding.recyclerViewRecord.layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        userDeleteAdapter.SetItemClickListener(object:OnItemClickListener{
            override fun SetOnItemClickListener(v: View, pos: Int) {
                var deleteuser = userDeleteAdapter.getItem(pos)
                binding.buttonUserDelete.setOnClickListener { userViewModel.deleteUserInfo(deleteuser.user_age, deleteuser.user_height, deleteuser.user_weight) }
            }
        })
        binding.buttonUserUpdate.setOnClickListener {
            if(binding.editTextViewAge.text.isBlank() || binding.editTextViewHeight.text.isBlank() || binding.editTextViewWeight.text.isBlank())
            {
                Toast.makeText(requireActivity(),"나이, 키, 몸무게를 입력해주세요!",Toast.LENGTH_SHORT).show()
            }
            else {
                userViewModel.editUserInfo(
                    UserInfo(
                        0,
                        binding.editTextViewAge.text.toString().toInt(),
                        binding.editTextViewHeight.text.toString().toDouble(),
                        binding.editTextViewWeight.text.toString().toDouble(),
                        ""
                    )
                )
            }
        }

    }

}