package com.bluedragoon.smartwaterviews.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.bluedragoon.smartwaterviews.data.entities.Z01Users

@Dao
interface Z01UsersDao : BaseDao<Z01Users> {

    @Query("""SELECT * FROM z01_users""")
    fun getAllUsers(): LiveData<List<Z01Users>>

    @Query("""SELECT * FROM z01_users WHERE house_id = :selectedHouseId""")
    fun getUsersByHouseId(selectedHouseId: String): LiveData<List<Z01Users>>


}