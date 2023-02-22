package com.bluedragoon.smartwaterviews.utilities

import android.content.Context
import android.content.SharedPreferences
import com.bluedragoon.smartwaterviews.models.UserModel

class SharedPreferencesManager(private val context: Context) {

    private val mSharedPreferences: SharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE
    )

    fun saveUserConfiguration(userData: UserModel) {
        val editor = mSharedPreferences.edit()
        editor.putString(USER_ID_KEY, userData.userId)
        editor.putString(USER_NAME_KEY, userData.userName)
        editor.putString(USER_SURNAME_KEY, userData.userSurname)
        editor.putInt(USER_TYPE_KEY, userData.userType)
        editor.putString(RELATED_HOUSE_ID_KEY, userData.houseId)
        editor.apply()
    }

    fun loadUserConfiguration(): UserModel {
        return UserModel(
            mSharedPreferences.getString(USER_ID_KEY, "") ?: "",
            mSharedPreferences.getString(USER_NAME_KEY, "") ?: "",
            mSharedPreferences.getString(USER_SURNAME_KEY, "") ?: "",
            mSharedPreferences.getInt(USER_TYPE_KEY, 0),
            mSharedPreferences.getString(RELATED_HOUSE_ID_KEY, "") ?: "",
        )
    }

    fun saveUserId(userId: String){
        val editor = mSharedPreferences.edit()
        editor.putString(USER_ID_KEY, userId)
        editor.apply()
    }

    fun loadUserId(): String = mSharedPreferences.getString(USER_ID_KEY, "") ?: ""

    fun saveUserName(userName: String, userSurname: String){
        val editor = mSharedPreferences.edit()
        editor.putString(USER_NAME_KEY, userName)
        editor.putString(USER_SURNAME_KEY, userSurname)
        editor.apply()
    }

    fun loadUserName(): String =
        mSharedPreferences.getString(USER_NAME_KEY, "") + " " + mSharedPreferences.getString(
            USER_SURNAME_KEY, "")

    fun saveHouseId(houseId: String){
        val editor = mSharedPreferences.edit()
        editor.putString(RELATED_HOUSE_ID_KEY, houseId)
        editor.apply()
    }

    fun loadHouseId(): String = mSharedPreferences.getString(RELATED_HOUSE_ID_KEY, "") ?: ""

    fun saveHouseName(houseName: String){
        val editor = mSharedPreferences.edit()
        editor.putString(RELATED_HOUSE_NAME_KEY, houseName)
        editor.apply()
    }

    fun loadHouseName(): String = mSharedPreferences.getString(RELATED_HOUSE_NAME_KEY, "") ?: ""

}