package app.com.parkingmanagement.data.repository

import app.com.parkingmanagement.data.source.RetrofitService
import app.com.parkingmanagement.domain.model.ParkingCharges
import app.com.parkingmanagement.domain.repository.ParkingChargesRepository
import io.reactivex.Single

class ParkingChargesRepositoryImpl(
    private val retrofitService: RetrofitService
) : ParkingChargesRepository {
    override fun getCharges(): Single<ParkingCharges> {
        return retrofitService.getParkingCharges()
    }

    override fun updateCharges(charges: ParkingCharges): Single<ParkingCharges> {
        return retrofitService.updateParkingCharges(charges)
    }
}