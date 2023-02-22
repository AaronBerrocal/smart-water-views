package com.bluedragoon.smartwaterviews

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bluedragoon.smartwaterviews.data.entities.Z01Users
import com.bluedragoon.smartwaterviews.databinding.ActivityMainBinding
import com.bluedragoon.smartwaterviews.models.UserModel
import com.bluedragoon.smartwaterviews.utilities.SharedPreferencesManager
import com.bluedragoon.smartwaterviews.viewmodels.DataManagerViewModel
import com.bluedragoon.smartwaterviews.viewmodels.DataManagerViewModelInjector
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

//    private lateinit var wakeLock: PowerManager.WakeLock

    private val dataViewModel: DataManagerViewModel by viewModels {
        DataManagerViewModelInjector.provideDataManagerViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

//        wakeLock = (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
//                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "SmartWaterViews::SmartWaterWakelockTag").apply {
//                    acquire()
//                }
//            }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_detect_flow,
//                R.id.navigation_house,
//                R.id.navigation_dashboard,
//                R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        checkMicrophonePermission()

        val searchedHouseId: String = SharedPreferencesManager(this).loadHouseId()

        val demoUser = UserModel(
            "user",
            "Sebastian",
            "Michaellis",
            0,
            searchedHouseId
        )

        val demoUserDb = Z01Users(
            0,
            "user",
            "Sebastian",
            "Michaellis",
            0,
            searchedHouseId
        )

        SharedPreferencesManager(this).saveUserConfiguration(demoUser)

        dataViewModel.insertUserVm(demoUserDb)

    }

    private fun checkMicrophonePermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.RECORD_AUDIO
                ), 0x123
            )
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        wakeLock.release()
//    }
}