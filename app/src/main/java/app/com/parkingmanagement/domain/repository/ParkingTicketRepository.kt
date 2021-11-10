package app.com.parkingmanagement.domain.repository

import app.com.parkingmanagement.domain.model.Floor
import app.com.parkingmanagement.domain.model.GenerateTicket
import app.com.parkingmanagement.domain.model.ParkingTicket
import app.com.parkingmanagement.domain.model.PriceCalculation
import io.reactivex.Single

/**
 * To make an interaction between [ParkingTicketRepositoryImpl] & [ParkingTicketUseCase]
 * */
interface ParkingTicketRepository {

    fun checkIfEntryExits(entryTime: String): Single<Boolean>

    fun generateTicket(ticket: GenerateTicket): Single<ParkingTicket>

    fun getTicketById(id: String): Single<ParkingTicket>

    fun deleteReservation(id: String): Single<Boolean>

    fun calculatePrice(data: PriceCalculation): Single<ParkingTicket>

    fun getReservationDetails(entryTime: String): Single<List<Floor>>

}