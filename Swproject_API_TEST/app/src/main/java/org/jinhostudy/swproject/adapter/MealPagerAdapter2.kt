package org.jinhostudy.swproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.jinhostudy.swproject.data.Meal
import org.jinhostudy.swproject.R

class MealPagerAdapter2(
   private val meals: List<Meal>
): RecyclerView.Adapter<MealPagerAdapter2.MealViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MealViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_userfood, parent, false)

        )

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    override fun getItemCount() = meals.size

    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mealTextView: TextView = itemView.findViewById(R.id.mealTextView)
        private val menuTextView: TextView = itemView.findViewById(R.id.menuTextView)

        fun bind(meal: Meal){
            mealTextView.text = meal.bld
            menuTextView.text = meal.bldMenu
        }
    }
}