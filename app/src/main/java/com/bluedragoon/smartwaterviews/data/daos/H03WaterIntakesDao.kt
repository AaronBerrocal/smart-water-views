package com.bluedragoon.smartwaterviews.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.bluedragoon.smartwaterviews.data.entities.H03WaterIntakes

@Dao
interface H03WaterIntakesDao : BaseDao<H03WaterIntakes> {

    @Query("""SELECT * FROM h03_water_intakes""")
    fun getAllWaterIntakes(): LiveData<List<H03WaterIntakes>>

    @Query("""SELECT * FROM h03_water_intakes WHERE house_id = :selectedHouseId""")
    fun getWaterIntakesByHouseId(selectedHouseId: String): LiveData<List<H03WaterIntakes>>

    @Query("""SELECT * FROM h03_water_intakes WHERE area_id = :selectedAreaId""")
    fun getWaterIntakesByAreaId(selectedAreaId: String): LiveData<List<H03WaterIntakes>>

    @Query("""SELECT * FROM h03_water_intakes 
        WHERE house_id = :selectedHouseId
        AND area_id = :selectedAreaId
        """)
    fun getWaterIntakesByHouseIdAndAreaId(
        selectedHouseId: String,
        selectedAreaId: String
    ): LiveData<List<H03WaterIntakes>>

    @Transaction
    @Query("""UPDATE h03_water_intakes 
        SET is_selected = :isSelectedValue
        WHERE house_id = :selectedHouseId""")
    suspend fun updateAllIsSelectedByHouseId(
        isSelectedValue: Int,
        selectedHouseId: String
    )

    @Transaction
    @Query("""DELETE FROM h03_water_intakes WHERE house_id = :selectedHouseId""")
    suspend fun deleteWaterIntakesByHouseId(selectedHouseId: String)

    @Transaction
    @Query("""DELETE FROM h03_water_intakes WHERE area_id = :selectedAreaId""")
    suspend fun deleteWaterIntakesByAreaId(selectedAreaId: String)

}