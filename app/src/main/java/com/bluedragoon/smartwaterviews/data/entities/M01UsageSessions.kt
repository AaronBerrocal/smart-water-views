package com.bluedragoon.smartwaterviews.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "m01_usage_sessions",
    indices = [
        Index(
            value = ["session_id", "intake_id", "area_id", "house_id", "user_id", "session_timestamp" ],
            unique = true
        )
    ]
)
data class M01UsageSessions(

    @PrimaryKey(autoGenerate = true)
    val uid: Long,

    @ColumnInfo(name = "session_id")
    val sessionId: String,

    @ColumnInfo(name = "intake_id")
    val intakeId: String,

    @ColumnInfo(name = "area_id")
    val areaId: String,

    @ColumnInfo(name = "house_id")
    val houseId: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "vfr")
    val vfr: Float,

    @ColumnInfo(name = "duration")
    val duration: Int,

    @ColumnInfo(name = "consumption")
    val consumption: Float,

    @ColumnInfo(name = "session_timestamp")
    val sessionTimestamp: LocalDateTime

)
