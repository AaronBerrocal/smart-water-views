package com.bluedragoon.smartwaterviews.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bluedragoon.smartwaterviews.data.daos.*
import com.bluedragoon.smartwaterviews.data.entities.*
import com.bluedragoon.smartwaterviews.utilities.Converters

@Database(
    entities = [
        H01House::class,
        H02Areas::class,
        H03WaterIntakes::class,
        M01UsageSessions::class,
        Z01Users::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class SmartWaterRoomDatabase : RoomDatabase() {

    abstract fun h01HouseDao(): H01HouseDao
    abstract fun h02AreasDao(): H02AreasDao
    abstract fun h03WaterIntakesDao(): H03WaterIntakesDao
    abstract fun m01UsageSessionsDao(): M01UsageSessionsDao
    abstract fun z01UsersDao(): Z01UsersDao

    companion object{
        @Volatile
        private var instance: SmartWaterRoomDatabase? = null

        fun getInstance(
            context: Context
        ): SmartWaterRoomDatabase{
            return instance ?: synchronized(this){
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): SmartWaterRoomDatabase{
            return Room.databaseBuilder(
                context,
                SmartWaterRoomDatabase::class.java,
                "smart_water_database")
                .fallbackToDestructiveMigration()
                .build()
        }

    }

}