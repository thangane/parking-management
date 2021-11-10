package app.com.parkingmanagement.data.repository

import app.com.parkingmanagement.data.source.RetrofitService
import app.com.parkingmanagement.domain.model.Coupon
import app.com.parkingmanagement.domain.model.GenerateCoupon
import app.com.parkingmanagement.domain.repository.CouponRepository
import io.reactivex.Single

class CouponRepositoryImpl(
    private val retrofitService: RetrofitService
):CouponRepository {


    override fun generateCoupons(data: GenerateCoupon): Single<List<Coupon>> {
      return retrofitService.generateCoupons(data.count,data.startAt,data.endAt)
    }

    override fun getCoupons(): Single<List<Coupon>> {
       return retrofitService.getCoupons()
    }

    override fun useCoupon(code: String): Single<Coupon> {
        return retrofitService.useCoupon(code)
    }

    override fun deleteCoupon(id: String): Single<Boolean> {
        return retrofitService.deleteCoupon(id)
    }

    override fun invalidateCoupon(id: String): Single<Boolean> {
        return retrofitService.invalidateCoupon(id)
    }
}