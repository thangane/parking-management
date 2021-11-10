package app.com.parkingmanagement.presentation.transaction_history

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatDialog
import app.com.parkingmanagement.R

class HistoryFilterDialog(
    context: Context,
    private val carCheck: Boolean,
    private val bikeCheck: Boolean,
    private val busCheck: Boolean,
    var dialogListener: HistoryFilterDialogListener
) : AppCompatDialog(context) {


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.dialog_history_filter)

        val dialogLayout = findViewById<LinearLayout>(R.id.history_filter_dialog)!!

        val params = dialogLayout.layoutParams
        params.width = (context.resources.displayMetrics.widthPixels * 0.95).toInt()
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialogLayout.layoutParams = params

        val carCheckBox = findViewById<CheckBox>(R.id.coupon_filter_car)!!
        val bikeCheckBox = findViewById<CheckBox>(R.id.coupon_filter_bike)!!
        val busCheckBox = findViewById<CheckBox>(R.id.coupon_filter_bus)!!

        carCheckBox.isChecked = carCheck
        bikeCheckBox.isChecked = bikeCheck
        busCheckBox.isChecked = busCheck

        findViewById<Button>(R.id.filter_history_button)!!.setOnClickListener {
            dialogListener.onOkButtonClicked(
                carCheckBox.isChecked,
                bikeCheckBox.isChecked,
                busCheckBox.isChecked
            )
            dismiss()
        }

    }

}

