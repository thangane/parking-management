package app.com.parkingmanagement.presentation.entry_exit

import android.app.DatePickerDialog
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
import app.com.parkingmanagement.databinding.FragmentVehicleVacancyBinding
import app.com.parkingmanagement.domain.model.Vacant
import app.com.parkingmanagement.util.DateConverter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class VehicleVacancyFragment : Fragment(), OnVacantAdapterListener {

    private lateinit var dataBinding: FragmentVehicleVacancyBinding
    private var mCallback: OnEntryExitCallback? = null
    private var mContext: Context? = null
    private var adapter: VacantAdapter? = null
    private val vacantViewModel: VacantViewModel by viewModels()

    private var selectedDate = Date().time

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEntryExitCallback) {
            mCallback = context
        } else throw ClassCastException(context.toString() + "must implement OnEntryExitCallback!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = VacantAdapter(this)
        vacantViewModel.getVacancyDetails(selectedDate.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_vehicle_vacancy,
            container,
            false
        )
        mContext = container?.context
        dataBinding.vacancyViewModel = vacantViewModel
        dataBinding.vacancyRecyclerView.adapter = adapter

        vacantViewModel.isLoad.observe(viewLifecycleOwner, {
            it?.let { visibility ->
                dataBinding.vacancyProgressBar.visibility =
                    if (visibility) View.GONE else View.VISIBLE
            }
        })
        vacantViewModel.isLoad.observe(viewLifecycleOwner, {
            it?.let { visibility ->
                dataBinding.vacancyProgressBar.visibility =
                    if (visibility) View.GONE else View.VISIBLE
            }
        })
        vacantViewModel.vacantLiveData.observe(viewLifecycleOwner, {
            it?.let {
                if (it.isNotEmpty()) {
                    dataBinding.vacancyListNoData.visibility = View.GONE
                    initRecyclerView(it)
                } else {
                    initRecyclerView(emptyList())
                    dataBinding.vacancyListNoData.visibility = View.VISIBLE
                }
            }
        })
        val vacantDate = dataBinding.vacantDate

        vacantDate.text = DateConverter.getCurrentDateString()
        selectedDate = Date().time

        vacantDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                mContext!!,
                { view, year, monthOfYear, dayOfMonth ->
                    val result = Calendar.getInstance()
                    result.set(year, monthOfYear, dayOfMonth)
                    selectedDate = result.time.time
                    vacantDate.text = DateConverter.convertToDateString(result.time)
                    vacantViewModel.getVacancyDetails(selectedDate.toString())
                }, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.datePicker.minDate = calendar.timeInMillis
            datePicker.show()
        }
        return dataBinding.root
    }

    private fun initRecyclerView(vacantList: List<Vacant>) {
        Log.i("VehicleVacancyFragment", vacantList.toString())
        adapter?.addData(vacantList)
    }

    override fun onDetach() {
        super.onDetach()
        adapter = null
        mCallback = null
    }

    companion object {

        val FRAGMENT_NAME = VehicleVacancyFragment::class.java.name

        @JvmStatic
        fun newInstance() =
            VehicleVacancyFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
