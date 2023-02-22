package com.bluedragoon.smartwaterviews.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluedragoon.smartwaterviews.adapters.SessionsLogRvAdapter
import com.bluedragoon.smartwaterviews.data.entities.H02Areas
import com.bluedragoon.smartwaterviews.data.entities.H03WaterIntakes
import com.bluedragoon.smartwaterviews.data.entities.M01UsageSessions
import com.bluedragoon.smartwaterviews.data.entities.Z01Users
import com.bluedragoon.smartwaterviews.databinding.FragmentDashboardBinding
import com.bluedragoon.smartwaterviews.models.SessionLogModel
import com.bluedragoon.smartwaterviews.utilities.DtosManager
import com.bluedragoon.smartwaterviews.utilities.SharedPreferencesManager
import com.bluedragoon.smartwaterviews.viewmodels.DataManagerViewModel
import com.bluedragoon.smartwaterviews.viewmodels.DataManagerViewModelInjector

class DashboardFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = DashboardFragment()
    }

    private var fragmentDashboardBinding: FragmentDashboardBinding? = null

    private val dataViewModel: DataManagerViewModel by activityViewModels {
        DataManagerViewModelInjector.provideDataManagerViewModelFactory(requireContext())
    }

    private val sessionLogRvAdapter = SessionsLogRvAdapter()
    private val dtosManager = DtosManager()
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private var sessionsLogList = ArrayList<M01UsageSessions>()
    private var waterIntakesList = ArrayList<H03WaterIntakes>()
    private var areasList = ArrayList<H02Areas>()
    private var usersList = ArrayList<Z01Users>()

    private var sessionsLogModelList = ArrayList<SessionLogModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDashboardBinding.inflate(inflater, container, false)
        fragmentDashboardBinding = binding

        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        //VIEWBINDING
        val sessionsRv: RecyclerView = binding.rvSessionsLog

        //FUNCTIONALITY
        sessionsRv.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        sessionsRv.adapter = sessionLogRvAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataViewModel.getSessionLogModelListVm.observe(viewLifecycleOwner, Observer { sessionModels ->
            sessionModels.let { smList ->
                sessionsLogModelList = smList as ArrayList<SessionLogModel>
//                    .filter { it.houseName == sharedPreferencesManager.loadHouseName() } as ArrayList<SessionLogModel>
                if(sessionsLogModelList.isNotEmpty()){
                    sessionLogRvAdapter.setSessionsLog(sessionsLogModelList)
                }
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentDashboardBinding = null
    }
}