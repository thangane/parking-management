package app.com.parkingmanagement.presentation.coupon

interface CouponFilterDialogListener {

    fun onOkButtonClicked(showUsed: Boolean, showUnused: Boolean, showInvalidated: Boolean)
}