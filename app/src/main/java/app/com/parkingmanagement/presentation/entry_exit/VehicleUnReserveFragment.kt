package app.com.parkingmanagement.presentation.entry_exit

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.com.parkingmanagement.R
import app.com.parkingmanagement.databinding.FragmentVehicleUnReservationBinding
import app.com.parkingmanagement.domain.model.ParkingTicket
import app.com.parkingmanagement.util.Constants
import app.com.parkingmanagement.util.DateConverter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VehicleUnReserveFragment : Fragment() {

    private lateinit var fragmentUnReserveBinding: FragmentVehicleUnReservationBinding
    private var mCallback: OnEntryExitCallback? = null
    private var mContext: Context? = null
    private val entryViewModel: EntryViewModel by viewModels()

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
        fragmentUnReserveBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_vehicle_un_reservation,
            container,
            false
        )
        mContext = container?.context

        entryViewModel.isLoad.observe(viewLifecycleOwner, {
            it?.let { visibility ->
                fragmentUnReserveBinding.unReservationProgressBar.visibility =
                    if (visibility) View.GONE else View.VISIBLE
            }
        })

        fragmentUnReserveBinding.getReservationDetails.setOnClickListener {
            entryViewModel.getTicketById(fragmentUnReserveBinding.unReservationIdTextInput.text.toString())
        }
        fragmentUnReserveBinding.deleteReservation.setOnClickListener {
            val reservationId = entryViewModel.parkingTicketLiveData.value?.id
            if (!reservationId.isNullOrEmpty()) {
                deleteFloor(reservationId)
            }
        }
        entryViewModel.parkingTicketLiveData.observe(viewLifecycleOwner, {
            if (it == null) {
                fragmentUnReserveBinding.reserveParkingTicket.visibility = View.GONE
            }
            it?.let {
                fragmentUnReserveBinding.reserveParkingTicket.visibility = View.VISIBLE
                setTicketData(it)
            }
        })
        return fragmentUnReserveBinding.root
    }

    private fun deleteFloor(reservationId: String) {
        val deleteAlert: AlertDialog.Builder = AlertDialog.Builder(mContext)
        deleteAlert.setMessage("Do you want to delete the reservation ?")
        deleteAlert.setCancelable(true)
        deleteAlert.setPositiveButton(
            "Yes"
        ) { _, _ ->
            entryViewModel.deleteReservation(reservationId)
        }
        deleteAlert.setNegativeButton(
            "No"
        ) { dialog, _ -> dialog.cancel() }
        val deleteAlertDialog: AlertDialog = deleteAlert.create()
        deleteAlertDialog.show()
    }

    private fun setTicketData(parkingTicket: ParkingTicket) {
        fragmentUnReserveBinding.vehicleTypeIcon.setBackgroundResource(getVehicleIcon(parkingTicket))
        fragmentUnReserveBinding.parkingNumber.text = parkingTicket.id
        fragmentUnReserveBinding.entryTime.text =
            DateConverter.convertToDateTime(parkingTicket.entryTime)
        fragmentUnReserveBinding.vehicleNumber.text = parkingTicket.vehicleNumber
        fragmentUnReserveBinding.spaceName.text = parkingTicket.allotedFloorName
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

        val FRAGMENT_NAME = VehicleUnReserveFragment::class.java.name

        @JvmStatic
        fun newInstance() =
            VehicleUnReserveFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
