package app.com.parkingmanagement.presentation.parking_charges

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.com.parkingmanagement.domain.model.ParkingCharges
import app.com.parkingmanagement.domain.usecase.ParkingChargesModuleUseCase

class ParkingChargesViewModel @ViewModelInject constructor(private val useCase: ParkingChargesModuleUseCase) :
    ViewModel() {

    private val TAG = ParkingChargesViewModel::class.java.simpleName
    val receivedLiveData = MutableLiveData<ParkingCharges>()
    val isLoad = MutableLiveData<Boolean>()

    init {
        isLoad.value = false
    }

    fun loadData() {
        isLoad.value = false
        useCase.getCharges().execute(
            obj = null,
            onSuccess = {
                isLoad.value = true
                receivedLiveData.value = it
            },
            onError = {
                receivedLiveData.value = ParkingCharges("0", "0")
                it.printStackTrace()
            }
        )
    }

    fun updateData(data: ParkingCharges) {
        isLoad.value = false
        useCase.updateCharges().execute(
            obj = data,
            onSuccess = {
                isLoad.value = true
                receivedLiveData.value = it
            },
            onError = {
                it.printStackTrace()
            },
        )
    }

}