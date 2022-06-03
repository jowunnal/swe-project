package org.jinhostudy.swproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.jinhostudy.swproject.adapter.UserAdapter
import org.jinhostudy.swproject.database.entity.UserInfo
import org.jinhostudy.swproject.databinding.UserInfoBinding
import org.jinhostudy.swproject.listener.OnItemClickListener
import org.jinhostudy.swproject.viewmodel.UserViewModel

class UserFragment:Fragment() {
    var _binding: UserInfoBinding ?= null
    val binding get() = _binding !!
    lateinit var userViewModel: UserViewModel
    lateinit var userAdapter: UserAdapter

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
        userAdapter = UserAdapter()
        userViewModel.showUserInfo().observe(viewLifecycleOwner, Observer {
            userAdapter.setItems(it)
            userAdapter.notifyDataSetChanged()
            if(it.isNotEmpty()){
                binding.TextPresentuserage.text = String.format("나이: %s",it.last().user_age)
                binding.TextPresentUserheight.text = String.format("키: %s",it.last().user_height)
                binding.TextPresentUserweight.text = String.format( "몸무게: %s",it.last().user_weight)
                binding.TextPresentUserBMI.text = String.format("BMI: %.2f",it.last().user_weight/(it.last().user_height*it.last().user_height/10000))

            }
        })
        binding.recyclerViewRecord.adapter=userAdapter
        binding.recyclerViewRecord.layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        userAdapter.SetItemClickListener(object:OnItemClickListener{
            override fun SetOnItemClickListener(v: View, pos: Int) {
                var deleteuser = userAdapter.getItem(pos)
                binding.buttonUserDelete.setOnClickListener { userViewModel.deleteUserInfo(deleteuser.user_age, deleteuser.user_height, deleteuser.user_weight) }
            }
        })
        binding.buttonUserUpdate.setOnClickListener {
            if(binding.editTextViewAge.text.isBlank() || binding.editTextViewHeight.text.isBlank() || binding.editTextViewWeight.text.isBlank())
            {
                Toast.makeText(requireActivity(),"사용자 정보가 입력되지 않았습니다.",Toast.LENGTH_SHORT).show()
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