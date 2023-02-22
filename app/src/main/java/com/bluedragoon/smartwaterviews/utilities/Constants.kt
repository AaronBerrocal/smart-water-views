package com.bluedragoon.smartwaterviews.utilities

const val DETECTOR_SLEEP_TIME = 2
const val WAITING_FOR_WATER_TIME = 8
const val WATER_RUNNING = 7
const val DEFAULT_VRF: Float = 0.08f
const val MINIMUM_SESSION_DURATION : Int = 10

//REGEXP AND PATTERNS
const val USER_NAME_PATTERN_STR : String = "^(?=.{1,30}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$"
const val PASSWORD_PATTERN_STR : String = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{10,30}$"
const val ROUTE_DATE_PATTERN: String = "MMMM d, yyyy"
const val ROUTE_TIME_PATTERN: String = "HH:mm"
const val WEB_DATE_TIME_PATTERN: String = "yyyy-MM-dd HH:mm:ss"
const val ISO_DATE_TIME_PATTERN: String = "yyyy-MM-dd'T'HH:mm:ss"
const val NOTIFICATION_DATE_TIME_PATTERN: String = "MMMM d, yyyy. HH:mm"
const val DATE_TO_ID_PATTERN: String = "ddMMyyyyHHmm"
const val SESSION_LOG_DATE_TIME_PATTERN: String = "d MMMM, yyyy. HH:mm"
const val SESSION_LOG_DATE_TIME_PATTERN_NO_YEAR: String = "d MMMM. HH:mm"

//SHARED PREFERENCES
const val SHARED_PREFERENCES_NAME : String = "com.bluedragoon.smartwaterviews.SHARED_PREFERENCES"
const val USER_ID_KEY : String = "com.bluedragoon.smartwaterviews.USER_ID"
const val USER_NAME_KEY : String = "com.bluedragoon.smartwaterviews.USER_NAME"
const val USER_SURNAME_KEY : String = "com.bluedragoon.smartwaterviews.USER_SURNAME"
const val USER_TYPE_KEY : String = "com.bluedragoon.smartwaterviews.USER_TYPE"
const val RELATED_HOUSE_ID_KEY : String = "com.bluedragoon.smartwaterviews.RELATED_HOUSE_ID"
const val RELATED_HOUSE_NAME_KEY : String = "com.bluedragoon.smartwaterviews.RELATED_HOUSE_NAME"

const val REMEMBER_USER_KEY : String = "com.bluedragoon.smartwaterviews.REMEMBER_USER"
const val IS_USER_LOGGED_IN_KEY : String = "com.bluedragoon.smartwaterviews.IS_USER_LOGGED_IN"
const val LAST_LOGGED_TIMESTAMP_KEY: String = "com.bluedragoon.smartwaterviews.LAST_LOGGED_TIMESTAMP"