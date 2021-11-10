package app.com.parkingmanagement.presentation.entry_exit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.com.parkingmanagement.domain.model.Floor
import app.com.parkingmanagement.domain.model.GenerateTicket
import app.com.parkingmanagement.domain.model.ParkingTicket
import app.com.parkingmanagement.domain.model.PriceCalculation
import app.com.parkingmanagement.domain.usecase.ParkingTicketModuleUseCase

class EntryViewModel @ViewModelInject constructor(private val useCase: ParkingTicketModuleUseCase) :
    ViewModel() {

    private val TAG = EntryViewModel::class.java.simpleName
    val parkingTicketLiveData = MutableLiveData<ParkingTicket>()
    val reservationDetailsLiveData = MutableLiveData<List<Floor>>()
    val isReservationLoaded = MutableLiveData<Boolean>()
    val isLoad = MutableLiveData<Boolean>()
    val isPaid = MutableLiveData<Boolean>()

    init {
        isLoad.value = false
        isPaid.value = false
        isReservationLoaded.value = false
    }

    fun setLoad(load: Boolean) {
        isLoad.value = load
    }

    fun setPaid(paid: Boolean) {
        isPaid.value = paid
    }

    fun isPaid(): Boolean {
        return isPaid.value ?: false
    }


    fun checkIfEntryExists(entryTime: String) {
        isLoad.value = false
        useCase.checkIfEntryExits().execute(
            obj = entryTime,
            onSuccess = {
                isLoad.value = true
            },
            onError = {
                it.printStackTrace()
            },
            onFinished = {
                isLoad.value = true
            }
        )
    }

    fun generateTicket(data: GenerateTicket) {
        isLoad.value = false
        useCase.generateTicket().execute(
            obj = data,
            onSuccess = {
                isLoad.value = true
                parkingTicketLiveData.value = it
                isReservationLoaded.value = false
            },
            onError = {
                parkingTicketLiveData.value = null
                it.printStackTrace()
            },
            onFinished = {
                isLoad.value = true
            }
        )
    }

    fun getReservationDetails(entryTime: String) {
        isLoad.value = false
        isReservationLoaded.value = false
        useCase.checkIfEntryExits().execute(
            obj = entryTime,
            onSuccess = {
                useCase.getReservationDetails().execute(
                    obj = entryTime,
                    onSuccess = {
                        isLoad.value = true
                        isReservationLoaded.value = true
                        reservationDetailsLiveData.value = it
                    },
                    onError = {
                        reservationDetailsLiveData.value = null
                        it.printStackTrace()
                    },
                    onFinished = {
                        isLoad.value = true
                    }
                )
            },
            onError = {
                it.printStackTrace()
            },
            onFinished = {
                isLoad.value = true
            }
        )


    }

    fun getTicketById(id: String) {
        isLoad.value = false
        useCase.getTicket().execute(
            obj = id,
            onSuccess = {
                isLoad.value = true
                parkingTicketLiveData.value = it
            },
            onError = {
                parkingTicketLiveData.value = null
                it.printStackTrace()
            },
            onFinished = {
                isLoad.value = true
            }
        )
    }

    fun deleteReservation(id: String) {
        isLoad.value = false
        useCase.deleteReservation().execute(
            obj = id,
            onSuccess = {
                parkingTicketLiveData.value = null
                isLoad.value = true
            },
            onError = {
                it.printStackTrace()
            },
            onFinished = {
                isLoad.value = true
            }
        )
    }

    fun calculatePrice(priceCalculation: PriceCalculation) {
        isLoad.value = false
        useCase.calculatePrice().execute(
            obj = priceCalculation,
            onSuccess = {
                isLoad.value = true
                parkingTicketLiveData.value = it
            },
            onError = {
                parkingTicketLiveData.value = null
                it.printStackTrace()
            },
            onFinished = {
                isLoad.value = true
            }
        )
    }


}