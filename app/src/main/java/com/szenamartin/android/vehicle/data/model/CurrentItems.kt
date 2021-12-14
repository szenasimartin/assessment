package com.szenamartin.android.vehicle.data.model

import com.squareup.moshi.Json

data class CurrentItems(
    @field:Json(name = "current") val current: List<Item>
)
