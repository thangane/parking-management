package app.com.parkingmanagement.presentation.floor_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import app.com.parkingmanagement.R
import app.com.parkingmanagement.databinding.HolderFloorBinding
import app.com.parkingmanagement.domain.model.Floor
import app.com.parkingmanagement.presentation.floor_list.FloorsAdapter.FloorViewHolder
import java.util.*

/**
 * This class is responsible for converting each data entry [Floor]
 * into [FloorViewHolder] that can then be added to the AdapterView.
 *
 */
internal class FloorsAdapter(val mListener: OnFloorsAdapterListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = FloorsAdapter::class.java.name
    private val floors: MutableList<Floor> = ArrayList()
    private lateinit var mContext: Context


    /**
     * This method is called right when adapter is created &
     * is used to initialize ViewHolders
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val holderFloorBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.holder_floor, parent, false
        )
        return FloorViewHolder(holderFloorBinding)
    }

    /** It is called for each ViewHolder to bind it to the adapter &
     * This is where we pass data to ViewHolder
     * */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FloorViewHolder).onBind(getItem(position))
    }

    private fun getItem(position: Int): Floor {
        return floors[position]
    }

    /**
     * This method returns the size of collection that contains the items we want to display
     * */
    override fun getItemCount(): Int {
        return floors.size
    }

    fun addData(list: List<Floor>) {
        this.floors.clear()
        this.floors.addAll(list)
        notifyDataSetChanged()
    }


    inner class FloorViewHolder(private val dataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {

        fun onBind(floor: Floor) {
            val holderFloorBinding = dataBinding as HolderFloorBinding
            val floorViewModel = FloorViewModel(floor)
            holderFloorBinding.floorViewModel = floorViewModel

            holderFloorBinding.floorDetail.setOnClickListener {
                mListener.showFloorDetail(floor)
            }
            holderFloorBinding.floorDeleteIcon.setOnClickListener {
                mListener.deleteFloor(floor)
            }

        }
    }

}
