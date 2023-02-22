package com.bluedragoon.smartwaterviews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bluedragoon.smartwaterviews.R
import com.bluedragoon.smartwaterviews.databinding.SessionCardItemBinding
import com.bluedragoon.smartwaterviews.models.SessionLogModel
import com.bluedragoon.smartwaterviews.utilities.SESSION_LOG_DATE_TIME_PATTERN
import com.bluedragoon.smartwaterviews.utilities.SESSION_LOG_DATE_TIME_PATTERN_NO_YEAR
import com.bluedragoon.smartwaterviews.utilities.formatToString
import java.time.LocalDateTime

class SessionsLogRvAdapter() : RecyclerView.Adapter<SessionsLogRvAdapter.SessionsLogViewHolder>() {

    private var sessionsList = listOf<SessionLogModel>()

    inner class SessionsLogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = SessionCardItemBinding.bind(itemView)

        val sessionConsumptionTv: TextView = binding.tvSessionConsumptionValue
        val sessionDurationTv: TextView = binding.tvSessionDurationValue
        val waterIntakeNameTv: TextView = binding.tvSessionWiName
        val areaNameTv: TextView = binding.tvSessionAreaName
        val userNameTv: TextView = binding.tvSessionUserName
        val vfrValueTv: TextView = binding.tvSessionVfrValue
        val sessionTimestampTv: TextView = binding.tvSessionTimestampValue

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionsLogViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.session_card_item,
            parent,
            false
        )

        return SessionsLogViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SessionsLogViewHolder, position: Int) {
        val currentItem = sessionsList[position]

        holder.sessionConsumptionTv.text = String.format("%.2f Lts", currentItem.consumption)
        holder.sessionDurationTv.text = String.format(
            "%d m:%02d s",
            currentItem.duration / 60,
            currentItem.duration % 60
        )
        holder.waterIntakeNameTv.text = currentItem.intakeName
        holder.areaNameTv.text = currentItem.areaName
        holder.userNameTv.text = currentItem.userFullName
        holder.vfrValueTv.text = String.format("%.2f lts/s", currentItem.vfr)
        holder.sessionTimestampTv.text = currentItem.sessionTimestamp.formatToString(
            if (currentItem.sessionTimestamp.year != LocalDateTime.now().year) {
                SESSION_LOG_DATE_TIME_PATTERN
            } else {
                SESSION_LOG_DATE_TIME_PATTERN_NO_YEAR
            }
        )

    }

    override fun getItemCount(): Int = sessionsList.size

    fun setSessionsLog(sessions: List<SessionLogModel>) {
        this.sessionsList = sessions
        notifyDataSetChanged()
    }

}