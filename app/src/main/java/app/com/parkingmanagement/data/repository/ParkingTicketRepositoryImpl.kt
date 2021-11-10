package app.com.parkingmanagement.data.repository

import app.com.parkingmanagement.data.source.RetrofitService
import app.com.parkingmanagement.domain.model.Floor
import app.com.parkingmanagement.domain.model.GenerateTicket
import app.com.parkingmanagement.domain.model.ParkingTicket
import app.com.parkingmanagement.domain.model.PriceCalculation
import app.com.parkingmanagement.domain.repository.ParkingTicketRepository
import io.reactivex.Single

class ParkingTicketRepositoryImpl(
    private val retrofitService: RetrofitService
) : ParkingTicketRepository {
    override fun checkIfEntryExits(entryTime: String): Single<Boolean> {
        return retrofitService.checkIfEntryExits(entryTime)
    }

    override fun generateTicket(data: GenerateTicket): Single<ParkingTicket> {
        return retrofitService.generateTicket(data)
    }

    override fun getTicketById(id: String): Single<ParkingTicket> {
        return retrofitService.getTicketById(id)
    }

    override fun deleteReservation(id: String): Single<Boolean> {
        return retrofitService.deleteReservation(id)
    }

    override fun getReservationDetails(entryTime: String): Single<List<Floor>> {
        return retrofitService.getReservationDetails(entryTime)
    }

    override fun calculatePrice(data: PriceCalculation): Single<ParkingTicket> {
        return retrofitService.calculatePrice(data)
    }


}