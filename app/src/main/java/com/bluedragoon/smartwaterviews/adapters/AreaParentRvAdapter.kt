package com.bluedragoon.smartwaterviews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluedragoon.smartwaterviews.R
import com.bluedragoon.smartwaterviews.data.entities.H03WaterIntakes
import com.bluedragoon.smartwaterviews.databinding.AreaCardItemBinding
import com.bluedragoon.smartwaterviews.models.AreaToWaterIntakes

class AreaParentRvAdapter() : RecyclerView.Adapter<AreaParentRvAdapter.AreaParentViewHolder>() {

    private var areaToWaterIntakesList = listOf<AreaToWaterIntakes>()

    inner class AreaParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val binding = AreaCardItemBinding.bind(itemView)

        val areaNameTv: TextView = binding.tvAreaNameParentItem
        val childrenWaterIntakesRv: RecyclerView = binding.rvChildrenWaterIntakes

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaParentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.area_card_item,
            parent,
            false
        )

        return AreaParentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AreaParentViewHolder, position: Int) {
        val currentItem = areaToWaterIntakesList[position]

        holder.areaNameTv.text = currentItem.parentArea.areaName

        val childrenWaterIntakesAdapter =
            WaterIntakeChildRvAdapter(currentItem.childrenWaterIntakes)
        holder.childrenWaterIntakesRv.layoutManager = LinearLayoutManager(
            holder.itemView.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        holder.childrenWaterIntakesRv.adapter = childrenWaterIntakesAdapter

        childrenWaterIntakesAdapter.setWaterIntakeClickListener {
            onAreaClickListener?.let { click ->
                click(it)

            }
        }

    }

    override fun getItemCount(): Int = areaToWaterIntakesList.size

    fun setAreaToWaterIntakes(areaToWaterIntakesList: List<AreaToWaterIntakes>){
        this.areaToWaterIntakesList = areaToWaterIntakesList
        notifyDataSetChanged()
    }

    private var onAreaClickListener: ((H03WaterIntakes) -> Unit)? = null

    fun setAreaClickListener(listener: (H03WaterIntakes) -> Unit) {
        onAreaClickListener = listener
    }

}