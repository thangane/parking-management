package app.com.parkingmanagement.domain.usecase

import app.com.parkingmanagement.domain.usecase.parking_ticket.*
import javax.inject.Inject


/**
 * An interactor that calls the actual implementation of [EntryViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class ParkingTicketModuleUseCase @Inject constructor(
    private val checkIfEntryExitsUseCase: CheckIfEntryExitsUseCase,
    private val generateParkingTicketUseCase: GenerateParkingTicketUseCase,
    private val getParkingTicketUseCase: GetParkingTicketUseCase,
    private val priceCalculationUseCase: PriceCalculationUseCase,
    private val getReservationDetailsUseCase: GetReservationDetailsUseCase,
    private val deleteParkingTicketUseCase: DeleteParkingTicketUseCase
) {

    fun checkIfEntryExits(): CheckIfEntryExitsUseCase {
        return checkIfEntryExitsUseCase
    }

    fun generateTicket(): GenerateParkingTicketUseCase {
        return generateParkingTicketUseCase
    }

    fun getTicket(): GetParkingTicketUseCase {
        return getParkingTicketUseCase
    }

    fun getReservationDetails(): GetReservationDetailsUseCase {
        return getReservationDetailsUseCase
    }

    fun calculatePrice(): PriceCalculationUseCase {
        return priceCalculationUseCase
    }

    fun deleteReservation(): DeleteParkingTicketUseCase {
        return deleteParkingTicketUseCase
    }

}