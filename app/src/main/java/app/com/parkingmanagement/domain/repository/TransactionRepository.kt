package app.com.parkingmanagement.domain.repository

import app.com.parkingmanagement.domain.model.ParkingTicket
import io.reactivex.Single

/**
 * To make an interaction between [TransactionRepositoryImpl] & [ParkingTicketUseCase]
 * */
interface TransactionRepository {

    fun getAllTransaction(): Single<List<ParkingTicket>>

    fun payAmount(data: ParkingTicket): Single<Boolean>

}