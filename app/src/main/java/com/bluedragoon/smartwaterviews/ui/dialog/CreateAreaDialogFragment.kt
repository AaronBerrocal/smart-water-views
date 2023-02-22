package com.bluedragoon.smartwaterviews.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bluedragoon.smartwaterviews.data.entities.H02Areas
import com.bluedragoon.smartwaterviews.databinding.FragmentCreateAreaDialogBinding
import com.bluedragoon.smartwaterviews.utilities.DtosManager
import com.bluedragoon.smartwaterviews.utilities.SharedPreferencesManager
import com.bluedragoon.smartwaterviews.viewmodels.DataManagerViewModel
import com.bluedragoon.smartwaterviews.viewmodels.DataManagerViewModelInjector
import com.google.android.material.button.MaterialButton
import java.util.*

class CreateAreaDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "CreateAreaDialogFragment"

        @JvmStatic
        fun newInstance() = CreateAreaDialogFragment()
    }

    private var fragmentCreateAreaDialogBinding: FragmentCreateAreaDialogBinding? = null

    private val dataViewModel: DataManagerViewModel by activityViewModels {
        DataManagerViewModelInjector.provideDataManagerViewModelFactory(requireContext())
    }

    private val dtosManager = DtosManager()
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private var selectedArea: H02Areas? = null

    private lateinit var actionText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var areaNameValueEt: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCreateAreaDialogBinding.inflate(inflater, container, false)
        fragmentCreateAreaDialogBinding = binding

        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        //VIEWBINDING
        actionText = binding.tvCreateAreaActionText
        descriptionText = binding.tvCreateAreaDescriptionText
        areaNameValueEt = binding.etCreateAreaNameValue
        val backBtn: MaterialButton = binding.btnBack
        val saveBtn: MaterialButton = binding.btnSave

        //FUNCTIONALITY
        backBtn.setOnClickListener {
            dismiss()
        }

        saveBtn.setOnClickListener {
            if(areaNameValueEt.text.isNotBlank()){
                if(selectedArea != null){
                    val newName = areaNameValueEt
                        .text
                        .toString()
                        .uppercase(Locale.getDefault())
                        .filter { it.isLetterOrDigit() }
                    val previousName = selectedArea!!
                        .areaName
                        .uppercase(Locale.getDefault())
                        .filter { it.isLetterOrDigit() }
                    if(newName == previousName){
                        Toast.makeText(requireContext(), "Ya existe un nombre equivalente o duplicado.", Toast.LENGTH_SHORT)
                            .show()
                    }else{
                        val modifiedArea = dtosManager.getModifiedArea(
                            selectedArea!!,
                            areaNameValueEt.text.toString()
                        )
                        dataViewModel.updateAreaVm(modifiedArea)

                        dismiss()
                    }
                }else{
                    val newArea = dtosManager.getNewArea(
                        areaNameValueEt.text.toString(),
                        sharedPreferencesManager.loadHouseId() //TODO: THIS MAY CHANGE
                    )
                    dataViewModel.insertAreaVm(newArea)

                    dismiss()
                }
            }else{
                Toast.makeText(requireContext(), "El nombre es requerido.", Toast.LENGTH_SHORT)
                    .show()
            }



//            if(selectedArea != null){
//                if(areaNameValueEt.text.isNotBlank()){
//                    val newName = areaNameValueEt
//                        .text
//                        .toString()
//                        .uppercase(Locale.getDefault())
//                        .filter { it.isLetterOrDigit() }
//                    val previousName = selectedArea!!
//                        .areaName
//                        .uppercase(Locale.getDefault())
//                        .filter { it.isLetterOrDigit() }
//                    if(newName == previousName){
//                        Toast.makeText(requireContext(), "Ya existe un nombre equivalente o duplicado.", Toast.LENGTH_SHORT)
//                            .show()
//                    }else{
//                        val modifiedArea = dataObjectsCreator.getModifiedArea(
//                            selectedArea!!,
//                            areaNameValueEt.text.toString()
//                        )
//                        dataViewModel.updateAreaVm(modifiedArea)
//                        dismiss()
//                    }
//                }else{
//                    Toast.makeText(requireContext(), "El nombre es requerido.", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }else{
//                if(areaNameValueEt.text.isNotBlank()){
//
//                }else{
//                    Toast.makeText(requireContext(), "El nombre es requerido.", Toast.LENGTH_SHORT)
//                        .show()
//                }
//
//            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataViewModel.getTargetArea().observe(viewLifecycleOwner, Observer { area ->
            if(area != null){
                selectedArea = area
                actionText.text = "Inaformación del área"
                descriptionText.text = "Si lo desea, modifique el sobrenombre"
            }else{
                actionText.text = "Nombra tu nueva área"
                descriptionText.text = "Elige un nombre para identificar esta área"
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
        fragmentCreateAreaDialogBinding = null
    }

    override fun onDetach() {
        fragmentCreateAreaDialogBinding = null
        super.onDetach()
    }
}