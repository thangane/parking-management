package app.com.parkingmanagement.presentation.entry_exit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.com.parkingmanagement.domain.model.Vacant
import app.com.parkingmanagement.domain.usecase.FloorsModuleUseCase

class VacantViewModel @ViewModelInject constructor(private val useCase: FloorsModuleUseCase) :
    ViewModel() {

    private val TAG = VacantViewModel::class.java.simpleName
    val vacantLiveData = MutableLiveData<List<Vacant>>()
    val isLoad = MutableLiveData<Boolean>()
    val floorVacantData = MutableLiveData<Vacant>()

    init {
        isLoad.value = false
    }

    val vacant: Vacant? get() = floorVacantData.value

    fun set(vacant: Vacant) = run { floorVacantData.value = vacant }

    fun setLoad(load: Boolean) {
        isLoad.value = load
    }

    fun getVacancyDetails(entryTime: String) {
        isLoad.value = false
        useCase.getVacancy().execute(
            obj = entryTime,
            onSuccess = {
                isLoad.value = true
                vacantLiveData.value = it
            },
            onError = {
                vacantLiveData.value = emptyList()
                it.printStackTrace()
            },
            onFinished = {
                isLoad.value = true
            }
        )
    }

}