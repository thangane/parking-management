package app.com.parkingmanagement.domain.model


data class GenerateCoupon(
    var count: String,
    var startAt: String,
    var endAt: String,
) {
}