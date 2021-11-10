package app.com.parkingmanagement.presentation.entry_exit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.com.parkingmanagement.R
import app.com.parkingmanagement.databinding.FragmentVehicleExitBinding
import app.com.parkingmanagement.domain.model.ParkingTicket
import app.com.parkingmanagement.presentation.coupon.CouponsViewModel
import app.com.parkingmanagement.presentation.transaction_history.TransactionHistoryViewModel
import app.com.parkingmanagement.util.Constants
import app.com.parkingmanagement.util.DateConverter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VehicleExitFragment : Fragment() {

    private lateinit var fragmentExitBinding: FragmentVehicleExitBinding
    private var mCallback: OnEntryExitCallback? = null
    private var mContext: Context? = null
    private val entryViewModel: EntryViewModel by viewModels()
    private val couponViewModel: CouponsViewModel by viewModels()
    private val transactionViewModel: TransactionHistoryViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEntryExitCallback) {
            mCallback = context
        } else throw ClassCastException(context.toString() + "must implement OnEntryExitCallback!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entryViewModel.setLoad(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentExitBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_vehicle_exit,
            container,
            false
        )
        mContext = container?.context

        entryViewModel.isLoad.observe(viewLifecycleOwner, {
            it?.let { visibility ->
                fragmentExitBinding.exitProgressBar.visibility =
                    if (visibility) View.GONE else View.VISIBLE
            }
        })

        fragmentExitBinding.getReservationDetails.setOnClickListener {
            entryViewModel.getTicketById(fragmentExitBinding.exitReservationIdTextInput.text.toString())
        }

        fragmentExitBinding.vehicleExitOk.setOnClickListener {
            PriceCalculationDialog(
                mContext!!,
                viewLifecycleOwner,
                entryViewModel,
                couponViewModel,
                transactionViewModel
            ).show()
        }
        entryViewModel.isPaid.observe(viewLifecycleOwner, {
            it?.let {
                if (it) {
                    Toast.makeText(context, R.string.paid_success, Toast.LENGTH_SHORT)
                        .show()

                }

            }
        })
        entryViewModel.parkingTicketLiveData.observe(viewLifecycleOwner, {
            if (it == null) {
                fragmentExitBinding.reserveParkingTicket.visibility = View.GONE
            }
            it?.let {
                fragmentExitBinding.reserveParkingTicket.visibility = View.VISIBLE
                setTicketData(it)
            }
        })
        return fragmentExitBinding.root
    }

    private fun setTicketData(parkingTicket: ParkingTicket) {
        fragmentExitBinding.vehicleTypeIcon.setBackgroundResource(getVehicleIcon(parkingTicket))
        fragmentExitBinding.parkingNumber.text = parkingTicket.id
        fragmentExitBinding.entryTime.text =
            DateConverter.convertToDateTime(parkingTicket.entryTime)
        fragmentExitBinding.vehicleNumber.text = parkingTicket.vehicleNumber
        fragmentExitBinding.spaceName.text = parkingTicket.allotedFloorName
    }

    private fun getVehicleIcon(parkingTicket: ParkingTicket): Int {
        return when (parkingTicket.vehicleType) {
            Constants.VEHICLE_CAR -> R.drawable.vehicle_car_icon
            Constants.VEHICLE_BIKE -> R.drawable.vehicle_bike_icon
            Constants.VEHICLE_BUS -> R.drawable.vehicle_bus_icon
            else -> 0
        }
    }

    companion object {

        val FRAGMENT_NAME = VehicleExitFragment::class.java.name

        @JvmStatic
        fun newInstance() =
            VehicleExitFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
