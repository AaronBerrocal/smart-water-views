package com.bluedragoon.smartwaterviews.data.repositories

import androidx.lifecycle.LiveData
import com.bluedragoon.smartwaterviews.data.daos.H03WaterIntakesDao
import com.bluedragoon.smartwaterviews.data.entities.H03WaterIntakes

class H03WaterIntakesRepository private constructor(private val h03WaterIntakesDao: H03WaterIntakesDao){

    val getAllWaterIntakesRepo: LiveData<List<H03WaterIntakes>> = h03WaterIntakesDao.getAllWaterIntakes()

    fun getWaterIntakesByHouseIdRepo(selectedHouseId: String): LiveData<List<H03WaterIntakes>>{
        return h03WaterIntakesDao.getWaterIntakesByHouseId(selectedHouseId)
    }

    fun getWaterIntakesByAreaIdRepo(selectedAreaId: String): LiveData<List<H03WaterIntakes>>{
        return h03WaterIntakesDao.getWaterIntakesByAreaId(selectedAreaId)
    }

    fun getWaterIntakesByHouseIdAndAreaIdRepo(
        selectedHouseId: String,
        selectedAreaId: String
    ): LiveData<List<H03WaterIntakes>>{
        return h03WaterIntakesDao.getWaterIntakesByHouseIdAndAreaId(selectedHouseId, selectedAreaId)
    }

    suspend fun insertWaterIntakeRepo(entity: H03WaterIntakes){
        h03WaterIntakesDao.insert(entity)
    }

    suspend fun bulkInsertWaterIntakesRepo(entities: List<H03WaterIntakes>){
        h03WaterIntakesDao.insert(entities)
    }

    suspend fun updateWaterIntakeRepo(entity: H03WaterIntakes){
        h03WaterIntakesDao.update(entity)
    }

    suspend fun updateAllIsSelectedByHouseIdRepo(
        isSelectedValue: Int,
        selectedHouseId: String
    ){
        h03WaterIntakesDao.updateAllIsSelectedByHouseId(isSelectedValue, selectedHouseId)
    }

    suspend fun deleteWaterIntakesByHouseIdRepo(selectedHouseId: String){
        h03WaterIntakesDao.deleteWaterIntakesByHouseId(selectedHouseId)
    }

    suspend fun deleteWaterIntakesByAreaIdRepo(selectedAreaId: String){
        h03WaterIntakesDao.deleteWaterIntakesByAreaId(selectedAreaId)
    }

    companion object{
        @Volatile
        private var instance: H03WaterIntakesRepository? = null

        fun getInstance(h03WaterIntakesDao: H03WaterIntakesDao) =
            instance ?: synchronized(this){
                instance ?: H03WaterIntakesRepository(h03WaterIntakesDao).also { instance = it }
            }
    }

}