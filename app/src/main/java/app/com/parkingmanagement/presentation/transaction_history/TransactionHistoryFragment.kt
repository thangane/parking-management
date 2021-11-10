package app.com.parkingmanagement.presentation.transaction_history

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.com.parkingmanagement.R
import app.com.parkingmanagement.databinding.FragmentTransactionHistoryBinding
import app.com.parkingmanagement.domain.model.ParkingTicket
import app.com.parkingmanagement.presentation.floor_list.FloorsFragment
import app.com.parkingmanagement.presentation.home.OnMainHomeCallback
import app.com.parkingmanagement.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionHistoryFragment : Fragment(), OnHistoryAdapterListener {
    private lateinit var fragmentHistoryBinding: FragmentTransactionHistoryBinding

    private var adapter: TransactionHistoryAdapter? = null
    private var mCallback: OnMainHomeCallback? = null
    private var mContext: Context? = null
    private val historyViewModel: TransactionHistoryViewModel by viewModels()
    private var carFilter: Boolean = true
    private var bikeFilter: Boolean = true;
    private var busFilter: Boolean = true;
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMainHomeCallback) {
            mCallback = context
        } else throw ClassCastException(context.toString() + "must implement OnMainHomeCallback!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TransactionHistoryAdapter(this)
        historyViewModel.loadHistory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHistoryBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_transaction_history,
            container,
            false
        )
        mContext = container?.context
        fragmentHistoryBinding.historyViewModel = historyViewModel
        fragmentHistoryBinding.transactionHistoryRecyclerView.adapter = adapter


        historyViewModel.isLoad.observe(viewLifecycleOwner, {
            it?.let { visibility ->
                fragmentHistoryBinding.transactionProgressBar.visibility =
                    if (visibility) View.GONE else View.VISIBLE
            }
        })

        historyViewModel.historyLiveData.observe(viewLifecycleOwner, {
            it?.let {
                if (it.isNotEmpty()) {
                    fragmentHistoryBinding.historyListNoData.visibility = View.GONE
                    initRecyclerView(it)
                } else {
                    initRecyclerView(emptyList())
                    fragmentHistoryBinding.historyListNoData.visibility = View.VISIBLE
                }
            }
        })
        fragmentHistoryBinding.historyFilter.setOnClickListener {
            HistoryFilterDialog(
                mContext!!,
                carFilter,
                bikeFilter,
                busFilter,
                object : HistoryFilterDialogListener {
                    override fun onOkButtonClicked(
                        carCheck: Boolean,
                        bikeCheck: Boolean,
                        busCheck: Boolean
                    ) {
                        carFilter = carCheck
                        bikeFilter = bikeCheck
                        busFilter = busCheck
                        filterList()
                    }
                }).show()
        }

        return fragmentHistoryBinding.root
    }

    private fun filterList() {
        val filteredList = mutableListOf<ParkingTicket>()
        val historyList = historyViewModel.historyLiveData.value
        if (historyList != null && historyList.isNotEmpty()) {
            if (carFilter) {
                filteredList.addAll(filterByVehicleType(historyList, Constants.VEHICLE_CAR))
            }
            if (busFilter) {
                filteredList.addAll(filterByVehicleType(historyList, Constants.VEHICLE_BUS))
            }
            if (bikeFilter) {
                filteredList.addAll(filterByVehicleType(historyList, Constants.VEHICLE_BIKE))
            }
            initRecyclerView(filteredList)
        }
    }

    private fun filterByVehicleType(
        historyList: List<ParkingTicket>,
        type: String
    ): List<ParkingTicket> {
        return historyList.filter { it.vehicleType == type }.toList()
    }

    private fun initRecyclerView(history: List<ParkingTicket>) {
        Log.i("FloorsFragment", history.toString())
        adapter?.addData(history)
    }

    override fun onDetach() {
        super.onDetach()
        adapter = null
        mCallback = null
    }

    companion object {

        val FRAGMENT_NAME = FloorsFragment::class.java.name

        @JvmStatic
        fun newInstance() =
            TransactionHistoryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}