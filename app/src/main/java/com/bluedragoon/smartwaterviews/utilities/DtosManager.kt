package com.bluedragoon.smartwaterviews.utilities

import com.bluedragoon.smartwaterviews.data.entities.H01House
import com.bluedragoon.smartwaterviews.data.entities.H02Areas
import com.bluedragoon.smartwaterviews.data.entities.H03WaterIntakes
import com.bluedragoon.smartwaterviews.data.entities.M01UsageSessions
import com.bluedragoon.smartwaterviews.models.AreaToWaterIntakes
import com.bluedragoon.smartwaterviews.models.SessionDataSnapshot
import java.util.*

class DtosManager {

    fun getNewHouse(name: String, adminId: String): H01House {
        return H01House(
            0,
            UUID.randomUUID().toString(),
            name,
            adminId
        )
    }

    fun getModifiedHouse(previousHouse: H01House, name: String, adminId: String): H01House {
        return H01House(
            previousHouse.uid,
            previousHouse.houseId,
            name,
            adminId
        )
    }

    fun getNewArea(name: String, houseId: String): H02Areas {
        return H02Areas(
            0,
            UUID.randomUUID().toString(),
            name,
            houseId
        )
    }

    fun getModifiedArea(previousArea: H02Areas, name: String): H02Areas {
        return H02Areas(
            previousArea.uid,
            previousArea.areaId,
            name,
            previousArea.houseId
        )
    }

    fun getNewWaterIntake(
        name: String,
        vfrValue: Float,
        areaId: String,
        areaName: String,
        houseId: String
    ): H03WaterIntakes {
        return H03WaterIntakes(
            0,
            UUID.randomUUID().toString(),
            name,
            vfrValue,
            areaId,
            areaName,
            houseId,
            0
        )
    }

    fun getModifiedWaterIntake(
        previousIntake: H03WaterIntakes,
        name: String,
        vfrValue: Float,
        areaId: String,
        areaName: String
    ): H03WaterIntakes {
        return H03WaterIntakes(
            previousIntake.uid,
            previousIntake.intakeId,
            name,
            vfrValue,
            areaId,
            areaName,
            previousIntake.houseId,
            previousIntake.isSelected
        )
    }

    fun updateWaterIntakeByIsSelected(
        previousIntake: H03WaterIntakes,
        isSelectedValue: Int
    ): H03WaterIntakes {
        return H03WaterIntakes(
            previousIntake.uid,
            previousIntake.intakeId,
            previousIntake.intakeName,
            previousIntake.vfr,
            previousIntake.areaId,
            previousIntake.areaName,
            previousIntake.houseId,
            isSelectedValue
        )
    }

    fun mergeAreasAndWaterIntakes(
        houseId: String,
        areasList: ArrayList<H02Areas>,
        waterIntakesList: ArrayList<H03WaterIntakes>
    ): List<AreaToWaterIntakes> {
        val atwList = ArrayList<AreaToWaterIntakes>()

        if (areasList.isNotEmpty()) {
            areasList.forEach { area ->
                val wiList = waterIntakesList.filter { it.areaId == area.areaId }
                atwList.add(
                    AreaToWaterIntakes(
                        houseId,
                        area,
                        wiList
                    )
                )
            }
        }

        return atwList
    }

    fun getNewSession(sessionSnapshot: SessionDataSnapshot): M01UsageSessions {
        return M01UsageSessions(
            0,
            UUID.randomUUID().toString(),
            sessionSnapshot.intakeId,
            sessionSnapshot.areaId,
            sessionSnapshot.houseId,
            sessionSnapshot.userId,
            sessionSnapshot.vfr,
            sessionSnapshot.duration,
            sessionSnapshot.consumption,
            sessionSnapshot.sessionTimestamp
        )
    }

}