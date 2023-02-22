package com.bluedragoon.smartwaterviews.models

import androidx.room.ColumnInfo
import java.time.LocalDateTime

data class SessionLogModel(

    @ColumnInfo(name = "intake_name")
    val intakeName: String?, //H03

    @ColumnInfo(name = "area_name")
    val areaName: String?, //H02

    @ColumnInfo(name = "house_name")
    val houseName: String?, //String

    @ColumnInfo(name = "user_full_name")
    val userFullName: String?, //Z01

    @ColumnInfo(name = "vfr")
    val vfr: Float, //M01

    @ColumnInfo(name = "duration")
    val duration: Int, //M01

    @ColumnInfo(name = "consumption")
    val consumption: Float, //M01

    @ColumnInfo(name = "session_timestamp")
    val sessionTimestamp: LocalDateTime //M01

)
