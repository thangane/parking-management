package app.com.parkingmanagement.presentation.transaction_history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import app.com.parkingmanagement.R
import app.com.parkingmanagement.databinding.HolderTransactionHistoryBinding
import app.com.parkingmanagement.domain.model.ParkingTicket
import app.com.parkingmanagement.presentation.transaction_history.TransactionHistoryAdapter.HistoryViewHolder
import java.util.*

/**
 * This class is responsible for converting each data entry [ParkingTicket]
 * into [HistoryViewHolder] that can then be added to the AdapterView.
 *
 */
internal class TransactionHistoryAdapter(val mListener: OnHistoryAdapterListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = TransactionHistoryAdapter::class.java.name
    private val transactionList: MutableList<ParkingTicket> = ArrayList()
    private lateinit var mContext: Context


    /**
     * This method is called right when adapter is created &
     * is used to initialize ViewHolders
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val holderHistoryBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.holder_transaction_history, parent, false
        )
        return HistoryViewHolder(holderHistoryBinding)
    }

    /** It is called for each ViewHolder to bind it to the adapter &
     * This is where we pass data to ViewHolder
     * */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HistoryViewHolder).onBind(getItem(position))
    }

    private fun getItem(position: Int): ParkingTicket {
        return transactionList[position]
    }

    /**
     * This method returns the size of collection that contains the items we want to display
     * */
    override fun getItemCount(): Int {
        return transactionList.size
    }

    fun addData(list: List<ParkingTicket>) {
        this.transactionList.clear()
        this.transactionList.addAll(list)
        notifyDataSetChanged()
    }


    inner class HistoryViewHolder(private val dataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {

        fun onBind(parkingTicket: ParkingTicket) {
            val holderHistoryBinding = dataBinding as HolderTransactionHistoryBinding
            val historyViewModel = HistoryViewModel(parkingTicket)
            val formattedData: ParkingTicket = historyViewModel.parkingTicket
            formattedData.netAmount = formattedData.netAmount
            formattedData.discountedAmount = formattedData.discountedAmount
            formattedData.parkingAmount = formattedData.parkingAmount
            holderHistoryBinding.historyViewModel = historyViewModel.getFormattedData(formattedData)
        }
    }

}
