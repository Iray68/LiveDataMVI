package dev.yiray.livedatamvi.ui.home

import dev.yiray.livedatamvi.ui.home.HomeView.UpdateTask
import dev.yiray.livedatamvi.ui.home.HomeViewState.NextPage
import android.util.Log
import dev.yiray.livedatamvi.base.BaseViewModel
import dev.yiray.livedatamvi.ui.home.HomeViewState.InputState
import dev.yiray.livedatamvi.ui.home.HomeViewState.ListModified
import dev.yiray.livedatamvi.base.BaseState.SideEffect
import kotlinx.coroutines.flow.*

class HomeViewModel : BaseViewModel<HomeViewState, HomeView.Action>() {
    private val interactor: TaskInteractor = TaskInteractor()
    private var memorized: HomeViewState? = null
    public override fun bindIntent(intent: HomeView.Action) {
        val observableInput = intent.observableInput()
            .drop(1)
            .map { charSequence: CharSequence ->
                InputState(
                    charSequence.toString()
                )
            }

        val observableCheckBox = intent.observableCheckBox()
            .map { isChecked: Boolean? ->
                HomeViewState.CheckBoxState(
                    isChecked!!
                )
            }
        val observableNext = intent.observableNextPage()
            .map { NextPage() }
        val observableListUpdated = intent.observableUpdated()
            ?.map { updatedTask: UpdateTask? ->
                ListModified(
                    updatedTask!!.position,
                    updatedTask.task
                )
            }

        val intents = merge(
            observableInput,
            observableCheckBox,
            observableNext,
            observableListUpdated!!
        )

        val state: HomeViewState? = if (memorized == null) {
            HomeViewState.ViewState()
        } else {
            memorized
        }
        val stateObservable = intents.scan(
            state!!,
            { previousState: HomeViewState?, newState: HomeViewState ->
                reducer(
                    previousState,
                    newState
                )
            })
        subscribe(stateObservable)
    }

    private fun reducer(previousState: HomeViewState?, newState: HomeViewState): HomeViewState {
        Log.i("reducer", newState.toString())
        Log.i("previousState", previousState.toString())
        var state: HomeViewState? = newState

        when (newState) {
            is SideEffect -> {
                if (previousState != null) {
                    handleSideEffects(previousState, newState)
                }
            }
            is ListModified -> {
                state = interactor.updateTaskList(
                    previousState!!.toViewState(),
                    newState
                )
            }
        }

        memorized = previousState!!.toViewState()?.copy(state!!.toViewState())
        return memorized!!
    }

}