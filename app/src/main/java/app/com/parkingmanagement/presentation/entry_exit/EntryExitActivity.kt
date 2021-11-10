package app.com.parkingmanagement.presentation.entry_exit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.com.parkingmanagement.R
import app.com.parkingmanagement.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryExitActivity : AppCompatActivity(), OnEntryExitCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_exit)
        if (savedInstanceState == null) {
            when (intent.getStringExtra("fragment")) {
                Constants.VEHICLE_ENTRY -> navigateToVehicleEntryPage()
                Constants.VEHICLE_EXIT -> navigateToVehicleExitPage()
                Constants.VEHICLE_RESERVE -> navigateToVehicleReservePage()
                Constants.VEHICLE_UN_RESERVE -> navigateToVehicleUnReservePage()
                Constants.VEHICLE_VACANCY -> navigateToVehicleVacancyPage()
            }
        }
    }

    override fun navigateToVehicleEntryPage() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.entry_exit_container,
                VehicleEntryFragment.newInstance(),
                VehicleEntryFragment.FRAGMENT_NAME
            ).commitAllowingStateLoss()
    }

    override fun navigateToVehicleExitPage() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.entry_exit_container,
                VehicleExitFragment.newInstance(),
                VehicleExitFragment.FRAGMENT_NAME
            ).commitAllowingStateLoss()
    }

    override fun navigateToVehicleReservePage() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.entry_exit_container,
                VehicleReserveFragment.newInstance(),
                VehicleReserveFragment.FRAGMENT_NAME
            ).commitAllowingStateLoss()
    }

    override fun navigateToVehicleUnReservePage() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.entry_exit_container,
                VehicleUnReserveFragment.newInstance(),
                VehicleUnReserveFragment.FRAGMENT_NAME
            ).commitAllowingStateLoss()
    }

    override fun navigateToVehicleVacancyPage() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.entry_exit_container,
                VehicleVacancyFragment.newInstance(),
                VehicleVacancyFragment.FRAGMENT_NAME
            ).commitAllowingStateLoss()

    }
}