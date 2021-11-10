package app.com.parkingmanagement.domain.usecase.parking_ticket

import app.com.parkingmanagement.domain.model.ParkingTicket
import app.com.parkingmanagement.domain.repository.ParkingTicketRepository
import app.com.parkingmanagement.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject


/**
 * An interactor that calls the actual implementation of [EntryViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class GetParkingTicketUseCase @Inject constructor(private val repository: ParkingTicketRepository) :
    SingleUseCase<ParkingTicket>() {

    override fun buildUseCaseSingle(obj: Any?): Single<ParkingTicket> {
        return repository.getTicketById(obj as String)
    }


}