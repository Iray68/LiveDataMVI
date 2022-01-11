package dev.yiray.livedatamvi.base

import androidx.lifecycle.LiveData

interface IViewModel<S> {
    fun states(): LiveData<S>
    fun errors(): LiveData<Throwable?>
    fun sideEffects(): LiveData<SideEffectEvent<S>>
    fun handleSideEffects(state: S, sideEffect: S)
}