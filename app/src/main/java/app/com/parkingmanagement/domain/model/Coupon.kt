package app.com.parkingmanagement.domain.model

import app.com.parkingmanagement.util.DateConverter


data class Coupon(
    var id: String,
    var code: String,
    var startAt: String,
    var endAt: String,
    var used: Boolean = false,
    var invalidated: Boolean = false
) {
    fun getStartDate():String {
        return DateConverter.getDate(startAt)
    }

    fun getEndDate():String {
        return DateConverter.getDate(endAt)
    }
}