package app.com.parkingmanagement.presentation.coupon

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatDialog
import app.com.parkingmanagement.R

class CouponFilterDialog(
    context: Context,
    isUnused: Boolean,
    isUsed: Boolean,
    isInvalidated: Boolean,
    var couponFilterDialogListener: CouponFilterDialogListener
) : AppCompatDialog(context) {

    private val checkUnused = isUnused
    private val checkUsed = isUsed
    private val checkInvalidated = isInvalidated

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.dialog_coupon_filter)

        val dialogLayout = findViewById<LinearLayout>(R.id.coupon_filter_dialog)!!

        val params = dialogLayout.layoutParams
        params.width = (context.resources.displayMetrics.widthPixels * 0.95).toInt()
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialogLayout.layoutParams = params

        val showUsed = findViewById<CheckBox>(R.id.coupon_filter_used)!!
        val showUnused = findViewById<CheckBox>(R.id.coupon_filter_unused)!!
        val showInvalidated = findViewById<CheckBox>(R.id.coupon_filter_invalidated)!!
        showUsed.isChecked = checkUsed
        showUnused.isChecked = checkUnused
        showInvalidated.isChecked = checkInvalidated

        findViewById<Button>(R.id.filter_coupon_button)!!.setOnClickListener {
            couponFilterDialogListener.onOkButtonClicked(
                showUsed.isChecked,
                showUnused.isChecked,
                showInvalidated.isChecked
            )
            dismiss()
        }

    }

}

