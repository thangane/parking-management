package app.com.parkingmanagement.domain.repository

import app.com.parkingmanagement.domain.model.Coupon
import app.com.parkingmanagement.domain.model.GenerateCoupon
import io.reactivex.Single

/**
 * To make an interaction between [CouponRepositoryImpl] & [CouponsUseCase]
 * */
interface CouponRepository {

    fun generateCoupons(data: GenerateCoupon): Single<List<Coupon>>

    fun getCoupons(): Single<List<Coupon>>

    fun useCoupon(code: String): Single<Coupon>

    fun deleteCoupon(id: String): Single<Boolean>

    fun invalidateCoupon(fid: String): Single<Boolean>
}