package com.bluedragoon.smartwaterviews.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bluedragoon.smartwaterviews.databinding.FragmentSaveSessionDialogBinding
import com.google.android.material.button.MaterialButton

class SaveSessionDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "SaveSessionDialogFragment"

        @JvmStatic
        fun newInstance() = SaveSessionDialogFragment()
    }

    private var fragmentSaveSessionDialogBinding: FragmentSaveSessionDialogBinding? = null

    internal lateinit var listener: SaveSessionDialogListener

    interface SaveSessionDialogListener{
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = parentFragment as SaveSessionDialogListener
        }catch (e: ClassCastException){
            throw ClassCastException(context.toString() + "must implement SaveSessionDialogListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSaveSessionDialogBinding.inflate(inflater, container, false)
        fragmentSaveSessionDialogBinding = binding

        //VIEWBINDING
        val closeBtn: MaterialButton = binding.btnClose
        val cancelBtn: MaterialButton = binding.btnCancel
        val saveBtn: MaterialButton = binding.btnSave

        //FUNCTIONALITY
        closeBtn.setOnClickListener {
            dismiss()
        }

        cancelBtn.setOnClickListener {
            listener.onDialogNegativeClick(this)
        }

        saveBtn.setOnClickListener {
            listener.onDialogPositiveClick(this)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentSaveSessionDialogBinding = null
    }

    override fun onDetach() {
        super.onDetach()
        fragmentSaveSessionDialogBinding = null
    }



}