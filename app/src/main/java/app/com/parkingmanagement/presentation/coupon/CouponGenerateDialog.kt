package app.com.parkingmanagement.presentation.coupon

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import app.com.parkingmanagement.R
import app.com.parkingmanagement.util.DateConverter
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class CouponGenerateDialog(
    context: Context,
    var couponGenerateDialogListener: CouponGenerateDialogListener
) : AppCompatDialog(context) {

    private var selectedDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.dialog_generate_coupon)

        val dialogLayout = findViewById<LinearLayout>(R.id.generate_coupon_dialog)!!

        val params = dialogLayout.layoutParams
        params.width = context.resources.displayMetrics.widthPixels
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialogLayout.layoutParams = params

        val couponCountTextInput = findViewById<TextInputEditText>(R.id.coupon_count_text_input)!!
        val endDate = findViewById<TextView>(R.id.coupon_end_date)!!

        endDate.text = DateConverter.getCurrentDateString()
        selectedDate = Date()

        endDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                context,
                { view, year, monthOfYear, dayOfMonth ->
                    val result = Calendar.getInstance()
                    result.set(year, monthOfYear, dayOfMonth)
                    selectedDate = result.time
                    endDate.text = DateConverter.convertToDateString(result.time)
                }, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.datePicker.minDate = calendar.timeInMillis
            datePicker.show()
        }

        findViewById<Button>(R.id.generate_dialog_button)!!.setOnClickListener {
            val couponCount = couponCountTextInput.text.toString()
            val endDateValue = selectedDate!!.time
            couponGenerateDialogListener.onGenerateButtonClicked(
                couponCount,
                endDateValue.toString()
            )
            dismiss()
        }

    }

    fun DatePicker.getDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        return calendar.time
    }

}


