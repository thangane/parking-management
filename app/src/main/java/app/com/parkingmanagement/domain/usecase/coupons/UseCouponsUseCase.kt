package app.com.parkingmanagement.domain.usecase.coupons

import app.com.parkingmanagement.domain.model.Coupon
import app.com.parkingmanagement.domain.repository.CouponRepository
import app.com.parkingmanagement.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject


/**
 * An interactor that calls the actual implementation of [CouponViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class UseCouponsUseCase @Inject constructor(private val repository: CouponRepository) :
    SingleUseCase<Coupon>() {

    override fun buildUseCaseSingle(obj: Any?): Single<Coupon> {
        return repository.useCoupon(obj as String)
    }


}