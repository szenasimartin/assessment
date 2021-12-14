package com.szenamartin.android.vehicle.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(protected val compositeDisposable: CompositeDisposable) : ViewModel() {
    enum class ScreenState {
        LOADING,
        LOADED
    }

    protected val screenState = MutableLiveData<ScreenState>()

    fun getScreenState(): LiveData<ScreenState> {
        return screenState
    }

    protected val errorMessage = MutableLiveData<String>()

    fun getErrorMessage(): LiveData<String> {
        return errorMessage
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
