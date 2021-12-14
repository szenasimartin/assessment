package com.szenamartin.android.vehicle.data.model

import com.squareup.moshi.Json

data class GetItemsResponse(
    @field:Json(name = "data") val data: CurrentItems
)
