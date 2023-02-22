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
import com.bluedragoon.smartwaterviews.data.entities.H01House
import com.bluedragoon.smartwaterviews.databinding.FragmentCreateHouseDialogBinding
import com.bluedragoon.smartwaterviews.utilities.DtosManager
import com.bluedragoon.smartwaterviews.utilities.SharedPreferencesManager
import com.bluedragoon.smartwaterviews.viewmodels.DataManagerViewModel
import com.bluedragoon.smartwaterviews.viewmodels.DataManagerViewModelInjector
import com.google.android.material.button.MaterialButton
import java.util.*


class CreateHouseDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "CreateHouseDialogFragment"

        @JvmStatic
        fun newInstance() = CreateHouseDialogFragment()
    }

    private var fragmentCreateHouseDialogBinding: FragmentCreateHouseDialogBinding? = null

    private val dataViewModel: DataManagerViewModel by activityViewModels {
        DataManagerViewModelInjector.provideDataManagerViewModelFactory(requireContext())
    }

    private val dtosManager = DtosManager()
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private var selectedHouse: H01House? = null

    private lateinit var actionText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var houseNameValueEt: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCreateHouseDialogBinding.inflate(inflater, container, false)
        fragmentCreateHouseDialogBinding = binding

        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        //VIEWBINDING
        actionText = binding.tvCreateHouseActionText
        descriptionText = binding.tvCreateHouseDescriptionText
        houseNameValueEt = binding.etCreateHouseNameValue
        val backBtn: MaterialButton = binding.btnBack
        val saveBtn: MaterialButton = binding.btnSave

        //FUNCTIONALITY
        backBtn.setOnClickListener {
            dismiss()
        }

        saveBtn.setOnClickListener {
            if (houseNameValueEt.text.isNotBlank()) {
                if (selectedHouse != null) {
                    val newName = houseNameValueEt
                        .text
                        .toString()
                        .uppercase(Locale.getDefault())
                        .filter { it.isLetterOrDigit() }
                    val previousName = selectedHouse!!
                        .houseName
                        .uppercase(Locale.getDefault())
                        .filter { it.isLetterOrDigit() }
                    if(newName == previousName){
                        Toast.makeText(requireContext(), "Ya existe un nombre equivalente o duplicado.", Toast.LENGTH_SHORT)
                            .show()
                    }else{
                        val modifiedHouse = dtosManager.getModifiedHouse(
                            selectedHouse!!,
                            houseNameValueEt.text.toString(),
                            sharedPreferencesManager.loadUserId() //TODO: THIS MAY CHANGE
                        )
                        dataViewModel.updateHouseVm(modifiedHouse)

                        dismiss()
                    }
                }else{
                    val newHouse = dtosManager.getNewHouse(
                        houseNameValueEt.text.toString(),
                        sharedPreferencesManager.loadUserId() //TODO: THIS MAY CHANGE
                    )
                    dataViewModel.insertHouseVm(newHouse)
                    sharedPreferencesManager.saveHouseId(newHouse.houseId)
                    sharedPreferencesManager.saveHouseName(newHouse.houseName)

                    dismiss()
                }
            }else{
                Toast.makeText(requireContext(), "El nombre es requerido.", Toast.LENGTH_SHORT)
                    .show()
            }




//            if (selectedHouse != null) {
//                if (houseNameValueEt.text.isNotBlank()) {
//                    val newName = houseNameValueEt
//                        .text
//                        .toString()
//                        .uppercase(Locale.getDefault())
//                        .filter { it.isLetterOrDigit() }
//                    val previousName = selectedHouse!!
//                        .houseName
//                        .uppercase(Locale.getDefault())
//                        .filter { it.isLetterOrDigit() }
//                    if(newName == previousName){
//                        Toast.makeText(requireContext(), "Ya existe un nombre equivalente o duplicado.", Toast.LENGTH_SHORT)
//                            .show()
//                    }else{
//                        val modifiedHouse = dataObjectsCreator.getModifiedHouse(
//                            selectedHouse!!,
//                            houseNameValueEt.text.toString(),
//                            sharedPreferencesManager.loadUserId() //TODO: THIS MAY CHANGE
//                        )
//                        dataViewModel.updateHouseVm(modifiedHouse)
//                        dismiss()
//                    }
//                } else {
//                    Toast.makeText(requireContext(), "El nombre es requerido.", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            } else {
//                if (houseNameValueEt.text.isNotBlank()) {
//                    val newHouse = dataObjectsCreator.getNewHouse(
//                        houseNameValueEt.text.toString(),
//                        sharedPreferencesManager.loadUserId() //TODO: THIS MAY CHANGE
//                    )
//                    dataViewModel.insertHouseVm(newHouse)
//                    sharedPreferencesManager.saveHouseId(newHouse.houseId)
//                    sharedPreferencesManager.saveHouseName(newHouse.houseName)
//
//                    dismiss()
//                } else {
//                    Toast.makeText(requireContext(), "El nombre es requerido.", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataViewModel.getTargetHouse().observe(viewLifecycleOwner, Observer { house ->
            if (house != null) {
                selectedHouse = house
                actionText.text = "Información de tu casa"
                descriptionText.text = "Si lo desea, modifique el sobrenombre"
            } else {
                actionText.text = "Nombra tu casa"
                descriptionText.text = "Escribe un sobrenombre para identificar esta casa"
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
        fragmentCreateHouseDialogBinding = null
    }

    override fun onDetach() {
        super.onDetach()
        fragmentCreateHouseDialogBinding = null
    }

}