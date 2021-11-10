package app.com.parkingmanagement.presentation.floor_list

import androidx.lifecycle.MutableLiveData
import app.com.parkingmanagement.domain.model.Floor

class FloorViewModel(val floor: Floor) {

    private val TAG = FloorViewModel::class.java.simpleName
    private val floorData = MutableLiveData<Floor>()

    init {
        floorData.value = floor
    }

}