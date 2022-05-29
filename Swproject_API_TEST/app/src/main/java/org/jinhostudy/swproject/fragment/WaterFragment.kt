package org.jinhostudy.swproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import org.jinhostudy.swproject.databinding.WaterFragmentBinding
import org.jinhostudy.swproject.viewmodel.*

class WaterFragment : Fragment() {
    var _binding:WaterFragmentBinding ?= null
    val binding get() = _binding!!
    val waterViewModel:WaterViewModel by activityViewModels{WaterViewModelFactory(requireActivity().application)}
    val calendarViewModel:CalendarViewModel by activityViewModels{ CalendarViewModelFactory(requireActivity().application) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= WaterFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarViewModel.day.observe(viewLifecycleOwner, Observer { s -> // 날짜선택 기능에따라 해당날짜의 물목표량 및 섭취량이 갱신
            waterViewModel.getDrinkGoal(s).observe(viewLifecycleOwner, Observer {
                binding.progressBarWaterToday.max=it
                binding.tvWaterGoal.text= it.toString()
            })
            waterViewModel.getDrink(s).observe(viewLifecycleOwner, Observer {
                binding.progressBarWaterToday.progress=it
                binding.tvWaterPresent.text=it.toString()
            })
        })

        binding.tvWaterHeader.text="오늘 물 섭취량"
        binding.buttonWaterAdd.setOnClickListener { //물 섭취량 혹은 목표량 증가버튼
            calendarViewModel.day.observe(viewLifecycleOwner, Observer {
                waterViewModel.plusDrink(it)
            })
        }
        binding.buttonWaterDec.setOnClickListener { // 물 섭취량 혹은 목표량 감소버튼
            calendarViewModel.day.observe(viewLifecycleOwner, Observer {
                waterViewModel.minusDrink(it)
            })
        }


        binding.switch1.setOnCheckedChangeListener { _, p1 -> // 물섭취량 혹은 물목표량 으로 스위치를 통해 변경
            when (p1) {
                true -> {
                    binding.tvWaterHeader.text="오늘 물 목표량"
                    binding.buttonWaterAdd.setOnClickListener {
                        calendarViewModel.day.observe(viewLifecycleOwner, Observer {
                            waterViewModel.plusDrinkGoal(it)
                        })
                    }
                    binding.buttonWaterDec.setOnClickListener {
                        calendarViewModel.day.observe(viewLifecycleOwner, Observer {
                            waterViewModel.minusDrinkGoal(it)
                        })
                    }
                }
                false->{
                    binding.tvWaterHeader.text="오늘 물 섭취량"
                    binding.buttonWaterAdd.setOnClickListener {
                        calendarViewModel.day.observe(viewLifecycleOwner, Observer {
                            waterViewModel.plusDrink(it)
                        })
                    }
                    binding.buttonWaterDec.setOnClickListener {
                        calendarViewModel.day.observe(viewLifecycleOwner, Observer {
                            waterViewModel.minusDrink(it)
                        })
                    }
                }
            }
        }
        val alarmViewModel=AlarmViewModel(requireActivity().application)
        binding.numberPickerAlarmTens.maxValue=9
        binding.numberPickerAlarmTens.minValue=0
        binding.numberPickerAlarmTens.value=0
        binding.numberPickerAlarmUnits.maxValue=9
        binding.numberPickerAlarmUnits.minValue=0
        binding.numberPickerAlarmUnits.value=0
        binding.buttonWaterAlarmAdd.setOnClickListener { //알람설정버튼
            val min=binding.numberPickerAlarmTens.value.toString()+binding.numberPickerAlarmUnits.value.toString()
            alarmViewModel.setAlarm(min.toInt())
        }
        binding.buttonWaterAlarmDelete.setOnClickListener {
            alarmViewModel.clearAlarm()
        }
    }
}