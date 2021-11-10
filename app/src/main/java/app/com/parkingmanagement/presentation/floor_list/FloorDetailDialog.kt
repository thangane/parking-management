package app.com.parkingmanagement.presentation.floor_list

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDialog
import app.com.parkingmanagement.R
import app.com.parkingmanagement.domain.model.Floor
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.floor

class FloorDetailDialog(
    context: Context,
    floor: Floor?,
    var floorDialogListener: FloorDialogListener
) : AppCompatDialog(context) {

    private var floorData: Floor? = floor

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.dialog_floor_detail)
        val dialogLayout = findViewById<LinearLayout>(R.id.floor_detail_dialog)!!
        val floorId = findViewById<TextView>(R.id.document_id)!!
        val floorNameInput = findViewById<TextInputEditText>(R.id.floor_name_text_input)!!
        val totalSpacesInput = findViewById<TextInputEditText>(R.id.available_spaces_text_input)!!
        val params = dialogLayout.layoutParams
        params.width = context.resources.displayMetrics.widthPixels
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialogLayout.layoutParams = params

        floorData?.let {
            floorId.text = it.id
            floorNameInput.setText(it.name)
            totalSpacesInput.setText(it.totalSpaces)
        }

        findViewById<Button>(R.id.save_button)!!.setOnClickListener {
            val floorName = floorNameInput.text.toString()
            val availableSpaces = totalSpacesInput.text.toString()
            if (floorName == "" || availableSpaces == "" || availableSpaces.toInt() < 0) {
                Toast.makeText(context, R.string.enter_valid_details, Toast.LENGTH_SHORT).show()
            } else {
                findViewById<ProgressBar>(R.id.floor_detail_dialog_progress)!!.visibility =
                    View.VISIBLE
                val id = floorId.text.toString()

                val partitionedSpaces = partitionSpaces(availableSpaces)
                val floor = if (floorData == null) Floor(
                    floorName,
                    availableSpaces,
                    partitionedSpaces["bikeSpace"]!!,
                    partitionedSpaces["busSpace"]!!,
                    partitionedSpaces["carSpace"]!!
                ) else Floor(
                    id,
                    floorName,
                    availableSpaces,
                    partitionedSpaces["bikeSpace"]!!,
                    partitionedSpaces["busSpace"]!!,
                    partitionedSpaces["carSpace"]!!
                )
                floorDialogListener.onSaveButtonClicked(floor)
                dismiss()
            }

        }

    }

    private fun partitionSpaces(availableSpaces: String): Map<String, String> {
        // Bike(40%), Car(40%) and Bus(20%)
        val totalSpace = availableSpaces.toInt()
        val bikeSpace = floor(totalSpace * .40).toInt()
        val busSpace = totalSpace - bikeSpace - bikeSpace
        return mapOf(
            "bikeSpace" to bikeSpace.toString(),
            "carSpace" to bikeSpace.toString(),
            "busSpace" to busSpace.toString()
        )
    }

}

