package com.szenamartin.android.vehicle.data

import com.szenamartin.android.vehicle.data.model.GetItemsResponse
import io.reactivex.Single

class VehicleRemoteDataSource(private val vehicleApi: VehicleApi) {
    fun getItems(): Single<GetItemsResponse> {
        return vehicleApi.getItems()
    }
}
