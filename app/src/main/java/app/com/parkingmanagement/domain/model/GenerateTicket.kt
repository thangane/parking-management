package app.com.parkingmanagement.domain.model


data class GenerateTicket(
    var vehicleType: String,
    var vehicleNumber: String,
    var entryTime: String,
    var floorId: String = ""
) {
}