package dev.yiray.livedatamvi.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S, I> protected constructor() : ViewModel(), IViewModel<S> {
    private var stateObservable: Flow<S>? = null
    private val stateLiveData: MutableLiveData<S> = MutableLiveData()
    private val errorLiveData: MutableLiveData<Throwable?> = MutableLiveData()
    private val sideEffectLiveData: MutableLiveData<SideEffectEvent<S>> = MutableLiveData()
    private var dumpState: S? = null
    override fun onCleared() {
        super.onCleared()
    }

    override fun states(): LiveData<S> {
        return stateLiveData
    }

    override fun errors(): LiveData<Throwable?> {
        return errorLiveData
    }

    override fun sideEffects(): LiveData<SideEffectEvent<S>> {
        return sideEffectLiveData
    }

    protected abstract fun bindIntent(intent: I)
    protected fun subscribe(stateObservable: Flow<S>) {
        this.stateObservable = stateObservable
    }

    fun subscribe() {
        viewModelScope.launch {
            stateObservable?.handleErrors()?.collect { state ->
                stateLiveData.postValue(state)
            }
        }
    }

    override fun handleSideEffects(state: S, sideEffect: S) {
        sideEffectLiveData.postValue(SideEffectEvent(state, sideEffect))
    }

    private fun <T> Flow<T>.handleErrors(): Flow<T> = flow {
        try {
            collect { value -> emit(value) }
        } catch (e: Throwable) {
            dumpState = stateLiveData.value
            errorLiveData.postValue(e)
        }
    }
}