package app.com.parkingmanagement.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import app.com.parkingmanagement.R
import app.com.parkingmanagement.presentation.coupon.CouponActivity
import app.com.parkingmanagement.presentation.entry_exit.EntryExitActivity
import app.com.parkingmanagement.presentation.entry_exit.EntryExitFragment
import app.com.parkingmanagement.presentation.floor_list.FloorListActivity
import app.com.parkingmanagement.presentation.floor_list.FloorsFragment
import app.com.parkingmanagement.presentation.parking_charges.ParkingChargesActivity
import app.com.parkingmanagement.presentation.settings.SettingsFragment
import app.com.parkingmanagement.presentation.transaction_history.TransactionHistoryFragment
import app.com.parkingmanagement.util.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainHomeActivity : AppCompatActivity(), OnMainHomeCallback,
    NavigationBarView.OnItemSelectedListener {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home)
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setOnItemSelectedListener(this)
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.nav_entry_exit
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_entry_exit -> {
                navigateToEntryExitPage()
                return true
            }
            R.id.nav_settings -> {
                navigateToSettingsPage()
                return true
            }
            R.id.nav_transaction_history -> {
                navigateToTransactionPage()
                return true
            }
        }
        return false
    }

    override fun navigateToEntryExitPage() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_home_container,
                EntryExitFragment.newInstance(),
                FloorsFragment.FRAGMENT_NAME
            ).commitAllowingStateLoss()
    }


    override fun navigateToSettingsPage() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_home_container,
                SettingsFragment.newInstance(),
                FloorsFragment.FRAGMENT_NAME
            ).commitAllowingStateLoss()
    }

    override fun selectSettingsPage() {
        bottomNavigationView.selectedItemId = R.id.nav_settings
    }

    override fun navigateToTransactionPage() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_home_container,
                TransactionHistoryFragment.newInstance(),
                FloorsFragment.FRAGMENT_NAME
            ).commitAllowingStateLoss()
    }

    override fun navigateToFloorListPage() {
        val intent = Intent(this, FloorListActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToCouponListPage() {
        val intent = Intent(this, CouponActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToParkingChargesPage() {
        val intent = Intent(this, ParkingChargesActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToEntryPage() {
        navigateToEntryExitActivity(Constants.VEHICLE_ENTRY)
    }

    override fun navigateToExitPage() {
        navigateToEntryExitActivity(Constants.VEHICLE_EXIT)
    }

    override fun navigateToReservePage() {
        navigateToEntryExitActivity(Constants.VEHICLE_RESERVE)
    }

    override fun navigateToUnReservePage() {
        navigateToEntryExitActivity(Constants.VEHICLE_UN_RESERVE)
    }

    override fun navigateToVacancyListPage() {
        navigateToEntryExitActivity(Constants.VEHICLE_VACANCY)
    }

    private fun navigateToEntryExitActivity(fragmentType: String) {
        val intent = Intent(this, EntryExitActivity::class.java)
        intent.putExtra("fragment", fragmentType)
        startActivity(intent)
    }


}