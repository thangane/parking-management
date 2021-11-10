package app.com.parkingmanagement.domain.repository

import app.com.parkingmanagement.domain.model.Floor
import app.com.parkingmanagement.domain.model.Vacant
import io.reactivex.Single

/**
 * To make an interaction between [FloorRepositoryImpl] & [FloorsUseCase]
 * */
interface FloorRepository {

    fun getFloors(): Single<List<Floor>>

    fun addFloor(floor: Floor): Single<Boolean>

    fun deleteFloor(id: String): Single<Boolean>

    fun updateFloor(floor: Floor): Single<Boolean>

    fun getSettings(): Single<Boolean>

    fun getFloorVacancy(entryTime: String): Single<List<Vacant>>
}