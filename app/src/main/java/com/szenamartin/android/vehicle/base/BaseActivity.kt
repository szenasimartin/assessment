package com.szenamartin.android.vehicle.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.szenamartin.android.vehicle.R
import com.szenamartin.android.vehicle.di.viewmodel.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity<V : BaseViewModel> : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    protected lateinit var viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDI()
        viewModel = ViewModelProvider(this, viewModelFactory).get(getViewModelClass())
        viewModel.getErrorMessage().observe(this, {
            showError(it)
        })
    }

    open fun showError(message: String) {
        val builder = MaterialAlertDialogBuilder(this)
            .setCancelable(false)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { _, _ -> }
        builder.show()
    }

    private fun performDI() = AndroidInjection.inject(this)

    abstract fun getViewModelClass(): Class<V>
}
