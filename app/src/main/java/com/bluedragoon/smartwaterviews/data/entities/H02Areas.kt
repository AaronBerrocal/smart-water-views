package com.bluedragoon.smartwaterviews.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "h02_areas",
    indices = [
        Index(
            value = ["area_id", "house_id"],
            unique = true
        )
    ]
)
data class H02Areas(

    @PrimaryKey(autoGenerate = true)
    val uid: Long,

    @ColumnInfo(name = "area_id")
    val areaId: String,

    @ColumnInfo(name = "area_name")
    val areaName: String,

    @ColumnInfo(name = "house_id")
    val houseId: String

)
