package dev.yiray.livedatamvi.base

interface IBaseView<S> {
    fun render(state: S)
    fun handleSideEffect(state: S, sideEffect: S)
    fun showErrorMsg(error: Throwable?)
    fun displayErrorMsg(message: String?)
}