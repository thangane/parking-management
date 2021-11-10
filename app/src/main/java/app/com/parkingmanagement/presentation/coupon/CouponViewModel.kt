package app.com.parkingmanagement.presentation.coupon

import androidx.lifecycle.MutableLiveData
import app.com.parkingmanagement.domain.model.Coupon

class CouponViewModel(val coupon: Coupon) {

    private val TAG = CouponViewModel::class.java.simpleName
    private val couponData = MutableLiveData<Coupon>()

    init {
        couponData.value = coupon
    }

}