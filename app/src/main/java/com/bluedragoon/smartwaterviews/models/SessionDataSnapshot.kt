package com.bluedragoon.smartwaterviews.models

import java.time.LocalDateTime

data class SessionDataSnapshot(

    val intakeId: String,

    val areaId: String,

    val houseId: String,

    val userId: String,

    val vfr: Float,

    val duration: Int,

    val consumption: Float,

    val sessionTimestamp: LocalDateTime

)
