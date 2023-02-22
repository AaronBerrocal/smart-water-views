package com.bluedragoon.smartwaterviews.data.repositories

import androidx.lifecycle.LiveData
import com.bluedragoon.smartwaterviews.data.daos.M01UsageSessionsDao
import com.bluedragoon.smartwaterviews.data.entities.M01UsageSessions
import com.bluedragoon.smartwaterviews.models.SessionLogModel

class M01UsageSessionsRepository private constructor(private val m01UsageSessionsDao: M01UsageSessionsDao){

    val getAllUsageSessionsRepo: LiveData<List<M01UsageSessions>> = m01UsageSessionsDao.getAllUsageSessions()

    fun getUsageSessionsByIntakeIdRepo(selectedIntakeId: String): LiveData<List<M01UsageSessions>>{
        return m01UsageSessionsDao.getUsageSessionsByIntakeId(selectedIntakeId)
    }

    val getSessionLogModelListRepo: LiveData<List<SessionLogModel>> = m01UsageSessionsDao.getSessionLogModelList()

    suspend fun insertUsageSessionRepo(entity: M01UsageSessions){
        m01UsageSessionsDao.insert(entity)
    }

    suspend fun bulkInsertUsageSessionsRepo(entities: List<M01UsageSessions>){
        m01UsageSessionsDao.insert(entities)
    }

    suspend fun updateUsageSessionRepo(entity: M01UsageSessions){
        m01UsageSessionsDao.update(entity)
    }

    suspend fun bulkUpdateUsageSessionsRepo(entities: List<M01UsageSessions>){
        m01UsageSessionsDao.update(entities)
    }

    suspend fun deleteUsageSessionRepo(entity: M01UsageSessions){
        m01UsageSessionsDao.delete(entity)
    }

    suspend fun deleteUsageSessionsByIntakeIdRepo(selectedIntakeId: String){
        m01UsageSessionsDao.deleteUsageSessionsByIntakeId(selectedIntakeId)
    }

    suspend fun deleteUsageSessionsByAreaIdRepo(selectedAreaId: String){
        m01UsageSessionsDao.deleteUsageSessionsByAreaId(selectedAreaId)
    }

    suspend fun deleteUsageSessionsByHouseIdRepo(selectedHouseId: String){
        m01UsageSessionsDao.deleteUsageSessionsByHouseId(selectedHouseId)
    }

    suspend fun deleteUsageSessionsByUserIdRepo(selectedUserId: String){
        m01UsageSessionsDao.deleteUsageSessionsByUserId(selectedUserId)
    }

    companion object{
        @Volatile
        private var instance: M01UsageSessionsRepository? = null

        fun getInstance(m01UsageSessionsDao: M01UsageSessionsDao) =
            instance ?: synchronized(this){
                instance ?: M01UsageSessionsRepository(m01UsageSessionsDao).also { instance = it }
            }
    }


}