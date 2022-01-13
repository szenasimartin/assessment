package com.szenamartin.android.vehicle.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.szenamartin.android.vehicle.base.BaseViewModel
import com.szenamartin.android.vehicle.data.VehicleRepository
import com.szenamartin.android.vehicle.data.model.Item
import com.szenamartin.android.vehicle.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val repository: VehicleRepository,
    compositeDisposable: CompositeDisposable
) : BaseViewModel(compositeDisposable) {

    private val _movieList: MutableLiveData<List<Item>> by lazy {
        MutableLiveData<List<Item>>().also {
            loadList()
        }
    }
    val movieList: LiveData<List<Item>>
        get() = _movieList

    private fun loadList() {
        screenState.value = ScreenState.LOADING
        compositeDisposable.add(
            repository.getItems()
                .compose(schedulerProvider.ioToMainSingleScheduler())
                .doFinally { screenState.value = ScreenState.LOADED }
                .subscribe({
                    _movieList.value = it.data.current
                }, { err ->
                    errorMessage.value = err.toString()
                })
        )
    }
}
