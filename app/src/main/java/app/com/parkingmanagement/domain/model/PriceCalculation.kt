package app.com.parkingmanagement.domain.model


data class PriceCalculation(
    var ticketData: ParkingTicket,
    var couponData: Coupon?,
) {
}