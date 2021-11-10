package app.com.parkingmanagement.presentation.entry_exit

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDialog
import androidx.lifecycle.LifecycleOwner
import app.com.parkingmanagement.R
import app.com.parkingmanagement.domain.model.ParkingTicket
import app.com.parkingmanagement.domain.model.PriceCalculation
import app.com.parkingmanagement.presentation.coupon.CouponsViewModel
import app.com.parkingmanagement.presentation.transaction_history.TransactionHistoryViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class PriceCalculationDialog(
    context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val entryViewModel: EntryViewModel,
    private val couponViewModel: CouponsViewModel,
    private val transactionViewModel: TransactionHistoryViewModel
) : AppCompatDialog(context) {


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.dialog_price_calculation)

        val dialogLayout = findViewById<LinearLayout>(R.id.price_calculation_dialog)!!

        val params = dialogLayout.layoutParams
        params.width = context.resources.displayMetrics.widthPixels
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialogLayout.layoutParams = params

        val couponCodeTextInput = findViewById<TextInputEditText>(R.id.coupon_code_text_input)!!

        couponViewModel.isValidCoupon.observe(lifecycleOwner, {
            it?.let {
                hideProgress()
                if (it) {
                    Toast.makeText(context, R.string.valid_coupon, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, R.string.invalid_coupon, Toast.LENGTH_SHORT).show()
                }
            }
        })

        entryViewModel.parkingTicketLiveData.observe(lifecycleOwner, {
            it?.let {
                hideProgress()
                if (!it.exitTime.isNullOrEmpty()) {
                    setCalculatedData(it)
                }
            }
        })
        transactionViewModel.isPaid.observe(lifecycleOwner, {
            it?.let {
                hideProgress()
                entryViewModel.parkingTicketLiveData.value = null
                if (it) {
                    dismiss()
                } else {
                    Toast.makeText(context, R.string.error_try_again, Toast.LENGTH_SHORT).show()
                }
            }
        })

        findViewById<Button>(R.id.apply_coupon_dialog_button)!!.setOnClickListener {
            val couponCode = couponCodeTextInput.text.toString()
            if (couponCode.isEmpty()) {
                Toast.makeText(context, R.string.invalid_coupon, Toast.LENGTH_SHORT).show()
            } else {
                showProgress()
                couponViewModel.useCoupon(couponCodeTextInput.text.toString())
            }
        }
        findViewById<Button>(R.id.calculate_dialog_button)!!.setOnClickListener {
            showProgress()
            entryViewModel.calculatePrice(
                PriceCalculation(
                    entryViewModel.parkingTicketLiveData.value!!,
                    couponViewModel.couponData.value
                )
            )
        }
        findViewById<Button>(R.id.pay_dialog_button)!!.setOnClickListener {
            showProgress()
            transactionViewModel.payAmount(entryViewModel.parkingTicketLiveData.value!!)
        }

    }

    private fun setCalculatedData(parkingTicket: ParkingTicket) {
        findViewById<MaterialButton>(R.id.pay_dialog_button)?.visibility = View.VISIBLE
        findViewById<TextView>(R.id.total_amount_label)?.text = "₹ ${parkingTicket.parkingAmount}"
        findViewById<TextView>(R.id.discounted_price_label)?.text =
            "₹ ${parkingTicket.discountedAmount}"
        findViewById<TextView>(R.id.net_amount_label)?.text = "₹ ${parkingTicket.netAmount}"
    }

    private fun showProgress() {
        findViewById<ProgressBar>(R.id.price_calculation_progress_bar)?.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        findViewById<ProgressBar>(R.id.price_calculation_progress_bar)?.visibility = View.GONE
    }


}


