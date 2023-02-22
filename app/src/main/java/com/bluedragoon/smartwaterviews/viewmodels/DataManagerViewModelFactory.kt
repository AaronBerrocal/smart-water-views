package com.bluedragoon.smartwaterviews.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bluedragoon.smartwaterviews.data.repositories.*

class DataManagerViewModelFactory(
    private val h01HouseRepository: H01HouseRepository,
    private val h02AreasRepository: H02AreasRepository,
    private val h03WaterIntakesRepository: H03WaterIntakesRepository,
    private val m01UsageSessionsRepository: M01UsageSessionsRepository,
    private val z01UsersRepository: Z01UsersRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DataManagerViewModel(
            h01HouseRepository,
            h02AreasRepository,
            h03WaterIntakesRepository,
            m01UsageSessionsRepository,
            z01UsersRepository
        ) as T
    }
}