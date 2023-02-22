package com.bluedragoon.smartwaterviews.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "h03_water_intakes",
    indices = [
        Index(
            value = ["intake_id", "area_id", "house_id"],
            unique = true
        )
    ]
)
data class H03WaterIntakes(

    @PrimaryKey(autoGenerate = true)
    val uid: Long,

    @ColumnInfo(name = "intake_id")
    val intakeId: String,

    @ColumnInfo(name = "intake_name")
    val intakeName: String,

    @ColumnInfo(name = "vfr")
    val vfr: Float,

    @ColumnInfo(name = "area_id")
    val areaId: String,

    @ColumnInfo(name = "area_name")
    val areaName: String,

    @ColumnInfo(name = "house_id")
    val houseId: String,

    @ColumnInfo(name = "is_selected")
    val isSelected: Int = 0

)
