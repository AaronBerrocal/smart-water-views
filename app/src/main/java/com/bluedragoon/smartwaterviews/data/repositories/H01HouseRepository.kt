package com.bluedragoon.smartwaterviews.data.repositories

import androidx.lifecycle.LiveData
import com.bluedragoon.smartwaterviews.data.daos.H01HouseDao
import com.bluedragoon.smartwaterviews.data.entities.H01House

class H01HouseRepository private constructor(private val h01HouseDao: H01HouseDao){

    val getAllHousesRepo: LiveData<List<H01House>> = h01HouseDao.getAllHouses()

    fun getHouseByHouseIdRepo(selectedHouseId: String): LiveData<H01House>{
        return h01HouseDao.getHouseByHouseId(selectedHouseId)
    }

    suspend fun insertHouseRepo(entity: H01House){
        h01HouseDao.insert(entity)
    }

    suspend fun updateHouseRepo(entity: H01House){
        h01HouseDao.update(entity)
    }

    suspend fun deleteHouseRepo(entity: H01House){
        h01HouseDao.delete(entity)
    }

    companion object{
        @Volatile
        private var instance: H01HouseRepository? = null

        fun getInstance(h01HouseDao: H01HouseDao) =
            instance ?: synchronized(this){
                instance ?: H01HouseRepository(h01HouseDao).also { instance = it }
            }
    }
}