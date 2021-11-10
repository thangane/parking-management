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
import app.com.parkingmanagement.databinding.FragmentVehicleEntryBinding
import app.com.parkingmanagement.domain.model.GenerateTicket
import app.com.parkingmanagement.domain.model.ParkingTicket
import app.com.parkingmanagement.presentation.floor_list.FloorsFragment
import app.com.parkingmanagement.util.Constants
import app.com.parkingmanagement.util.DateConverter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class VehicleEntryFragment : Fragment() {

    private lateinit var fragmentEntryBinding: FragmentVehicleEntryBinding
    private var mCallback: OnEntryExitCallback? = null
    private var mContext: Context? = null
    private val entryViewModel: EntryViewModel by viewModels()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEntryExitCallback) {
            mCallback = context
        } else throw ClassCastException(context.toString() + "must implement OnEntryExitCallback!")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        entryViewModel.checkIfEntryExists(Date().time.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentEntryBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_vehicle_entry,
            container,
            false
        )
        mContext = container?.context
        fragmentEntryBinding.entryViewModel = entryViewModel

        entryViewModel.isLoad.observe(viewLifecycleOwner, {
            it?.let { visibility ->
                fragmentEntryBinding.vehicleEntryProgressBar.visibility =
                    if (visibility) View.GONE else View.VISIBLE
            }
        })
        fragmentEntryBinding.getTicketButton.setOnClickListener {
            generateTicket()
        }
        entryViewModel.parkingTicketLiveData.observe(viewLifecycleOwner, {
            if (it == null) {
                Toast.makeText(context, R.string.error_try_again, Toast.LENGTH_SHORT)
                    .show()
            }
            it?.let {
                fragmentEntryBinding.ticketEmptyView.visibility = View.GONE
                fragmentEntryBinding.parkingTicket.visibility = View.VISIBLE
                setTicketData(it)
            }
        })
        return fragmentEntryBinding.root
    }

    private fun generateTicket() {
        val vehicleType = getVehicleType()
        val vehicleNumber = fragmentEntryBinding.vehicleNumberTextInput.text.toString()
        val entryTime = Date().time.toString()
        entryViewModel.generateTicket(GenerateTicket(vehicleType, vehicleNumber, entryTime))
    }

    private fun getVehicleType(): String {
        return if (fragmentEntryBinding.radioCar.isChecked)
            Constants.VEHICLE_CAR
        else if (fragmentEntryBinding.radioBike.isChecked)
            Constants.VEHICLE_BIKE
        else Constants.VEHICLE_BUS
    }

    private fun setTicketData(parkingTicket: ParkingTicket) {
        fragmentEntryBinding.vehicleTypeIcon.setBackgroundResource(getVehicleIcon(parkingTicket))
        fragmentEntryBinding.parkingNumber.text = parkingTicket.id
        fragmentEntryBinding.entryTime.text =
            DateConverter.convertToDateTime(parkingTicket.entryTime)
        fragmentEntryBinding.vehicleNumber.text = parkingTicket.vehicleNumber
        fragmentEntryBinding.spaceName.text = parkingTicket.allotedFloorName
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

        val FRAGMENT_NAME = FloorsFragment::class.java.name

        @JvmStatic
        fun newInstance() =
            VehicleEntryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
