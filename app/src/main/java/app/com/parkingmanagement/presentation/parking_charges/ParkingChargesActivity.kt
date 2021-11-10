package app.com.parkingmanagement.presentation.parking_charges

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.com.parkingmanagement.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ParkingChargesActivity : AppCompatActivity(), OnParkingChargesCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_charges)
        if (savedInstanceState == null) {
            navigateToParkingChargesPage()
        }
    }

    override fun navigateToParkingChargesPage() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.parking_charge_container,
                ParkingChargesFragment.newInstance(),
                ParkingChargesFragment.FRAGMENT_NAME
            ).commitAllowingStateLoss()
    }

    override fun goBack() {
        this.finish()
    }


}



