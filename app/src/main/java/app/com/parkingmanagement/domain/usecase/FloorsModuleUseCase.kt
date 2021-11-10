package app.com.parkingmanagement.domain.usecase

import app.com.parkingmanagement.domain.usecase.floors.*
import javax.inject.Inject


/**
 * An interactor that calls the actual implementation of [FloorViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class FloorsModuleUseCase @Inject constructor(
    private val getFloorsUseCase: GetFloorsUseCase,
    private val updateFloorUseCase: UpdateFloorUseCase,
    private val addFloorUseCase: AddFloorUseCase,
    private val deleteFloorUseCase: DeleteFloorUseCase,
    private val getVacantFloorUseCase: GetVacantFloorUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
) {

    fun getAllFloors(): GetFloorsUseCase {
        return getFloorsUseCase
    }

    fun updateFloor(): UpdateFloorUseCase {
        return updateFloorUseCase
    }

    fun addFloor(): AddFloorUseCase {
        return addFloorUseCase
    }

    fun deleteFloor(): DeleteFloorUseCase {
        return deleteFloorUseCase
    }

    fun getVacancy(): GetVacantFloorUseCase {
        return getVacantFloorUseCase
    }

    fun getSettings(): GetSettingsUseCase {
        return getSettingsUseCase
    }

}