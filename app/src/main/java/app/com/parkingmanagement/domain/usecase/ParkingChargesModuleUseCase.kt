package app.com.parkingmanagement.domain.usecase

import app.com.parkingmanagement.domain.usecase.parking_charges.GetParkingChargesUseCase
import app.com.parkingmanagement.domain.usecase.parking_charges.UpdateParkingChargesUseCase
import javax.inject.Inject


/**
 * An interactor that calls the actual implementation of [ParkingChargesViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class ParkingChargesModuleUseCase @Inject constructor(
    private val getParkingChargesUseCase: GetParkingChargesUseCase,
    private val updateParkingChargesUseCase: UpdateParkingChargesUseCase,
) {

    fun getCharges(): GetParkingChargesUseCase {
        return getParkingChargesUseCase
    }

    fun updateCharges(): UpdateParkingChargesUseCase {
        return updateParkingChargesUseCase
    }

}