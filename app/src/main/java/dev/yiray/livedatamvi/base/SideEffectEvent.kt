package dev.yiray.livedatamvi.base

import android.util.Pair

class SideEffectEvent<S>(state: S, sideEffect: S) {
    private var hasBeenHandled = false
    val sideEffectPair: Pair<S, S> = Pair(state, sideEffect)

    fun getSideEffect(): Pair<S, S>? {
        if (hasBeenHandled) {
            return null
        }
        hasBeenHandled = true
        return sideEffectPair
    }
}