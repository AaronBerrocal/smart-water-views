package com.bluedragoon.smartwaterviews.utilities

import com.bluedragoon.smartwaterviews.data.entities.Z01Users
import com.bluedragoon.smartwaterviews.models.UserModel
import java.text.ParseException
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.formatToString(datePattern: String): String {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(datePattern)
    return this.format(formatter)
}

fun ZonedDateTime.formatToString(datePattern: String): String {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(datePattern)
    return this.format(formatter)
}

fun String.formatToLdt(datePattern: String): LocalDateTime {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(datePattern)
    return try {
        LocalDateTime.parse(this, formatter)
    } catch (e: ParseException) {
        LocalDateTime.now()
    }
}

fun String.formatToZdt(datePattern: String): ZonedDateTime {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(datePattern)
    return try {
        ZonedDateTime.parse(this, formatter)
    } catch (e: ParseException) {
        ZonedDateTime.now()
    }
}

fun Z01Users.ToUserModel(): UserModel {
    return UserModel(
        this.userId,
        this.userName,
        this.userSurname,
        this.userType,
        this.houseId
    )
}

fun UserModel.ToZ01User(): Z01Users {
    return Z01Users(
        0,
        this.userId,
        this.userName,
        this.userSurname,
        this.userType,
        this.houseId
    )
}