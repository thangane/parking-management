package app.com.parkingmanagement.presentation.entry_exit


interface OnEntryExitCallback {
    fun navigateToVehicleEntryPage()

    fun navigateToVehicleExitPage()

    fun navigateToVehicleReservePage()

    fun navigateToVehicleUnReservePage()

    fun navigateToVehicleVacancyPage()

}