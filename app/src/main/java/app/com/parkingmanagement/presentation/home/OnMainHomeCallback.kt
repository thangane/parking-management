package app.com.parkingmanagement.presentation.home


interface OnMainHomeCallback {

    fun navigateToSettingsPage()

    fun navigateToTransactionPage()

    fun navigateToEntryExitPage()

    fun navigateToFloorListPage()

    fun navigateToCouponListPage()

    fun navigateToParkingChargesPage()

    fun navigateToEntryPage()

    fun navigateToExitPage()

    fun navigateToReservePage()

    fun navigateToUnReservePage()

    fun navigateToVacancyListPage()

    fun selectSettingsPage()
}