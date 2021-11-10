package app.com.parkingmanagement.presentation.floor_list

import app.com.parkingmanagement.domain.model.Floor

/**
 * To make an interaction between [FloorsAdapter] & [FloorsFragment]
 * */
interface OnFloorsAdapterListener {

    fun showFloorDetail(floor: Floor)

    fun deleteFloor(floor: Floor)
}