package app.com.parkingmanagement.presentation.coupon

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.com.parkingmanagement.domain.model.Coupon
import app.com.parkingmanagement.domain.model.GenerateCoupon
import app.com.parkingmanagement.domain.usecase.CouponsModuleUseCase
import java.util.*

class CouponsViewModel @ViewModelInject constructor(private val couponsUseCase: CouponsModuleUseCase) :
    ViewModel() {

    private val TAG = CouponsViewModel::class.java.simpleName
    val couponsReceivedLiveData = MutableLiveData<List<Coupon>>()
    val isLoad = MutableLiveData<Boolean>()
    val couponData = MutableLiveData<Coupon>()
    val isValidCoupon = MutableLiveData<Boolean?>()

    init {
        isLoad.value = false
        isValidCoupon.value = null
    }

    val coupon: Coupon? get() = couponData.value

    fun set(coupon: Coupon) = run { couponData.value = coupon }

    fun loadCoupons() {
        isLoad.value = false
        couponsUseCase.getCoupons().execute(
            obj = null,
            onSuccess = {
                isLoad.value = true
                couponsReceivedLiveData.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun generateCoupons(couponCount: String, endDate: String) {
        isLoad.value = false
        couponsUseCase.generateCoupons().execute(
            obj = GenerateCoupon(
                count = couponCount,
                startAt = Date().time.toString(),
                endAt = endDate
            ),
            onSuccess = {
                isLoad.value = true
                couponsReceivedLiveData.value = it
            },
            onError = {
                it.printStackTrace()
            },
        )
    }

    fun invalidateCoupon(id: String) {
        isLoad.value = false
        couponsUseCase.invalidateCoupon().execute(
            obj = id,
            onSuccess = {
                loadCoupons()
            },
            onError = {
                it.printStackTrace()
            },
        )
    }

    fun useCoupon(code: String) {
        isLoad.value = false
        isValidCoupon.value = null
        couponsUseCase.useCoupon().execute(
            obj = code,
            onSuccess = {
                isLoad.value = true
                isValidCoupon.value = true
                couponData.value = it
            },
            onError = {
                isValidCoupon.value = false
                it.printStackTrace()
            },
        )
    }

    fun deleteCoupon(id: String) {
        isLoad.value = false
        couponsUseCase.deleteCoupon().execute(
            obj = id,
            onSuccess = {
                loadCoupons()
            },
            onError = {
                it.printStackTrace()
            },
        )
    }

}