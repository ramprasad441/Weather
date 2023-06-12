package com.ramprasad.weather.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus

/**
 * Created by Ramprasad on 6/11/23.
 */
private const val TAG = "BaseViewModel"
open class BaseViewModel: ViewModel() {

    protected val viewModelSafeScope by lazy {
        viewModelScope + coroutineExceptionHandler
    }

    private val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { _, exception ->
            Log.e(TAG, exception.localizedMessage, exception)
        }
    }

 /*   val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }*/
}