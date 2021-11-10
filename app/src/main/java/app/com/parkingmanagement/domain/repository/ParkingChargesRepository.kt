package app.com.parkingmanagement.domain.repository

import app.com.parkingmanagement.domain.model.ParkingCharges
import io.reactivex.Single

/**
 * To make an interaction between [ParkingChargesrRepositoryImpl] & [ParkingChargesUseCase]
 * */
interface ParkingChargesRepository {

    fun getCharges(): Single<ParkingCharges>

    fun updateCharges(charges: ParkingCharges): Single<ParkingCharges>

}