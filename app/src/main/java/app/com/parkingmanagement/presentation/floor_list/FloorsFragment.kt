package app.com.parkingmanagement.presentation.floor_list

import android.app.AlertDialog
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
import app.com.parkingmanagement.databinding.FragmentFloorsBinding
import app.com.parkingmanagement.domain.model.Floor
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FloorsFragment : Fragment(), OnFloorsAdapterListener {


    private lateinit var fragmentFloorsBinding: FragmentFloorsBinding

    private var adapter: FloorsAdapter? = null
    private var mCallback: OnFloorListCallback? = null
    private var mContext: Context? = null
    private val floorsViewModel: FloorsViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFloorListCallback) {
            mCallback = context
        } else throw ClassCastException(context.toString() + "must implement OnFloorListCallback!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FloorsAdapter(this)
        floorsViewModel.loadFloors()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFloorsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_floors,
            container,
            false
        )
        mContext = container?.context
        fragmentFloorsBinding.floorsViewModel = floorsViewModel
        fragmentFloorsBinding.floorsRecyclerView.adapter = adapter

        floorsViewModel.isLoad.observe(viewLifecycleOwner, {
            it?.let { visibility ->
                fragmentFloorsBinding.floorsProgressBar.visibility =
                    if (visibility) View.GONE else View.VISIBLE
            }
        })

        floorsViewModel.floorsReceivedLiveData.observe(viewLifecycleOwner, {
            it?.let {
                if (it.isNotEmpty()) {
                    fragmentFloorsBinding.floorListNoData.visibility = View.GONE
                    initRecyclerView(it)
                } else {
                    initRecyclerView(emptyList())
                    fragmentFloorsBinding.floorListNoData.visibility = View.VISIBLE
                }
            }
        })

        fragmentFloorsBinding.addFloorButton.setOnClickListener {
            FloorDetailDialog(mContext!!, null, object : FloorDialogListener {
                override fun onSaveButtonClicked(floor: Floor) {
                    floorsViewModel.addFloor(floor)
                }
            }).show()
        }

        return fragmentFloorsBinding.root
    }

    override fun showFloorDetail(floor: Floor) {
        FloorDetailDialog(mContext!!, floor, object : FloorDialogListener {
            override fun onSaveButtonClicked(floor: Floor) {
                floorsViewModel.updateFloor(floor)
            }
        }).show()
    }

    override fun deleteFloor(floor: Floor) {
        val deleteAlert: AlertDialog.Builder = AlertDialog.Builder(mContext)
        deleteAlert.setMessage("Do you want to delete the floor ?")
        deleteAlert.setCancelable(true)
        deleteAlert.setPositiveButton(
            "Yes"
        ) { _, _ ->
            floorsViewModel.deleteFloor(floor.id)
        }
        deleteAlert.setNegativeButton(
            "No"
        ) { dialog, _ -> dialog.cancel() }
        val deleteAlertDialog: AlertDialog = deleteAlert.create()
        deleteAlertDialog.show()
    }

    private fun initRecyclerView(floors: List<Floor>) {
        Log.i("FloorsFragment", floors.toString())
        adapter?.addData(floors)
    }

    override fun onDetach() {
        super.onDetach()
        adapter = null
        mCallback = null
    }

    companion object {

        val FRAGMENT_NAME = FloorsFragment::class.java.name

        @JvmStatic
        fun newInstance() =
            FloorsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


}