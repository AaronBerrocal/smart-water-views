package com.bluedragoon.smartwaterviews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bluedragoon.smartwaterviews.R
import com.bluedragoon.smartwaterviews.data.entities.H03WaterIntakes
import com.bluedragoon.smartwaterviews.databinding.WaterIntakeCardItemBinding

class WaterIntakeChildRvAdapter(
    private var waterIntakesData: List<H03WaterIntakes>
    ) : RecyclerView.Adapter<WaterIntakeChildRvAdapter.WaterIntakeChildViewHolder>() {

    private var waterIntakesList = listOf<H03WaterIntakes>()

    init {
        this.waterIntakesList = waterIntakesData
    }

    inner class WaterIntakeChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val binding = WaterIntakeCardItemBinding.bind(itemView)

        val waterIntakeNameTv: TextView = binding.tvCardWaterIntakeName
        val waterIntakeVfrTv: TextView = binding.tvCardWaterIntakeVfr
        val waterIntakeIconIv: ImageView = binding.ivCardWaterIntakeIcon

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaterIntakeChildViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.water_intake_card_item,
            parent,
            false
        )

        return WaterIntakeChildViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WaterIntakeChildViewHolder, position: Int) {
        val currentItem = waterIntakesList[position]

        holder.waterIntakeNameTv.text = currentItem.intakeName
        holder.waterIntakeVfrTv.text = String.format("%.2f lts/s", currentItem.vfr)
        holder.waterIntakeIconIv.setImageResource( if(currentItem.isSelected == 1){
            R.drawable.ic_baseline_star_24
        }else{
            R.drawable.ic_baseline_star_border_24
        })

        holder.itemView.setOnClickListener {
            onWaterIntakeClickListener?.let{ click ->
                click(currentItem)

            }
        }

    }

    override fun getItemCount(): Int = waterIntakesList.size

    private var onWaterIntakeClickListener: ((H03WaterIntakes) -> Unit)? = null

    fun setWaterIntakeClickListener(listener: (H03WaterIntakes) -> Unit) {
        onWaterIntakeClickListener = listener
    }

}