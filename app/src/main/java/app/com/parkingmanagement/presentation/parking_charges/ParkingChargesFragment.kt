package app.com.parkingmanagement.presentation.parking_charges

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
import app.com.parkingmanagement.databinding.FragmentParkingChargesBinding
import app.com.parkingmanagement.domain.model.ParkingCharges
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParkingChargesFragment : Fragment() {

    private lateinit var fragmentChargesBinding: FragmentParkingChargesBinding

    private var mCallback: OnParkingChargesCallback? = null
    private var mContext: Context? = null
    private val parkingChargesViewModel: ParkingChargesViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnParkingChargesCallback) {
            mCallback = context
        } else throw ClassCastException(context.toString() + "must implement OnParkingChargesCallback!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parkingChargesViewModel.loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentChargesBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_parking_charges,
            container,
            false
        )
        mContext = container?.context
        fragmentChargesBinding.parkingChargesViewModel = parkingChargesViewModel

        parkingChargesViewModel.isLoad.observe(viewLifecycleOwner, {
            it?.let { visibility ->
                fragmentChargesBinding.chargesProgressBar.visibility =
                    if (visibility) View.GONE else View.VISIBLE
            }
        })

        val firstHourCharge = fragmentChargesBinding.firstHourChargeTextInput
        val laterHourCharge = fragmentChargesBinding.laterHourChargeTextInput

        parkingChargesViewModel.receivedLiveData.observe(viewLifecycleOwner, {
            it?.let {
                firstHourCharge.setText(it.firstHourCharge)
                laterHourCharge.setText(it.laterHourCharge)
            }
        })

        fragmentChargesBinding.firstHourChargeIncreaseButton.setOnClickListener {
            val value = firstHourCharge.text.toString().toInt()
            firstHourCharge.setText((value + 1).toString())
        }
        fragmentChargesBinding.firstHourChargeDecreaseButton.setOnClickListener {
            val value = firstHourCharge.text.toString().toInt()
            if (value >= 0) {
                firstHourCharge.setText((value - 1).toString())
            }
        }
        fragmentChargesBinding.laterHourChargeIncreaseButton.setOnClickListener {
            val value = laterHourCharge.text.toString().toInt()
            laterHourCharge.setText((value + 1).toString())
        }
        fragmentChargesBinding.laterHourChargeDecreaseButton.setOnClickListener {
            val value = laterHourCharge.text.toString().toInt()
            if (value >= 0) {
                laterHourCharge.setText((value - 1).toString())
            }
        }

        fragmentChargesBinding.saveChargesButton.setOnClickListener {
            parkingChargesViewModel.updateData(
                ParkingCharges(
                    firstHourCharge.text.toString(),
                    laterHourCharge.text.toString()
                )
            )
            Toast.makeText(mContext, "Updated", Toast.LENGTH_SHORT).show()
        }

        fragmentChargesBinding.cancelChargesButton.setOnClickListener {
            mCallback!!.goBack()
        }

        return fragmentChargesBinding.root
    }

    override fun onDetach() {
        super.onDetach()
        mCallback = null
    }


    companion object {

        val FRAGMENT_NAME = ParkingChargesFragment::class.java.name

        @JvmStatic
        fun newInstance() =
            ParkingChargesFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}