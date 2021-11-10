package app.com.parkingmanagement.presentation.entry_exit

import app.com.parkingmanagement.domain.model.ParkingTicket
import app.com.parkingmanagement.domain.model.PriceCalculation

interface PriceCalculationDialogListener {

    fun applyCoupon(code: String)

    fun calculatePrice(data: PriceCalculation)

    fun payAmount(data: ParkingTicket)

}