package app.com.parkingmanagement.presentation.entry_exit

import androidx.lifecycle.MutableLiveData
import app.com.parkingmanagement.domain.model.Vacant

class VacantFloorViewModel(val vacancy: Vacant) {

    private val TAG = VacantFloorViewModel::class.java.simpleName
    private val vacantData = MutableLiveData<Vacant>()

    init {
        vacantData.value = vacancy
    }

}