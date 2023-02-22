package com.bluedragoon.smartwaterviews.ui.detectflow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bluedragoon.smartwaterviews.R
import com.bluedragoon.smartwaterviews.data.entities.H03WaterIntakes
import com.bluedragoon.smartwaterviews.databinding.FragmentDetectFlowBinding
import com.bluedragoon.smartwaterviews.models.SessionDataSnapshot
import com.bluedragoon.smartwaterviews.ui.dialog.SaveSessionDialogFragment
import com.bluedragoon.smartwaterviews.utilities.*
import com.bluedragoon.smartwaterviews.viewmodels.DataManagerViewModel
import com.bluedragoon.smartwaterviews.viewmodels.DataManagerViewModelInjector
import com.bluedragoon.smartwaterviews.viewmodels.SoundDetectionViewModel
import com.bluedragoon.smartwaterviews.viewmodels.viewModelProviderFactoryOf
import com.google.android.material.button.MaterialButton
import java.time.LocalDateTime

class DetectFlowFragment : Fragment(),
    SaveSessionDialogFragment.SaveSessionDialogListener {

    companion object {
        @JvmStatic
        fun newInstance() = DetectFlowFragment()
    }

    private val detectFlowFragmentTag: String = DetectFlowFragment::class.java.simpleName

    private var fragmentDetectFlowBinding : FragmentDetectFlowBinding? = null

    private val detectFlowViewModel: SoundDetectionViewModel by activityViewModels {
        viewModelProviderFactoryOf { SoundDetectionViewModel(SoundDetector(requireContext())) }
    }

    private val dataViewModel: DataManagerViewModel by activityViewModels {
        DataManagerViewModelInjector.provideDataManagerViewModelFactory(requireContext())
    }

    private val dtosManager = DtosManager()
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    //VIEWS
    private lateinit var playStopBtn : MaterialButton
    private lateinit var detectionStatusTv: TextView
    private lateinit var waterIntakeNameTv: TextView
    private lateinit var areaNameTv: TextView
    private lateinit var vfrEt: EditText
    private lateinit var timeTv: TextView
    private lateinit var expenditureTv: TextView

    //ViewModel Values
    private var timeSpec: Int = 0

    //Support Variables
    private var vfrValue: Float = DEFAULT_VRF
    private var playStopState: Boolean = true
    private var text: String = ""
    private var sessionData: SessionDataSnapshot? = null

    private var preferredWaterIntake: H03WaterIntakes? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetectFlowBinding.inflate(inflater, container, false)
        fragmentDetectFlowBinding = binding

        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        //VIEWBINDING
        playStopBtn = binding.mbtnStartStopFlowDetection
        detectionStatusTv = binding.tvDetectionStatus
        waterIntakeNameTv = binding.tvWaterIntakeName
        areaNameTv = binding.tvAreaName
        vfrEt = binding.etVfrValue
        timeTv = binding.tvTimeValue
        expenditureTv = binding.tvExpenditureValue

        //FUNCTIONALITY
        val detectionTextAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.tween_view)

        playStopBtn.setOnClickListener {
            if(playStopState){
                detectFlowViewModel.setPlayStopCurrentState(false)
                playStopBtn.text = "Terminar"
                detectionStatusTv.text = "Detectando ..."
                detectionStatusTv.startAnimation(detectionTextAnim)
                sessionData = null
                detectFlowViewModel.startDetection()
            }else{
                detectFlowViewModel.setPlayStopCurrentState(true)
                playStopBtn.text = "Detectar"
                detectionStatusTv.text = ""
                detectionStatusTv.clearAnimation()
                detectionStatusTv.alpha = 1.0f
                //TODO: TRIGGER HERE THE ALERT DIALOG TO ASK IF
                // THE USER DESIRES TO SAVE THE SESSION
                // THE TRIGGER IS CONDITIONED TO THE DETECTION TIME
                // ONLY IF IT IS GREATER THAN A PARAMETER
                if(preferredWaterIntake != null){
                    sessionData = SessionDataSnapshot(
                        preferredWaterIntake!!.intakeId,
                        preferredWaterIntake!!.areaId,
                        preferredWaterIntake!!.houseId,
                        sharedPreferencesManager.loadUserId(),
                        vfrValue,
                        timeSpec,
                        timeSpec * vfrValue,
                        LocalDateTime.now()
                    )
                    saveSessionProtocol()
                }
                detectFlowViewModel.stopDetection()

            }
        }

        vfrEt.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                if(vfrEt.text.toString().isEmpty()){
                    text = vfrEt.text.toString()
                    vfrValue = 0.0f
                }else{
                    text = when(vfrEt.text.toString().toFloatOrNull()){
                        null -> text
                        else -> vfrEt.text.toString()
                    }
                    vfrValue = when(vfrEt.text.toString().toFloatOrNull()){
                        null -> vfrValue
                        else -> vfrEt.text.toString().toFloat()
                    }
                }.also {
                    vfrEt.setText(text)
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detectFlowViewModel.timeState.observe(viewLifecycleOwner, Observer {
            timeSpec = it
            timeTv.text = String.format("%d m:%02d s", timeSpec / 60, timeSpec % 60 )
            expenditureTv.text = String.format("%.2f Lts", timeSpec * vfrValue)
        })

        detectFlowViewModel.getPlayStopCurrentState().observe(viewLifecycleOwner, Observer {
            playStopState = it
            vfrEt.isEnabled = it
        })

        dataViewModel.getAllWaterIntakesVm.observe(viewLifecycleOwner, Observer { wis ->
            preferredWaterIntake =
                wis.firstOrNull { it.isSelected == 1 && it.houseId == sharedPreferencesManager.loadHouseId() }
            if(preferredWaterIntake != null){
                waterIntakeNameTv.text = preferredWaterIntake!!.intakeName
                areaNameTv.text = preferredWaterIntake!!.areaName //TODO: FIX THIS, DON'T PASS THE NAME TO THE ENTITY, JUST THE ID
                vfrValue = preferredWaterIntake!!.vfr
            }else{
                waterIntakeNameTv.text = "Toma de Agua Genérica"
                areaNameTv.text = "No disponible"
                vfrValue = DEFAULT_VRF
            }
            vfrEt.setText(vfrValue.toString())
        })

    }

    private fun saveSessionProtocol(){
        if(sessionData != null){
            if(sessionData!!.duration >= MINIMUM_SESSION_DURATION){
                SaveSessionDialogFragment().show(
                    childFragmentManager, SaveSessionDialogFragment.TAG
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentDetectFlowBinding = null
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        if(sessionData != null){
            val newSession = dtosManager.getNewSession(sessionData!!)
            dataViewModel.insertUsageSessionVm(newSession)
            Log.d("DetectFlowFragment ->", "Sesion Guardada. ${newSession.sessionTimestamp.formatToString(
                SESSION_LOG_DATE_TIME_PATTERN_NO_YEAR)}")
            Toast.makeText(requireContext(), "Sesion Guardada. ${newSession.sessionTimestamp.formatToString(
                SESSION_LOG_DATE_TIME_PATTERN_NO_YEAR)}", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        dialog.dismiss()
    }

}