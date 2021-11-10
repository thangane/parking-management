package app.com.parkingmanagement.domain.usecase.parking_ticket

import app.com.parkingmanagement.domain.repository.ParkingTicketRepository
import app.com.parkingmanagement.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject


/**
 * An interactor that calls the actual implementation of [EntryViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class CheckIfEntryExitsUseCase @Inject constructor(private val repository: ParkingTicketRepository) :
    SingleUseCase<Boolean>() {

    override fun buildUseCaseSingle(obj: Any?): Single<Boolean> {
        return repository.checkIfEntryExits(obj as String)
    }


}