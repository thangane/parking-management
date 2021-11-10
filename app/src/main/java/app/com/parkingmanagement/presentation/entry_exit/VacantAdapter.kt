package app.com.parkingmanagement.presentation.entry_exit

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import app.com.parkingmanagement.R
import app.com.parkingmanagement.databinding.HolderVacancyBinding
import app.com.parkingmanagement.domain.model.Vacant
import app.com.parkingmanagement.presentation.entry_exit.VacantAdapter.VacantViewHolder
import java.util.*

/**
 * This class is responsible for converting each data entry [Vacant]
 * into [VacantViewHolder] that can then be added to the AdapterView.
 *
 */
internal class VacantAdapter(val mListener: OnVacantAdapterListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = VacantAdapter::class.java.name
    private val vacantList: MutableList<Vacant> = ArrayList()
    private lateinit var mContext: Context


    /**
     * This method is called right when adapter is created &
     * is used to initialize ViewHolders
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val holderVacantBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.holder_vacancy, parent, false
        )
        return VacantViewHolder(holderVacantBinding)
    }

    /** It is called for each ViewHolder to bind it to the adapter &
     * This is where we pass data to ViewHolder
     * */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VacantViewHolder).onBind(getItem(position))
    }

    private fun getItem(position: Int): Vacant {
        return vacantList[position]
    }

    /**
     * This method returns the size of collection that contains the items we want to display
     * */
    override fun getItemCount(): Int {
        return vacantList.size
    }

    fun addData(vacantList: List<Vacant>) {
        this.vacantList.clear()
        this.vacantList.addAll(vacantList)
        notifyDataSetChanged()
    }


    inner class VacantViewHolder(private var dataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {

        fun onBind(vacant: Vacant) {
            val vacantBinding = dataBinding as HolderVacancyBinding
            val vacantViewModel = VacantFloorViewModel(vacant)
            vacantBinding.vacantViewModel = vacantViewModel
        }
    }

}
