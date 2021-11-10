package app.com.parkingmanagement.presentation.entry_exit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.com.parkingmanagement.R
import app.com.parkingmanagement.presentation.floor_list.FloorsFragment
import app.com.parkingmanagement.presentation.floor_list.FloorsViewModel
import app.com.parkingmanagement.presentation.home.OnMainHomeCallback
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryExitFragment : Fragment() {

    private var mCallback: OnMainHomeCallback? = null


    private val floorsViewModel: FloorsViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMainHomeCallback) {
            mCallback = context
        } else throw ClassCastException(context.toString() + "must implement OnFloorListCallback!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        floorsViewModel.getSettings()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_entry_exit, null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = requireView().findViewById<ProgressBar>(R.id.entry_exit_progress_bar)
        floorsViewModel.isLoad.observe(viewLifecycleOwner, {
            it?.let {
                if (it) progressBar.visibility = View.GONE else progressBar.visibility =
                    View.VISIBLE
            }
        })
        floorsViewModel.settingsNotExist.observe(viewLifecycleOwner, {
            it?.let {
                if (it) {
                    mCallback!!.selectSettingsPage()
                    Toast.makeText(context, R.string.configure_floors, Toast.LENGTH_SHORT).show()
                }
            }
        })
        requireView().findViewById<MaterialButton>(R.id.parking_entry_button).setOnClickListener {
            mCallback!!.navigateToEntryPage()
        }
        requireView().findViewById<MaterialButton>(R.id.parking_exit_button).setOnClickListener {
            mCallback!!.navigateToExitPage()
        }
        requireView().findViewById<MaterialButton>(R.id.parking_reserve_button).setOnClickListener {
            mCallback!!.navigateToReservePage()
        }
        requireView().findViewById<MaterialButton>(R.id.parking_un_reserve_button)
            .setOnClickListener {
                mCallback!!.navigateToUnReservePage()
            }
        requireView().findViewById<MaterialButton>(R.id.parking_vacancy_detail_button)
            .setOnClickListener {
                mCallback!!.navigateToVacancyListPage()
            }
    }

    companion object {

        val FRAGMENT_NAME = FloorsFragment::class.java.name

        @JvmStatic
        fun newInstance() =
            EntryExitFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
