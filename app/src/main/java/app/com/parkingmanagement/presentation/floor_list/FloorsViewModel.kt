package app.com.parkingmanagement.presentation.floor_list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.com.parkingmanagement.domain.model.Floor
import app.com.parkingmanagement.domain.usecase.FloorsModuleUseCase

class FloorsViewModel @ViewModelInject constructor(private val floorUseCase: FloorsModuleUseCase) :
    ViewModel() {

    private val TAG = FloorsViewModel::class.java.simpleName
    val floorsReceivedLiveData = MutableLiveData<List<Floor>>()
    val isLoad = MutableLiveData<Boolean>()
    val floorData = MutableLiveData<Floor>()
    val settingsNotExist = MutableLiveData<Boolean?>()

    init {
        isLoad.value = false
        settingsNotExist.value = null
    }

    val floor: Floor? get() = floorData.value

    fun set(floor: Floor) = run { floorData.value = floor }

    fun loadFloors() {
        floorUseCase.getAllFloors().execute(
            obj = null,
            onSuccess = {
                isLoad.value = true
                floorsReceivedLiveData.value = it
            },
            onError = {
                floorsReceivedLiveData.value = emptyList()
                it.printStackTrace()
            },
            onFinished = {
                isLoad.value = true
            }
        )
    }

    fun addFloor(floor: Floor) {
        isLoad.value = false
        floorUseCase.addFloor().execute(
            obj = floor,
            onSuccess = {
                loadFloors()
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun updateFloor(floor: Floor) {
        isLoad.value = false
        floorUseCase.updateFloor().execute(
            obj = floor,
            onSuccess = {
                loadFloors()
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun deleteFloor(id: String) {
        isLoad.value = false
        floorUseCase.deleteFloor().execute(
            obj = id,
            onSuccess = {
                loadFloors()
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    fun getSettings() {
        isLoad.value = false
        floorUseCase.getSettings().execute(
            obj = null,
            onSuccess = {
                isLoad.value = true
                settingsNotExist.value = false
            },
            onError = {
                settingsNotExist.value = true
            },
            onFinished = {
                isLoad.value = true
            }
        )
    }


}