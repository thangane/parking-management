package app.com.parkingmanagement.presentation.transaction_history

interface HistoryFilterDialogListener {

    fun onOkButtonClicked(showUsed: Boolean, showUnused: Boolean, showInvalidated: Boolean)
}