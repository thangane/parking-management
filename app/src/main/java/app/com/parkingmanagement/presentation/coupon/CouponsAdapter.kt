package app.com.parkingmanagement.presentation.coupon

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import app.com.parkingmanagement.R
import app.com.parkingmanagement.databinding.HolderCouponBinding
import app.com.parkingmanagement.domain.model.Coupon
import app.com.parkingmanagement.presentation.coupon.CouponsAdapter.CouponViewHolder
import java.util.*

/**
 * This class is responsible for converting each data entry [Coupon]
 * into [CouponViewHolder] that can then be added to the AdapterView.
 *
 */
internal class CouponsAdapter(val mListener: OnCouponsAdapterListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = CouponsAdapter::class.java.name
    private val coupons: MutableList<Coupon> = ArrayList()
    private lateinit var mContext: Context


    /**
     * This method is called right when adapter is created &
     * is used to initialize ViewHolders
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val holderCouponBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.holder_coupon, parent, false
        )
        return CouponViewHolder(holderCouponBinding)
    }

    /** It is called for each ViewHolder to bind it to the adapter &
     * This is where we pass data to ViewHolder
     * */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CouponViewHolder).onBind(getItem(position))
    }

    private fun getItem(position: Int): Coupon {
        return coupons[position]
    }

    /**
     * This method returns the size of collection that contains the items we want to display
     * */
    override fun getItemCount(): Int {
        return coupons.size
    }

    fun addData(list: List<Coupon>) {
        this.coupons.clear()
        this.coupons.addAll(list)
        notifyDataSetChanged()
    }


    inner class CouponViewHolder(private val dataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {

        fun onBind(coupon: Coupon) {
            val holderCouponBinding = dataBinding as HolderCouponBinding
            val couponViewModel = CouponViewModel(coupon)
            holderCouponBinding.couponViewModel = couponViewModel
            holderCouponBinding.couponCardView.setBackgroundColor(getColor(coupon))
            setButtonVisibility(coupon, holderCouponBinding)
            holderCouponBinding.couponDeleteIcon.setOnClickListener {
                mListener.deleteCoupon(coupon.id)
            }
            holderCouponBinding.couponInvalidateIcon.setOnClickListener {
                mListener.invalidateCoupon(coupon.id)
            }

        }

        private fun setButtonVisibility(coupon: Coupon, dataBinding: HolderCouponBinding) {
            if (coupon.invalidated) {
                dataBinding.couponDeleteIcon.visibility = View.GONE
                dataBinding.couponInvalidateIcon.visibility = View.GONE
            } else if (coupon.used) {
                dataBinding.couponDeleteIcon.visibility = View.GONE
                dataBinding.couponInvalidateIcon.visibility = View.GONE
            } else {
                dataBinding.couponDeleteIcon.visibility = View.VISIBLE
                dataBinding.couponInvalidateIcon.visibility = View.VISIBLE
            }
        }

        private fun getColor(coupon: Coupon): Int {
            return if (coupon.invalidated) {
                ContextCompat.getColor(mContext, R.color.invalidated_color)
            } else if (coupon.used) {
                ContextCompat.getColor(mContext, R.color.used_color)
            } else {
                ContextCompat.getColor(mContext, R.color.unused_color)
            }

        }
    }

}
