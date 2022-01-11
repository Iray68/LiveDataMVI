package dev.yiray.livedatamvi.ui.home

import dev.yiray.livedatamvi.base.BaseState.SideEffect
import dev.yiray.livedatamvi.base.BaseState
import dev.yiray.livedatamvi.base.BaseState.BaseViewState
import dev.yiray.livedatamvi.model.Task

interface HomeViewState : BaseState<HomeViewState.ViewState?> {
    data class ViewState(
        val text: String? = null,
        val checked: Boolean = false,
        val taskList: List<Task?>? = null,
        val lastModifiedPosition: Int? = null
    ) :
        BaseViewState<ViewState?>(), HomeViewState {

        override fun toViewState(): ViewState {
            return this
        }

        override fun copy(nextState: ViewState?): ViewState {
            if (nextState == null) {
                return this
            }

            return copy(
                text = nextState.text ?: text,
                checked = nextState.checked,
                taskList = nextState.taskList ?: taskList,
                lastModifiedPosition = nextState.lastModifiedPosition ?: lastModifiedPosition
            )
        }
    }

    data class InputState(private val text: String) : HomeViewState {
        override fun toViewState(): ViewState {
            return ViewState(text)
        }
    }

    data class CheckBoxState(private val isChecked: Boolean) : HomeViewState {
        override fun toViewState(): ViewState {
            return ViewState(checked = isChecked)
        }
    }

    class NextPage : HomeViewState, SideEffect {
        override fun toViewState(): ViewState? {
            return null
        }

        override fun toString(): String {
            return "NextPage{}"
        }
    }

    data class ListModified(val position: Int, val task: Task?) : HomeViewState {
        override fun toViewState(): ViewState {
            return ViewState(taskList = listOf(task), lastModifiedPosition = position)
        }
    }
}