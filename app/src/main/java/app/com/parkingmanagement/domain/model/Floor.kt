package app.com.parkingmanagement.domain.model


data class Floor(
    var id: String,
    var name: String,
    var totalSpaces: String,
    var bikeSpace: String,
    var busSpace: String,
    var carSpace: String
) {

    constructor(
        name: String,
        totalSpaces: String,
        bikeSpace: String,
        busSpace: String,
        carSpace: String
    ) : this("", name, totalSpaces, bikeSpace, busSpace, carSpace) {
    }
}