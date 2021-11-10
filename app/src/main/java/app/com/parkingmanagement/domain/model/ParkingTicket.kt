package app.com.parkingmanagement.domain.model


data class ParkingTicket(
    var id: String,
    var vehicleType: String,
    var vehicleNumber: String,
    var entryTime: String,
    var exitTime: String?,
    var parkingAmount: String,
    var couponId: String?,
    var couponCode: String?,
    var discountedAmount: String?,
    var netAmount: String,
    var allotedFloorId: String,
    var allotedFloorName: String
) {

}