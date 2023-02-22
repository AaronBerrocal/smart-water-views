package com.bluedragoon.smartwaterviews.ui.house

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluedragoon.smartwaterviews.R
import com.bluedragoon.smartwaterviews.adapters.AreaParentRvAdapter
import com.bluedragoon.smartwaterviews.data.entities.H01House
import com.bluedragoon.smartwaterviews.data.entities.H02Areas
import com.bluedragoon.smartwaterviews.data.entities.H03WaterIntakes
import com.bluedragoon.smartwaterviews.databinding.FragmentHouseBinding
import com.bluedragoon.smartwaterviews.models.AreaToWaterIntakes
import com.bluedragoon.smartwaterviews.ui.dialog.CreateAreaDialogFragment
import com.bluedragoon.smartwaterviews.ui.dialog.CreateHouseDialogFragment
import com.bluedragoon.smartwaterviews.ui.dialog.CreateWaterIntakeDialogFragment
import com.bluedragoon.smartwaterviews.utilities.DtosManager
import com.bluedragoon.smartwaterviews.utilities.SharedPreferencesManager
import com.bluedragoon.smartwaterviews.viewmodels.DataManagerViewModel
import com.bluedragoon.smartwaterviews.viewmodels.DataManagerViewModelInjector
import com.google.android.material.button.MaterialButton

class HouseFragment : Fragment(), PopupMenu.OnMenuItemClickListener {

    companion object{
        @JvmStatic
        fun newInstance() = HouseFragment()
    }

    private val houseFragmentTag: String = HouseFragment::class.java.simpleName

    private var fragmentHouseBinding: FragmentHouseBinding? = null

    private val dataViewModel: DataManagerViewModel by activityViewModels {
        DataManagerViewModelInjector.provideDataManagerViewModelFactory(requireContext())
    }

    private val areaParentRvAdapter = AreaParentRvAdapter()
    private val dtosManager = DtosManager()
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    //VIEWS
    private lateinit var houseNameTv:TextView
    private lateinit var houseAdminTv:TextView
    private lateinit var noHouseTv:TextView

    //UTILITY VARIABLES
    private lateinit var currentSelectionAreaToWaterIntakes: List<AreaToWaterIntakes>
    private var selectedHouse: H01House? = null
    private var selectedAreasList: List<H02Areas> = ArrayList()
    private var selectedWaterIntakesList: List<H03WaterIntakes> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHouseBinding.inflate(inflater, container, false)
        fragmentHouseBinding = binding

        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        //VIEWBINDING
        val addObjectsBtn: MaterialButton = binding.btnAddObjects
        houseNameTv = binding.cvHouseInfo.tvHouseName
        houseAdminTv = binding.cvHouseInfo.tvHouseAdminName
        noHouseTv = binding.cvHouseInfo.tvNoHouseFound
        val areasRv: RecyclerView = binding.rvParentAreas

        //FUNCTIONALITY
        areasRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        areasRv.adapter = areaParentRvAdapter

        areaParentRvAdapter.setAreaClickListener { wi ->
            //TODO: SCRIPT THE FUNCTION TO SELECT THIS WATERINTAKE AS THE ONE TO
            // USE IN THE DETECTFLOW FRAGMENT:
            // TO ACHIEVE THAT WE NEED TO:
            // - SAVE THE CLICKED H03 TO THE SOUNDDETECTIONVIEWMODEL AS CURRENT SELECTED
            dataViewModel.updateAllIsSelectedByHouseIdVm(0, sharedPreferencesManager.loadHouseId())
            val selectedWi = dtosManager.updateWaterIntakeByIsSelected(wi, if(wi.isSelected == 0) 1 else 0)
            dataViewModel.updateWaterIntakeVm(selectedWi)
        }

        addObjectsBtn.setOnClickListener {
            showCreateObjectMenu(it)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        dataViewModel.getHouseByHouseIdVm(sharedPreferencesManager.loadHouseId()).observe(viewLifecycleOwner, Observer { selectedHouse ->
//            if(selectedHouse != null){
//                dataViewModel.setTargetHouse(selectedHouse!!) //TODO: THIS MAY CHANGE
//                noHouseTv.visibility = View.GONE
//                houseNameTv.visibility = View.VISIBLE
//                houseNameTv.text = selectedHouse!!.houseName
//                houseAdminTv.visibility = View.VISIBLE
//                houseAdminTv.text = sharedPreferencesManager.loadUserName()   //TODO: CHANGE FOR THE USER WITH ADMIN PERMISSIONS
//            }else{
//                noHouseTv.visibility = View.VISIBLE
//                houseNameTv.visibility = View.GONE
//                houseAdminTv.visibility = View.GONE
//            }
//        })

        dataViewModel.getAllHousesVm.observe(viewLifecycleOwner, Observer { houses ->
            houses.let { houseList ->
                selectedHouse =
                    houseList.firstOrNull { it.houseId == sharedPreferencesManager.loadHouseId() }
                if(selectedHouse != null){
                    dataViewModel.setTargetHouse(selectedHouse!!) //TODO: THIS MAY CHANGE
                    noHouseTv.visibility = View.GONE
                    houseNameTv.visibility = View.VISIBLE
                    houseNameTv.text = selectedHouse!!.houseName
                    houseAdminTv.visibility = View.VISIBLE
                    houseAdminTv.text = sharedPreferencesManager.loadUserName()  //TODO: CHANGE FOR THE USER WITH ADMIN PERMISSIONS
                }else{
                    noHouseTv.visibility = View.VISIBLE
                    houseNameTv.visibility = View.GONE
                    houseAdminTv.visibility = View.GONE
                }
            }
        })

        dataViewModel.getAllAreasVm.observe(viewLifecycleOwner, Observer { areas ->
            areas.let { areasList ->
                selectedAreasList = areasList.filter { it.houseId == sharedPreferencesManager.loadHouseId() }
                currentSelectionAreaToWaterIntakes = dtosManager.mergeAreasAndWaterIntakes(
                    sharedPreferencesManager.loadHouseId(),
                    selectedAreasList as ArrayList<H02Areas>,
                    selectedWaterIntakesList as ArrayList<H03WaterIntakes>
                )

                if(currentSelectionAreaToWaterIntakes.isNotEmpty()){
                    areaParentRvAdapter.setAreaToWaterIntakes(currentSelectionAreaToWaterIntakes)
                }
            }
        })

        dataViewModel.getAllWaterIntakesVm.observe(viewLifecycleOwner, Observer { waterIntakes ->
            waterIntakes.let { waterIntakesList ->
                selectedWaterIntakesList = waterIntakesList.filter { it.houseId == sharedPreferencesManager.loadHouseId() }
                currentSelectionAreaToWaterIntakes = dtosManager.mergeAreasAndWaterIntakes(
                    sharedPreferencesManager.loadHouseId(),
                    selectedAreasList as ArrayList<H02Areas>,
                    selectedWaterIntakesList as ArrayList<H03WaterIntakes>
                )

                if(currentSelectionAreaToWaterIntakes.isNotEmpty()){
                    areaParentRvAdapter.setAreaToWaterIntakes(currentSelectionAreaToWaterIntakes)
                }
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentHouseBinding = null
    }

    private fun showCreateObjectMenu(v: View){
        PopupMenu(requireContext(), v).apply {
            setOnMenuItemClickListener(this@HouseFragment)
            inflate(R.menu.create_objects_menu)
            show()
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.create_house_opt -> {
                if(selectedHouse == null){
                    CreateHouseDialogFragment().show(childFragmentManager, CreateHouseDialogFragment.TAG)
                }else{
                    Toast.makeText(requireContext(), "Límite de casas: 1", Toast.LENGTH_SHORT)
                        .show()
                }
                true
            }
            R.id.create_area_opt -> {
                if(selectedHouse != null){
                    CreateAreaDialogFragment().show(childFragmentManager, CreateAreaDialogFragment.TAG)
                }else{
                    Toast.makeText(requireContext(), "Es requerida una casa.", Toast.LENGTH_SHORT)
                        .show()
                }
                true
            }
            R.id.create_water_intake_opt -> {
                if(selectedHouse != null && selectedAreasList.isNotEmpty()){
                    CreateWaterIntakeDialogFragment().show(childFragmentManager, CreateWaterIntakeDialogFragment.TAG)
                }else{
                    Toast.makeText(requireContext(), "Es requerida una casa y al menos un área.", Toast.LENGTH_SHORT)
                        .show()
                }
                true
            }
            else -> false
        }
    }




}