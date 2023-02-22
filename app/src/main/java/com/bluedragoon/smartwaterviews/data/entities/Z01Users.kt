package com.bluedragoon.smartwaterviews.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "z01_users",
    indices = [
        Index(
            value = ["user_id"],
            unique = true
        )
    ]
)
data class Z01Users(

    @PrimaryKey(autoGenerate = true)
    val uid: Long,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "user_name")
    val userName: String,

    @ColumnInfo(name = "user_surname")
    val userSurname: String,

    @ColumnInfo(name = "user_type")
    val userType: Int,

    @ColumnInfo(name = "house_id")
    val houseId: String

)
