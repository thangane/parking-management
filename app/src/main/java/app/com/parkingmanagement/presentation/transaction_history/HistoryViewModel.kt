package app.com.parkingmanagement.presentation.transaction_history

import androidx.lifecycle.MutableLiveData
import app.com.parkingmanagement.domain.model.ParkingTicket

class HistoryViewModel(val parkingTicket: ParkingTicket) {

    private val TAG = HistoryViewModel::class.java.simpleName
    private val historyData = MutableLiveData<ParkingTicket>()


    init {
        historyData.value = parkingTicket
    }

    fun getFormattedData(parkingTicket: ParkingTicket): HistoryViewModel {
        historyData.value = parkingTicket
        return this
    }

}