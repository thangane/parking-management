package app.com.parkingmanagement.domain.usecase.floors

import app.com.parkingmanagement.domain.repository.FloorRepository
import app.com.parkingmanagement.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject


/**
 * An interactor that calls the actual implementation of [FloorViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class DeleteFloorUseCase @Inject constructor(private val repository: FloorRepository) :
    SingleUseCase<Boolean>() {

    override fun buildUseCaseSingle(obj: Any?): Single<Boolean> {
        return repository.deleteFloor(obj as String)
    }

}