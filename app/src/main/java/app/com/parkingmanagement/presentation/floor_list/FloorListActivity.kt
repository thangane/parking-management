package app.com.parkingmanagement.presentation.floor_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.com.parkingmanagement.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FloorListActivity : AppCompatActivity(), OnFloorListCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_floor_list)
        if (savedInstanceState == null) {
            navigateToFloorListPage()
        }
    }

    override fun navigateToFloorListPage() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.floor_list_container,
                FloorsFragment.newInstance(),
                FloorsFragment.FRAGMENT_NAME
            ).commitAllowingStateLoss()
    }
}