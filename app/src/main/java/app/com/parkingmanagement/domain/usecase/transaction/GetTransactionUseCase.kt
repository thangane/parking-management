package app.com.parkingmanagement.domain.usecase.transaction

import app.com.parkingmanagement.domain.model.ParkingTicket
import app.com.parkingmanagement.domain.repository.TransactionRepository
import app.com.parkingmanagement.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject


/**
 * An interactor that calls the actual implementation of [TransactionViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class GetTransactionUseCase @Inject constructor(private val repository: TransactionRepository) :
    SingleUseCase<List<ParkingTicket>>() {

    override fun buildUseCaseSingle(obj: Any?): Single<List<ParkingTicket>> {
        return repository.getAllTransaction()
    }


}