package app.com.parkingmanagement.presentation.coupon

/**
 * To make an interaction between [CouponsAdapter] & [CouponFragment]
 * */
interface OnCouponsAdapterListener {

    fun deleteCoupon(id: String)

    fun invalidateCoupon(id: String)

}