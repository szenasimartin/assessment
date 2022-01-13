package com.szenamartin.android.vehicle.utils

import android.content.Context
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.szenamartin.android.vehicle.R
import com.szenamartin.android.vehicle.map.clustering.MyItem

fun showBottomSheet(context: Context, item: MyItem){
    val dialog = BottomSheetDialog(context)
    dialog.setContentView(R.layout.item_vehicle)
    dialog.findViewById<TextView>(R.id.model_value)?.apply { text = item.title }
    dialog.findViewById<TextView>(R.id.vehicle_id_value)?.apply { text = item.vehicleId }
    dialog.findViewById<TextView>(R.id.resolution_value)?.apply { text = item.resolution }
    dialog.findViewById<TextView>(R.id.state_value)?.apply { text = item.snippet }
    dialog.show()
}
