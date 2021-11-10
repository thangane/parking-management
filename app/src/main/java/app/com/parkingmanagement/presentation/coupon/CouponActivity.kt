package app.com.parkingmanagement.presentation.coupon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.com.parkingmanagement.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CouponActivity : AppCompatActivity(), OnCouponCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon_list)
        if (savedInstanceState == null) {
            navigateToCouponListPage()
        }
    }

    override fun navigateToCouponListPage() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.coupon_list_container,
                CouponFragment.newInstance(),
                CouponFragment.FRAGMENT_NAME
            ).commitAllowingStateLoss()
    }


}