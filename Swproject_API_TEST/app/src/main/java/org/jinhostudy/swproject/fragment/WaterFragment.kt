package org.jinhostudy.swproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import org.jinhostudy.swproject.databinding.WaterFragmentBinding
import org.jinhostudy.swproject.viewmodel.CalendarViewModel
import org.jinhostudy.swproject.viewmodel.CalendarViewModelFactory
import org.jinhostudy.swproject.viewmodel.WaterViewModel
import org.jinhostudy.swproject.viewmodel.WaterViewModelFactory

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

        calendarViewModel.day.observe(viewLifecycleOwner, Observer { s ->
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


        binding.switch1.setOnCheckedChangeListener { p0, p1 ->
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
    }
}