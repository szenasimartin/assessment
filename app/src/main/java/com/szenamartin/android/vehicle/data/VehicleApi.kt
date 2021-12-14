package com.szenamartin.android.vehicle.data

import com.szenamartin.android.vehicle.data.model.GetItemsResponse
import io.reactivex.Single
import retrofit2.http.GET

interface VehicleApi {
    @GET("/b/5fa8ff8dbd01877eecdb898f")
    fun getItems(): Single<GetItemsResponse>
}
