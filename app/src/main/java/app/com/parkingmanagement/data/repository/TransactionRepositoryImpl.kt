package app.com.parkingmanagement.data.repository

import app.com.parkingmanagement.data.source.RetrofitService
import app.com.parkingmanagement.domain.model.ParkingTicket
import app.com.parkingmanagement.domain.repository.TransactionRepository
import io.reactivex.Single

class TransactionRepositoryImpl(
    private val retrofitService: RetrofitService
):TransactionRepository {


    override fun getAllTransaction(): Single<List<ParkingTicket>> {
        return retrofitService.getAllTransaction()
    }

    override fun payAmount(data: ParkingTicket): Single<Boolean> {
        return retrofitService.payAmount(data)
    }
}