package app.com.parkingmanagement.presentation.transaction_history

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.com.parkingmanagement.domain.model.ParkingTicket
import app.com.parkingmanagement.domain.usecase.TransactionModuleUseCase

class TransactionHistoryViewModel @ViewModelInject constructor(private val useCase: TransactionModuleUseCase) :
    ViewModel() {

    private val TAG = TransactionHistoryViewModel::class.java.simpleName
    val historyLiveData = MutableLiveData<List<ParkingTicket>>()
    val isLoad = MutableLiveData<Boolean>()
    val isPaid = MutableLiveData<Boolean?>()

    init {
        isLoad.value = false
        isPaid.value = null
    }


    fun loadHistory() {
        isLoad.value = false
        useCase.getAllTransaction().execute(
            obj = null,
            onSuccess = {
                isLoad.value = true
                historyLiveData.value = it
            },
            onError = {
                it.printStackTrace()
            },
            onFinished = {
                isLoad.value = true
            }
        )
    }

    fun payAmount(parkingTicket: ParkingTicket) {
        isLoad.value = false
        useCase.payAmount().execute(
            obj = parkingTicket,
            onSuccess = {
                isLoad.value = true
                isPaid.value = true
            },
            onError = {
                isPaid.value = false
                it.printStackTrace()
            },
            onFinished = {
                isLoad.value = true
            }
        )
    }


}