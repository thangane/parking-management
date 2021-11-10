package app.com.parkingmanagement.presentation.floor_list

import app.com.parkingmanagement.domain.model.Floor

interface FloorDialogListener {

    fun onSaveButtonClicked(floor: Floor)
}