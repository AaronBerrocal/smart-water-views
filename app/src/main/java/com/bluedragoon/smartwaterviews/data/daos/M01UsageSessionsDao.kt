package com.bluedragoon.smartwaterviews.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.bluedragoon.smartwaterviews.data.entities.M01UsageSessions
import com.bluedragoon.smartwaterviews.models.SessionLogModel

@Dao
interface M01UsageSessionsDao : BaseDao<M01UsageSessions> {

    @Query("""SELECT * FROM m01_usage_sessions""")
    fun getAllUsageSessions(): LiveData<List<M01UsageSessions>>

    @Query("""SELECT * FROM m01_usage_sessions WHERE intake_id = :selectedIntakeId""")
    fun getUsageSessionsByIntakeId(selectedIntakeId: String): LiveData<List<M01UsageSessions>>

    @Transaction
    @Query("""SELECT 
        h03.intake_name as intake_name,
        h02.area_name as area_name,
        h01.house_name as house_name,
        (z01.user_name || ' ' || z01.user_surname) as user_full_name,
        m01.vfr as vfr,
        m01.duration as duration,
        m01.consumption as consumption,
        m01.session_timestamp as session_timestamp
        FROM m01_usage_sessions m01
        LEFT JOIN h03_water_intakes h03 on h03.intake_id = m01.intake_id 
        LEFT JOIN h02_areas h02 on h02.area_id = m01.area_id
        LEFT JOIN h01_house h01 on h01.house_id = m01.house_id
        LEFT JOIN z01_users z01 on z01.user_id = m01.user_id 
        ORDER BY session_timestamp DESC
    """)
    fun getSessionLogModelList(): LiveData<List<SessionLogModel>>

    @Transaction
    @Query("""DELETE FROM m01_usage_sessions WHERE intake_id = :selectedIntakeId""")
    suspend fun deleteUsageSessionsByIntakeId(selectedIntakeId: String)

    @Transaction
    @Query("""DELETE FROM m01_usage_sessions WHERE area_id = :selectedAreaId""")
    suspend fun deleteUsageSessionsByAreaId(selectedAreaId: String)

    @Transaction
    @Query("""DELETE FROM m01_usage_sessions WHERE house_id = :selectedHouseId""")
    suspend fun deleteUsageSessionsByHouseId(selectedHouseId: String)

    @Transaction
    @Query("""DELETE FROM m01_usage_sessions WHERE user_id = :selectedUserId""")
    suspend fun deleteUsageSessionsByUserId(selectedUserId: String)

}