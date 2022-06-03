package org.jinhostudy.swproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import org.jinhostudy.swproject.database.entity.UserInfo
import org.jinhostudy.swproject.databinding.ItemUserinfoBinding
import org.jinhostudy.swproject.listener.OnItemClickListener

class UserAdapter: RecyclerView.Adapter<UserAdapter.ViewHolder>(),OnItemClickListener {
    val list = ArrayList<UserInfo>()
    var listener:OnItemClickListener ?= null
    inner class ViewHolder(private var itemBinding: ItemUserinfoBinding):RecyclerView.ViewHolder(itemBinding.root){
        fun bind(data:UserInfo){

            itemBinding.itemUserage.text=String.format("나이: %s",data.user_age)
            itemBinding.itemUserheight.text=String.format("키: %s",data.user_height)
            itemBinding.itemUserweight.text=String.format("몸무게: %s",data.user_weight)
            itemBinding.itemUserBMI.text= String.format("BMI: %.2f",data.user_weight/(data.user_height*data.user_height/10000))
            itemBinding.root.setOnClickListener {
                SetOnItemClickListener(it,adapterPosition)
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUserinfoBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        lateinit var itemClickListener : AdapterView.OnItemClickListener
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItems(item:List<UserInfo>){
        list.clear()
        list.addAll(item)
    }
    fun setPresentItems(userInfo: UserInfo){

    }
    fun getItem(pos: Int): UserInfo {
        return list[pos]
    }
    fun SetItemClickListener(listener: OnItemClickListener){
        this.listener=listener
    }
    override fun SetOnItemClickListener(v: View, pos: Int) {
        this.listener?.SetOnItemClickListener(v,pos)
    }
}