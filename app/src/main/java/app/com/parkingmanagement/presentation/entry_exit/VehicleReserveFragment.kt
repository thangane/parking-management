package app.com.parkingmanagement.presentation.entry_exit

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.com.parkingmanagement.R
import app.com.parkingmanagement.databinding.FragmentVehicleReservationBinding
import app.com.parkingmanagement.domain.model.Floor
import app.com.parkingmanagement.domain.model.GenerateTicket
import app.com.parkingmanagement.domain.model.ParkingTicket
import app.com.parkingmanagement.util.Constants
import app.com.parkingmanagement.util.DateConverter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class VehicleReserveFragment : Fragment() {

    private var mCallback: OnEntryExitCallback? = null
    private var selectedDate: Date = Date()
    private var selectedFloor: Floor? = null
    private lateinit var fragmentReserveBinding: FragmentVehicleReservationBinding
    private var mContext: Context? = null
    private val entryViewModel: EntryViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEntryExitCallback) {
            mCallback = context
        } else throw ClassCastException(context.toString() + "must implement OnEntryExitCallback!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        entryViewModel.setLoad(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentReserveBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_vehicle_reservation,
            container,
            false
        )
        mContext = container?.context
        fragmentReserveBinding.reserveViewModel = entryViewModel

        val dateInput = fragmentReserveBinding.reservationDateTextInput
        dateInput.setText(DateConverter.convertToDateString(Date()))
        dateInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    val result = Calendar.getInstance()
                    result.set(year, monthOfYear, dayOfMonth)
                    selectedDate = result.time
                    dateInput.setText(DateConverter.convertToDateString(result.time))
                }, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.datePicker.minDate = calendar.timeInMillis
            datePicker.show()
        }
        val radioGroup = fragmentReserveBinding.floorSelectionRadioGroup
        fragmentReserveBinding.getReservationDetails.setOnClickListener {
            selectedFloor = null
            radioGroup.removeAllViews()
            entryViewModel.getReservationDetails(selectedDate?.time.toString())
        }

        entryViewModel.isLoad.observe(viewLifecycleOwner, {
            it?.let { visibility ->
                fragmentReserveBinding.vehicleReservationProgressBar.visibility =
                    if (visibility) View.GONE else View.VISIBLE
            }
        })

        entryViewModel.isReservationLoaded.observe(viewLifecycleOwner, {
            it?.let { visibility ->
                fragmentReserveBinding.vehicleReserveDetailLayout.visibility =
                    if (visibility) View.VISIBLE else View.GONE
                if (!visibility) {
                    radioGroup.removeAllViews()
                }
            }
        })

        fragmentReserveBinding.getTicketButton.setOnClickListener {
            generateTicket()
        }

        entryViewModel.reservationDetailsLiveData.observe(viewLifecycleOwner, {
            it?.let {
                it.forEach { floor ->
                    val floorRadio = RadioButton(requireContext())
                    floorRadio.id = View.generateViewId()
                    floorRadio.text =
                        "${floor.name} \n Availability [Bike:${floor.bikeSpace} Car:${floor.carSpace} Bus:${floor.busSpace}]"
                    radioGroup.addView(floorRadio)
                    floorRadio.setOnClickListener {
                        selectedFloor = floor
                    }
                }

            }
        })

        entryViewModel.parkingTicketLiveData.observe(viewLifecycleOwner, {
            if (it == null) {
                Toast.makeText(context, R.string.error_try_again, Toast.LENGTH_SHORT)
                    .show()
            }
            it?.let {
                fragmentReserveBinding.reserveParkingTicket.visibility = View.VISIBLE
                setTicketData(it)
            }
        })
        return fragmentReserveBinding.root
    }

    private fun setTicketData(parkingTicket: ParkingTicket) {
        fragmentReserveBinding.vehicleTypeIcon.setBackgroundResource(getVehicleIcon(parkingTicket))
        fragmentReserveBinding.parkingNumber.text = parkingTicket.id
        fragmentReserveBinding.entryTime.text =
            DateConverter.convertToDateTime(parkingTicket.entryTime)
        fragmentReserveBinding.vehicleNumber.text = parkingTicket.vehicleNumber
        fragmentReserveBinding.spaceName.text = parkingTicket.allotedFloorName
    }

    private fun getVehicleIcon(parkingTicket: ParkingTicket): Int {
        return when (parkingTicket.vehicleType) {
            Constants.VEHICLE_CAR -> R.drawable.vehicle_car_icon
            Constants.VEHICLE_BIKE -> R.drawable.vehicle_bike_icon
            Constants.VEHICLE_BUS -> R.drawable.vehicle_bus_icon
            else -> 0
        }
    }


    private fun generateTicket() {
        if (selectedFloor == null) {
            Toast.makeText(mContext, "Please select the floor", Toast.LENGTH_SHORT).show()
        } else {
            val vehicleType = getVehicleType()
            val vehicleNumber = fragmentReserveBinding.vehicleNumberTextInput.text.toString()
            val entryTime = selectedDate.time.toString()
            entryViewModel.generateTicket(
                GenerateTicket(
                    vehicleType,
                    vehicleNumber,
                    entryTime,
                    selectedFloor!!.id
                )
            )
        }

    }

    private fun getVehicleType(): String {
        return if (fragmentReserveBinding.radioCar.isChecked)
            Constants.VEHICLE_CAR
        else if (fragmentReserveBinding.radioBike.isChecked)
            Constants.VEHICLE_BIKE
        else Constants.VEHICLE_BUS
    }

    companion object {

        val FRAGMENT_NAME = VehicleReserveFragment::class.java.name

        @JvmStatic
        fun newInstance() =
            VehicleReserveFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
