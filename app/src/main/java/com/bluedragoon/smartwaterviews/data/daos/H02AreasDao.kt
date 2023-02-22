package com.bluedragoon.smartwaterviews.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.bluedragoon.smartwaterviews.data.entities.H02Areas

@Dao
interface H02AreasDao : BaseDao<H02Areas> {

    @Query("""SELECT * FROM h02_areas""")
    fun getAllAreas(): LiveData<List<H02Areas>>

    @Query("""SELECT * FROM h02_areas WHERE house_id = :selectedHouseId""")
    fun getAreasByHouseId(selectedHouseId: String): LiveData<List<H02Areas>>

    @Transaction
    @Query("""DELETE FROM h02_areas WHERE house_id = :selectedHouseId""")
    suspend fun deleteAreasByHouseId(selectedHouseId: String)

}