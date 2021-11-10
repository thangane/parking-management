package app.com.parkingmanagement.domain.model


data class Vacant(
    var id: String,
    var name: String,
    var totalSpaces: String,
    var bikeSpace: String,
    var busSpace: String,
    var carSpace: String,
    var vacantBikeSpace: String,
    var vacantBusSpace: String,
    var vacantCarSpace: String
) {

}