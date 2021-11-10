package app.com.parkingmanagement.presentation.coupon

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.com.parkingmanagement.R
import app.com.parkingmanagement.databinding.FragmentCouponsBinding
import app.com.parkingmanagement.domain.model.Coupon
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CouponFragment : Fragment(), OnCouponsAdapterListener {

    private lateinit var fragmentCouponsBinding: FragmentCouponsBinding

    private var adapter: CouponsAdapter? = null
    private var mCallback: OnCouponCallback? = null
    private var mContext: Context? = null
    private val couponsViewModel: CouponsViewModel by viewModels()
    private var showUnused: Boolean = true
    private var showUsed: Boolean = false
    private var showInvalidated: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCouponCallback) {
            mCallback = context
        } else throw ClassCastException(context.toString() + "must implement OnCouponCallback!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CouponsAdapter(this)
        couponsViewModel.loadCoupons()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentCouponsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_coupons,
            container,
            false
        )
        mContext = container?.context
        fragmentCouponsBinding.couponsViewModel = couponsViewModel
        fragmentCouponsBinding.couponsRecyclerView.adapter = adapter

        couponsViewModel.isLoad.observe(viewLifecycleOwner, {
            it?.let { visibility ->
                fragmentCouponsBinding.couponsProgressBar.visibility =
                    if (visibility) View.GONE else View.VISIBLE
            }
        })

        couponsViewModel.couponsReceivedLiveData.observe(viewLifecycleOwner, {
            it?.let {
                var couponList = mutableListOf<Coupon>()
                if (showUsed) {
                    couponList.addAll(it.filter { coupon -> (coupon.used && !coupon.invalidated) })
                }
                if (showUnused) {
                    couponList.addAll(it.filter { coupon -> (!coupon.used && !coupon.invalidated) })
                }
                if (showInvalidated) {
                    couponList.addAll(it.filter { coupon -> coupon.invalidated })
                }
                initRecyclerView(couponList)
            }
        })

        fragmentCouponsBinding.generateCouponsButton.setOnClickListener {
            CouponGenerateDialog(
                mContext!!,
                object : CouponGenerateDialogListener {
                    override fun onGenerateButtonClicked(couponCount: String, endDate: String) {
                        couponsViewModel.generateCoupons(couponCount, endDate)
                    }

                }).show()
        }

        fragmentCouponsBinding.couponFilter.setOnClickListener {
            CouponFilterDialog(
                mContext!!,
                showUnused,
                showUsed,
                showInvalidated,
                object : CouponFilterDialogListener {
                    override fun onOkButtonClicked(
                        isUsed: Boolean,
                        isUnused: Boolean,
                        isInvalidated: Boolean
                    ) {
                        showUsed = isUsed
                        showUnused = isUnused
                        showInvalidated = isInvalidated
                        couponsViewModel.loadCoupons()
                    }
                }).show()
        }

        return fragmentCouponsBinding.root
    }

    private fun initRecyclerView(coupons: List<Coupon>) {
        Log.i("CouponsFragment", coupons.toString())
        adapter?.addData(coupons)
    }

    override fun onDetach() {
        super.onDetach()
        adapter = null
        mCallback = null
    }

    override fun deleteCoupon(id: String) {
        couponsViewModel.deleteCoupon(id)
    }

    override fun invalidateCoupon(id: String) {
        couponsViewModel.invalidateCoupon(id)
    }


    companion object {

        val FRAGMENT_NAME = CouponFragment::class.java.name

        @JvmStatic
        fun newInstance() =
            CouponFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}