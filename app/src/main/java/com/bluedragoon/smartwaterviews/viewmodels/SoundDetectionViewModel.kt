package com.bluedragoon.smartwaterviews.viewmodels

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluedragoon.smartwaterviews.utilities.DETECTOR_SLEEP_TIME
import com.bluedragoon.smartwaterviews.utilities.SoundDetector
import com.bluedragoon.smartwaterviews.utilities.WAITING_FOR_WATER_TIME
import com.bluedragoon.smartwaterviews.utilities.WATER_RUNNING
import com.huawei.hms.mlsdk.sounddect.MLSoundDector
import kotlinx.coroutines.*

class SoundDetectionViewModel(private val soundDetector: SoundDetector) : ViewModel() {

    private var job: Job? = null

    val timeState = MutableLiveData(0)

    private val _isWaterRunning = MutableLiveData(false)
    val isWaterRunning: LiveData<Boolean> = _isWaterRunning

    private val _isDetectorRunning = MutableLiveData(false)
    val isDetectorRunning: LiveData<Boolean> = _isDetectorRunning

    var sleepCountdown = 0
    var waterDetectedCountdown = 0

    val playStopCurrentState = MutableLiveData(true)

    fun getPlayStopCurrentState(): LiveData<Boolean>{
        return playStopCurrentState
    }

    fun setPlayStopCurrentState(currentState: Boolean){
        playStopCurrentState.value = currentState
    }

    init {
        soundDetector.setCallbacks(
            onSuccess = { result -> onSoundDetected(result) },
            onError = { errCode -> onDetectorError(errCode) })
    }

    fun startDetection() {
        soundDetector.startDetection()
        _isDetectorRunning.postValue(true)
        waterDetectedCountdown = WAITING_FOR_WATER_TIME
        Log.i("ViewModel", "start water detection")
    }

    fun stopDetection() {
        soundDetector.stopDetection()
        _isDetectorRunning.postValue(false)
        _isWaterRunning.postValue(false)
        stop(0)
        Log.i("ViewModel", "stop water detection")
    }

    fun startTimer() {
        stop(timeState.value!!)
        job = viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                updateLiters()
                waitForWater()
                sleepDetectorOrRestart()
                delay(1_000)
            }
        }
    }

    private fun updateLiters() {
        if (isWaterRunning.value == true) {
            timeState.postValue((timeState.value!! + 1))
        }
    }

    private fun waitForWater() {
        if (soundDetector.isRunning() && waterDetectedCountdown > 0) {
            Log.i("ViewModel", "waiting for water $waterDetectedCountdown")
            waterDetectedCountdown--
        }
        if (waterDetectedCountdown == 0) {
            _isWaterRunning.postValue(false)
            Log.i("ViewModel", "water is not running anymore")
        }
    }

    private fun sleepDetectorOrRestart() {
        if (sleepCountdown > 0) {
            sleepCountdown--
            Log.i("ViewModel", "listen again in $sleepCountdown")
        } else if (!soundDetector.isRunning()) {
            startDetection()
            Log.i("ViewModel", "start water detection")
        }
    }

    fun stop(time: Int = 0) {
        job?.cancel()
        viewModelScope.launch(Dispatchers.IO) {
            timeState.postValue(time)
        }
    }

    private fun onSoundDetected(result: Bundle) {
        val soundType = result.getInt(MLSoundDector.RESULTS_RECOGNIZED)
        if (soundType == WATER_RUNNING) {
            _isWaterRunning.value = true
            sleepCountdown = DETECTOR_SLEEP_TIME
            waterDetectedCountdown = WAITING_FOR_WATER_TIME
            soundDetector.stopDetection()
            startTimer()
            Log.i("ViewModel", "Water running!!!")
        } else {
            _isWaterRunning.value = false
        }
    }

    private fun onDetectorError(errorCode: Int) {
        stop(0)
        _isWaterRunning.value = false
    }
}