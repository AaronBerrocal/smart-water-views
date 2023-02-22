package com.bluedragoon.smartwaterviews.data.repositories

import androidx.lifecycle.LiveData
import com.bluedragoon.smartwaterviews.data.daos.H02AreasDao
import com.bluedragoon.smartwaterviews.data.entities.H02Areas

class H02AreasRepository private constructor(private val h02AreasDao: H02AreasDao){

    val getAllAreasRepo: LiveData<List<H02Areas>> = h02AreasDao.getAllAreas()

    fun getAreasByHouseIdRepo(selectedHouseId: String): LiveData<List<H02Areas>>{
        return h02AreasDao.getAreasByHouseId(selectedHouseId)
    }

    suspend fun insertAreaRepo(entity: H02Areas){
        h02AreasDao.insert(entity)
    }

    suspend fun bulkInsertAreasRepo(entities: List<H02Areas>){
        h02AreasDao.insert(entities)
    }

    suspend fun updateAreaRepo(entity: H02Areas){
        h02AreasDao.update(entity)
    }

    suspend fun deleteAreaRepo(entity: H02Areas){
        h02AreasDao.delete(entity)
    }

    suspend fun deleteAreasByHouseIdRepo(selectedHouseId: String){
        h02AreasDao.deleteAreasByHouseId(selectedHouseId)
    }

    companion object{
        @Volatile
        private var instance: H02AreasRepository? = null

        fun getInstance(h02AreasDao: H02AreasDao) =
            instance ?: synchronized(this){
                instance ?: H02AreasRepository(h02AreasDao).also { instance = it }
            }
    }

}