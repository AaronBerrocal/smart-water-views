package com.bluedragoon.smartwaterviews.models

import com.bluedragoon.smartwaterviews.data.entities.H02Areas
import com.bluedragoon.smartwaterviews.data.entities.H03WaterIntakes

data class AreaToWaterIntakes(

    val houseId: String,

    val parentArea: H02Areas,

    val childrenWaterIntakes: List<H03WaterIntakes>

)
