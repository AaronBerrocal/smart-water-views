package com.bluedragoon.smartwaterviews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluedragoon.smartwaterviews.data.entities.*
import com.bluedragoon.smartwaterviews.data.repositories.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DataManagerViewModel(
    private val h01HouseRepository: H01HouseRepository,
    private val h02AreasRepository: H02AreasRepository,
    private val h03WaterIntakesRepository: H03WaterIntakesRepository,
    private val m01UsageSessionsRepository: M01UsageSessionsRepository,
    private val z01UsersRepository: Z01UsersRepository
) : ViewModel() {

    //region Operations properties
    val targetHouse = MutableLiveData<H01House>()

    fun getTargetHouse(): LiveData<H01House>{
        return targetHouse
    }

    fun setTargetHouse(selectedHouse: H01House){
        targetHouse.value = selectedHouse
    }

    val targetArea = MutableLiveData<H02Areas>()

    fun getTargetArea(): LiveData<H02Areas>{
        return targetArea
    }

    fun setTargetArea(selectedArea: H02Areas){
        targetArea.value = selectedArea
    }

    val targetWaterIntake = MutableLiveData<H03WaterIntakes>()

    fun getTargetWaterIntake(): LiveData<H03WaterIntakes>{
        return targetWaterIntake
    }

    fun setTargetWaterIntake(selectedWaterIntake: H03WaterIntakes){
        targetWaterIntake.value = selectedWaterIntake
    }

    //endregion Operations properties

    //region h01House properties
    val getAllHousesVm = h01HouseRepository.getAllHousesRepo

    fun getHouseByHouseIdVm(selectedHouseId: String): LiveData<H01House> {
        return h01HouseRepository.getHouseByHouseIdRepo(selectedHouseId)
    }

    fun insertHouseVm(entity: H01House): Job = viewModelScope.launch {
        h01HouseRepository.insertHouseRepo(entity)
    }

    fun updateHouseVm(entity: H01House): Job = viewModelScope.launch {
        h01HouseRepository.updateHouseRepo(entity)
    }

    fun deleteHouseVm(entity: H01House): Job = viewModelScope.launch {
        h01HouseRepository.deleteHouseRepo(entity)
    }
    //endregion h01House properties

    //region h02Areas properties
    val getAllAreasVm = h02AreasRepository.getAllAreasRepo

    fun getAreasByHouseIdVm(selectedHouseId: String): LiveData<List<H02Areas>>{
        return h02AreasRepository.getAreasByHouseIdRepo(selectedHouseId)
    }

    fun insertAreaVm(entity: H02Areas): Job = viewModelScope.launch {
        h02AreasRepository.insertAreaRepo(entity)
    }

    fun bulkInsertAreasVm(entities: List<H02Areas>): Job = viewModelScope.launch {
        h02AreasRepository.bulkInsertAreasRepo(entities)
    }

    fun updateAreaVm(entity: H02Areas): Job = viewModelScope.launch {
        h02AreasRepository.updateAreaRepo(entity)
    }

    fun deleteAreaVm(entity: H02Areas): Job = viewModelScope.launch {
        h02AreasRepository.deleteAreaRepo(entity)
    }

    fun deleteAreasByHouseIdVm(selectedHouseId: String): Job = viewModelScope.launch {
        h02AreasRepository.deleteAreasByHouseIdRepo(selectedHouseId)
    }
    //endregion h02Areas properties

    //region h03WaterIntakes properties
    val getAllWaterIntakesVm = h03WaterIntakesRepository.getAllWaterIntakesRepo

    fun getWaterIntakesByHouseIdVm(selectedHouseId: String): LiveData<List<H03WaterIntakes>>{
        return h03WaterIntakesRepository.getWaterIntakesByHouseIdRepo(selectedHouseId)
    }

    fun getWaterIntakesByAreaIdVm(selectedAreaId: String): LiveData<List<H03WaterIntakes>>{
        return h03WaterIntakesRepository.getWaterIntakesByAreaIdRepo(selectedAreaId)
    }

    fun getWaterIntakesByHouseIdAndAreaIdVm(
        selectedHouseId: String,
        selectedAreaId: String
    ): LiveData<List<H03WaterIntakes>>{
        return h03WaterIntakesRepository.getWaterIntakesByHouseIdAndAreaIdRepo(
            selectedHouseId,
            selectedAreaId
        )
    }

    fun insertWaterIntakeVm(entity: H03WaterIntakes): Job = viewModelScope.launch {
        h03WaterIntakesRepository.insertWaterIntakeRepo(entity)
    }

    fun bulkInsertWaterIntakesVm(entities: List<H03WaterIntakes>): Job = viewModelScope.launch {
        h03WaterIntakesRepository.bulkInsertWaterIntakesRepo(entities)
    }

    fun updateWaterIntakeVm(entity: H03WaterIntakes): Job = viewModelScope.launch {
        h03WaterIntakesRepository.updateWaterIntakeRepo(entity)
    }

    fun updateAllIsSelectedByHouseIdVm(
        isSelectedValue: Int,
        selectedHouseId: String
    ): Job = viewModelScope.launch {
        h03WaterIntakesRepository.updateAllIsSelectedByHouseIdRepo(isSelectedValue, selectedHouseId)
    }

    fun deleteWaterIntakesByHouseIdVm(selectedHouseId: String): Job = viewModelScope.launch {
        h03WaterIntakesRepository.deleteWaterIntakesByHouseIdRepo(selectedHouseId)
    }

    fun deleteWaterIntakesByAreaIdVm(selectedAreaId: String): Job = viewModelScope.launch {
        h03WaterIntakesRepository.deleteWaterIntakesByAreaIdRepo(selectedAreaId)
    }
    //endregion h03WaterIntakes properties

    //region m01UsageSessions properties
    val getAllUsageSessionsVm = m01UsageSessionsRepository.getAllUsageSessionsRepo

    fun getUsageSessionsByIntakeIdVm(selectedIntakeId: String): LiveData<List<M01UsageSessions>>{
        return m01UsageSessionsRepository.getUsageSessionsByIntakeIdRepo(selectedIntakeId)
    }

    val getSessionLogModelListVm = m01UsageSessionsRepository.getSessionLogModelListRepo

    fun insertUsageSessionVm(entity: M01UsageSessions): Job = viewModelScope.launch {
        m01UsageSessionsRepository.insertUsageSessionRepo(entity)
    }

    fun bulkInsertUsageSessionsVm(entities: List<M01UsageSessions>): Job = viewModelScope.launch {
        m01UsageSessionsRepository.bulkInsertUsageSessionsRepo(entities)
    }

    fun updateUsageSessionVm(entity: M01UsageSessions): Job = viewModelScope.launch {
        m01UsageSessionsRepository.updateUsageSessionRepo(entity)
    }

    fun bulkUpdateUsageSessionsVm(entities: List<M01UsageSessions>): Job = viewModelScope.launch {
        m01UsageSessionsRepository.bulkUpdateUsageSessionsRepo(entities)
    }

    fun deleteUsageSessionVm(entity: M01UsageSessions): Job = viewModelScope.launch {
        m01UsageSessionsRepository.deleteUsageSessionRepo(entity)
    }

    fun deleteUsageSessionsByIntakeIdVm(selectedIntakeId: String): Job = viewModelScope.launch {
        m01UsageSessionsRepository.deleteUsageSessionsByIntakeIdRepo(selectedIntakeId)
    }

    fun deleteUsageSessionsByAreaIdVm(selectedAreaId: String): Job = viewModelScope.launch {
        m01UsageSessionsRepository.deleteUsageSessionsByAreaIdRepo(selectedAreaId)
    }

    fun deleteUsageSessionsByHouseIdVm(selectedHouseId: String): Job = viewModelScope.launch {
        m01UsageSessionsRepository.deleteUsageSessionsByHouseIdRepo(selectedHouseId)
    }

    fun deleteUsageSessionsByUserIdVm(selectedUserId: String): Job = viewModelScope.launch {
        m01UsageSessionsRepository.deleteUsageSessionsByUserIdRepo(selectedUserId)
    }
    //endregion m01UsageSessions properties

    //region z01Users properties
    val getAllUsersVm = z01UsersRepository.getAllUsersRepo

    fun getUsersByHouseIdVm(selectedHouseId: String): LiveData<List<Z01Users>>{
        return z01UsersRepository.getUsersByHouseIdRepo(selectedHouseId)
    }

    fun insertUserVm(entity: Z01Users): Job = viewModelScope.launch {
        z01UsersRepository.insertUserRepo(entity)
    }

    fun bulkInsertUsersVm(entities: List<Z01Users>): Job = viewModelScope.launch {
        z01UsersRepository.bulkInsertUsersRepo(entities)
    }

    fun updateUserVm(entity: Z01Users): Job = viewModelScope.launch {
        z01UsersRepository.updateUserRepo(entity)
    }

    fun bulkUpdateUsersVm(entities: List<Z01Users>): Job = viewModelScope.launch {
        z01UsersRepository.bulkUpdateUsersRepo(entities)
    }

    fun deleteUserVm(entity: Z01Users): Job = viewModelScope.launch {
        z01UsersRepository.deleteUserRepo(entity)
    }

    fun deleteUsersVm(entities: List<Z01Users>): Job = viewModelScope.launch {
        z01UsersRepository.deleteUsersRepo(entities)
    }
    //endregion z01Users properties

}