package app.com.parkingmanagement.presentation.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.com.parkingmanagement.R
import app.com.parkingmanagement.presentation.floor_list.FloorsFragment
import app.com.parkingmanagement.presentation.home.OnMainHomeCallback
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var mCallback: OnMainHomeCallback? = null
    private lateinit var floorListCardView: MaterialButton
    private lateinit var couponListCardView: MaterialButton
    private lateinit var parkingChargesCardView: MaterialButton

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMainHomeCallback) {
            mCallback = context
        } else throw ClassCastException(context.toString() + "must implement OnMainHomeCallback!")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_settings, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        floorListCardView = requireView().findViewById(R.id.floor_settings_card)
        couponListCardView = requireView().findViewById(R.id.discount_coupon_settings_card)
        parkingChargesCardView = requireView().findViewById(R.id.parking_charges_settings_card)
        eventListeners()
    }

    private fun eventListeners() {
        floorListCardView.setOnClickListener {
            mCallback!!.navigateToFloorListPage()
        }
        couponListCardView.setOnClickListener {
            mCallback!!.navigateToCouponListPage()
        }
        parkingChargesCardView.setOnClickListener {
            mCallback!!.navigateToParkingChargesPage()
        }
    }

    companion object {

        val FRAGMENT_NAME = FloorsFragment::class.java.name

        @JvmStatic
        fun newInstance() =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}