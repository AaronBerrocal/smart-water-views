package com.bluedragoon.smartwaterviews.data.repositories

import androidx.lifecycle.LiveData
import com.bluedragoon.smartwaterviews.data.daos.Z01UsersDao
import com.bluedragoon.smartwaterviews.data.entities.Z01Users

class Z01UsersRepository private constructor(private val z01UsersDao: Z01UsersDao){

    val getAllUsersRepo: LiveData<List<Z01Users>> = z01UsersDao.getAllUsers()

    fun getUsersByHouseIdRepo(selectedHouseId: String): LiveData<List<Z01Users>>{
        return z01UsersDao.getUsersByHouseId(selectedHouseId)
    }

    suspend fun insertUserRepo(entity: Z01Users){
        z01UsersDao.insert(entity)
    }

    suspend fun bulkInsertUsersRepo(entities: List<Z01Users>){
        z01UsersDao.insert(entities)
    }

    suspend fun updateUserRepo(entity: Z01Users){
        z01UsersDao.update(entity)
    }

    suspend fun bulkUpdateUsersRepo(entities: List<Z01Users>){
        z01UsersDao.update(entities)
    }

    suspend fun deleteUserRepo(entity: Z01Users){
        z01UsersDao.delete(entity)
    }

    suspend fun deleteUsersRepo(entities: List<Z01Users>){
        z01UsersDao.delete(entities)
    }

    companion object{
        @Volatile
        private var instance: Z01UsersRepository? = null

        fun getInstance(z01UsersDao: Z01UsersDao) =
            instance ?: synchronized(this){
                instance ?: Z01UsersRepository(z01UsersDao).also { instance = it }
            }
    }

}