package app.com.parkingmanagement.domain.usecase.parking_charges

import app.com.parkingmanagement.domain.model.ParkingCharges
import app.com.parkingmanagement.domain.repository.ParkingChargesRepository
import app.com.parkingmanagement.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject


/**
 * An interactor that calls the actual implementation of [ParkingChargesViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class UpdateParkingChargesUseCase @Inject constructor(private val repository: ParkingChargesRepository) :
    SingleUseCase<ParkingCharges>() {

    override fun buildUseCaseSingle(obj: Any?): Single<ParkingCharges> {
        return repository.updateCharges(obj as ParkingCharges)
    }

}