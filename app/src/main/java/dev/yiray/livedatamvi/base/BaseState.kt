package dev.yiray.livedatamvi.base

import dev.yiray.livedatamvi.base.BaseState.BaseViewState

interface BaseState<VS : BaseViewState<VS>?> {
    fun toViewState(): VS

    abstract class BaseViewState<VS : BaseViewState<VS>?> : BaseState<VS> {
        abstract fun copy(nextState: VS): VS
    }

    interface SideEffect
}