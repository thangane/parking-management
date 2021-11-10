package app.com.parkingmanagement.domain.usecase.floors

import app.com.parkingmanagement.domain.model.Vacant
import app.com.parkingmanagement.domain.repository.FloorRepository
import app.com.parkingmanagement.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject


/**
 * An interactor that calls the actual implementation of [FloorViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class GetVacantFloorUseCase @Inject constructor(private val repository: FloorRepository) :
    SingleUseCase<List<Vacant>>() {

    override fun buildUseCaseSingle(obj: Any?): Single<List<Vacant>> {
        return repository.getFloorVacancy(obj as String)
    }

}