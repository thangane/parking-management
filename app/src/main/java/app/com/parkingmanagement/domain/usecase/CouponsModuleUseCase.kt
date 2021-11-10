package app.com.parkingmanagement.domain.usecase

import app.com.parkingmanagement.domain.usecase.coupons.*
import javax.inject.Inject


/**
 * An interactor that calls the actual implementation of [CouponViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class CouponsModuleUseCase @Inject constructor(
    private val deleteCouponsUseCase: DeleteCouponsUseCase,
    private val generateCouponsUseCase: GenerateCouponsUseCase,
    private val getCouponsUseCase: GetCouponsUseCase,
    private val invalidateCouponsUseCase: InvalidateCouponsUseCase,
    private val useCouponsUseCase: UseCouponsUseCase
) {

    fun getCoupons(): GetCouponsUseCase {
        return getCouponsUseCase
    }

    fun generateCoupons(): GenerateCouponsUseCase {
        return generateCouponsUseCase
    }

    fun deleteCoupon(): DeleteCouponsUseCase {
        return deleteCouponsUseCase
    }

    fun invalidateCoupon(): InvalidateCouponsUseCase {
        return invalidateCouponsUseCase
    }

    fun useCoupon(): UseCouponsUseCase {
        return useCouponsUseCase
    }


}