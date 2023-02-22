package com.bluedragoon.smartwaterviews.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bluedragoon.smartwaterviews.adapters.AreasArrayAdapter
import com.bluedragoon.smartwaterviews.data.entities.H02Areas
import com.bluedragoon.smartwaterviews.data.entities.H03WaterIntakes
import com.bluedragoon.smartwaterviews.databinding.FragmentCreateWaterIntakeDialogBinding
import com.bluedragoon.smartwaterviews.utilities.DtosManager
import com.bluedragoon.smartwaterviews.utilities.SharedPreferencesManager
import com.bluedragoon.smartwaterviews.viewmodels.DataManagerViewModel
import com.bluedragoon.smartwaterviews.viewmodels.DataManagerViewModelInjector
import com.google.android.material.button.MaterialButton
import java.util.*
import kotlin.collections.ArrayList

class CreateWaterIntakeDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "CreateWaterIntakeDialogFragment"

        @JvmStatic
        fun newInstance() = CreateWaterIntakeDialogFragment()
    }

    private var fragmentCreateWaterIntakeDialogBinding: FragmentCreateWaterIntakeDialogBinding? = null

    private val dataViewModel: DataManagerViewModel by activityViewModels {
        DataManagerViewModelInjector.provideDataManagerViewModelFactory(requireContext())
    }

    private val dtosManager = DtosManager()
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private var selectedWaterIntake: H03WaterIntakes? = null

    private lateinit var actionText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var waterIntakeNameValueEt: EditText
    private lateinit var waterIntakeVfrEt: EditText

    private lateinit var areasSpr: Spinner
    private var selectedAreasList = ArrayList<H02Areas>()
    private lateinit var areasCustomAdapter: AreasArrayAdapter
//    private var selectedAreaId: String = ""
    private lateinit var selectedArea: H02Areas

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCreateWaterIntakeDialogBinding.inflate(inflater, container, false)
        fragmentCreateWaterIntakeDialogBinding = binding

        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        //VIEWBINDING
        actionText = binding.tvCreateWiActionText
        descriptionText = binding.tvCreateWiDescriptionText
        waterIntakeNameValueEt = binding.etCreateWiNameValue
        waterIntakeVfrEt = binding.etCreateWiVfrValue
        areasSpr = binding.sprCreateWiAreaValue
        val backBtn: MaterialButton = binding.btnBack
        val saveBtn: MaterialButton = binding.btnSave

        //FUNCTIONALITY
        backBtn.setOnClickListener {
            dismiss()
        }

        saveBtn.setOnClickListener {
            if(waterIntakeNameValueEt.text.isNotBlank() && waterIntakeVfrEt.text.isNotBlank()){
                if(selectedWaterIntake != null){
                    val newName = waterIntakeNameValueEt
                        .text
                        .toString()
                        .uppercase(Locale.getDefault())
                        .filter { it.isLetterOrDigit() }
                    val previousName = selectedWaterIntake!!
                        .intakeName
                        .uppercase(Locale.getDefault())
                        .filter { it.isLetterOrDigit() }
                    if(newName == previousName){
                        Toast.makeText(requireContext(), "Ya existe un nombre equivalente o duplicado.", Toast.LENGTH_SHORT)
                            .show()
                    }else{
                        val modifiedWaterIntake = dtosManager.getModifiedWaterIntake(
                            selectedWaterIntake!!,
                            waterIntakeNameValueEt.text.toString(),
                            waterIntakeVfrEt.text.toString().toFloatOrNull() ?: 0.0f,
                            selectedArea.areaId,
                            selectedArea.areaName
                        )
                        dataViewModel.updateWaterIntakeVm(modifiedWaterIntake)

                        dismiss()
                    }
                }else{
                    val newWaterIntake = dtosManager.getNewWaterIntake(
                        waterIntakeNameValueEt.text.toString(),
                        waterIntakeVfrEt.text.toString().toFloatOrNull() ?: 0.0f,
                        selectedArea.areaId,
                        selectedArea.areaName,
                        sharedPreferencesManager.loadHouseId()
                    )
                    dataViewModel.insertWaterIntakeVm(newWaterIntake)

                    dismiss()
                }
            }else{
                Toast.makeText(requireContext(), "Todos los campos son requeridos.", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        areasCustomAdapter = AreasArrayAdapter(requireContext(),selectedAreasList)
        areasSpr.adapter = areasCustomAdapter
        areasSpr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedArea = parent?.getItemAtPosition(position) as H02Areas
//                selectedAreaId = (parent.getItemAtPosition(position) as H02Areas).areaId
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataViewModel.getAllAreasVm.observe(viewLifecycleOwner, Observer { areas ->
            areas.let { areasList ->
                selectedAreasList = areasList.filter { it.houseId == sharedPreferencesManager.loadHouseId() } as ArrayList<H02Areas>
                areasCustomAdapter.setAreas(selectedAreasList)
            }
        })

        dataViewModel.getTargetWaterIntake().observe(viewLifecycleOwner, Observer { wi ->
            if(wi != null){
                selectedWaterIntake = wi
                actionText.text = "Modifica tu toma de agua"
                descriptionText.text = "Si lo deseas, elige otro sobrenombre"
            }else{
                actionText.text = "Configura tu nueva toma de agua"
                descriptionText.text = "Escribe un identificador para esta toma"
            }

        })

    }

    override fun onResume() {
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentCreateWaterIntakeDialogBinding = null
    }

    override fun onDetach() {
        super.onDetach()
        fragmentCreateWaterIntakeDialogBinding = null
    }

}