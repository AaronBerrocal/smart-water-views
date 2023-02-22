package com.bluedragoon.smartwaterviews.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bluedragoon.smartwaterviews.data.entities.H02Areas

class AreasArrayAdapter(
    private val mContext: Context,
    private var objectsArrayList: ArrayList<H02Areas>
): ArrayAdapter<H02Areas?>(mContext, 0, objectsArrayList as List<H02Areas>) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return this.createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return this.createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(mContext).inflate(
            android.R.layout.simple_spinner_dropdown_item,
            parent,
            false
        )

        val area: H02Areas? = getItem(position)
        if(area != null){
            val tvSignature = view.findViewById<TextView>(android.R.id.text1)
            tvSignature?.text = area.areaName
        }

        return view
    }

    fun setAreas(areasList: ArrayList<H02Areas>){
        clear()
        addAll(areasList)
        notifyDataSetChanged()
    }

    fun getIndex(itemId: String): Int{
        var idx = 0
        for(i in 0 until count){
            if((getItem(i) as H02Areas).areaId == itemId){
                idx = i
                break
            }
        }

        return idx
    }

}