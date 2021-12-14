package com.szenamartin.android.vehicle.data.model

import com.squareup.moshi.Json

data class Item(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "vehicleId") val vehicleId: String,
    @field:Json(name = "latitude") val latitude: Double,
    @field:Json(name = "longitude") val longitude: Double,
    @field:Json(name = "resolution") val resolution: String,
    @field:Json(name = "model") val model: String,
    @field:Json(name = "state") val state: String
)
