package org.jinhostudy.swproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import org.jinhostudy.swproject.api.Item
import org.jinhostudy.swproject.databinding.ItemLayoutBinding
import kotlin.coroutines.coroutineContext

class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var items=ArrayList<Item>()
    class ViewHolder(private val binding:ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item){
            binding.textView.text="이름: "+item.dESCKOR
            binding.textView2.text="1회 제공량: "+item.sERVINGWT
            binding.textView3.text="열량: "+item.nUTRCONT1.toString()
            binding.textView4.text="탄수화물: "+item.nUTRCONT2.toString()
            binding.textView5.text="단백질: "+item.nUTRCONT3.toString()
            binding.textView6.text="지방: "+item.nUTRCONT4.toString()
            binding.textView7.text="당류: "+item.nUTRCONT5.toString()
            binding.textView8.text="나트륨: "+item.nUTRCONT6.toString()
            binding.textView12.text="구축년도: "+item.bGNYEAR
            binding.textView13.text="가공업체: "+item.aNIMALPLANT

        }
    }
    fun setItems(items:List<Item>){
        this.items.clear()
        this.items.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {

        }
    }
}