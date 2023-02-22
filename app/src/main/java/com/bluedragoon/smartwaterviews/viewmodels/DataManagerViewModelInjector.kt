package com.bluedragoon.smartwaterviews.viewmodels

import android.content.Context
import com.bluedragoon.smartwaterviews.data.SmartWaterRoomDatabase
import com.bluedragoon.smartwaterviews.data.repositories.*

object DataManagerViewModelInjector {

    private fun getH01HouseRepository(context: Context): H01HouseRepository{
        return H01HouseRepository.getInstance(
            SmartWaterRoomDatabase.getInstance(context.applicationContext).h01HouseDao()
        )
    }

    private fun getH02AreasRepository(context: Context): H02AreasRepository{
        return H02AreasRepository.getInstance(
            SmartWaterRoomDatabase.getInstance(context.applicationContext).h02AreasDao()
        )
    }

    private fun getH03WaterIntakesRepository(context: Context): H03WaterIntakesRepository{
        return H03WaterIntakesRepository.getInstance(
            SmartWaterRoomDatabase.getInstance(context.applicationContext).h03WaterIntakesDao()
        )
    }

    private fun getM01UsageSessionsRepository(context: Context): M01UsageSessionsRepository{
        return M01UsageSessionsRepository.getInstance(
            SmartWaterRoomDatabase.getInstance(context.applicationContext).m01UsageSessionsDao()
        )
    }

    private fun getZ01UsersRepository(context: Context): Z01UsersRepository{
        return Z01UsersRepository.getInstance(
            SmartWaterRoomDatabase.getInstance(context.applicationContext).z01UsersDao()
        )
    }


    fun provideDataManagerViewModelFactory(
        context: Context
    ): DataManagerViewModelFactory{
        return DataManagerViewModelFactory(
            getH01HouseRepository(context),
            getH02AreasRepository(context),
            getH03WaterIntakesRepository(context),
            getM01UsageSessionsRepository(context),
            getZ01UsersRepository(context)
        )
    }

}