package com.bluedragoon.smartwaterviews.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.bluedragoon.smartwaterviews.data.entities.H01House

@Dao
interface H01HouseDao : BaseDao<H01House> {

    @Query("""SELECT * FROM h01_house""")
    fun getAllHouses(): LiveData<List<H01House>>

    @Query("""SELECT * FROM h01_house WHERE house_id = :selectedHouseId""")
    fun getHouseByHouseId(selectedHouseId: String): LiveData<H01House>

}