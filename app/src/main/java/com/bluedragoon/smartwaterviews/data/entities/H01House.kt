package com.bluedragoon.smartwaterviews.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "h01_house",
    indices = [
        Index(
            value = ["house_id", "admin_user_id"],
            unique = true
        )
    ]
)
data class H01House(

    @PrimaryKey(autoGenerate = true)
    val uid: Long,

    @ColumnInfo(name = "house_id")
    val houseId: String,

    @ColumnInfo(name = "house_name")
    val houseName: String,

    @ColumnInfo(name = "admin_user_id")
    val adminUserId: String

)
