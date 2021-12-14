package com.szenamartin.android.vehicle.data

import com.szenamartin.android.vehicle.data.model.GetItemsResponse
import io.reactivex.Single

class VehicleRepository(private val vehicleRemoteDataSource: VehicleRemoteDataSource) {
    fun getItems(): Single<GetItemsResponse> {
        return vehicleRemoteDataSource.getItems()
    }
}
