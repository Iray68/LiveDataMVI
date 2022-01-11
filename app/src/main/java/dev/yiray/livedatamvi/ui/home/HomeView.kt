package dev.yiray.livedatamvi.ui.home

import dev.yiray.livedatamvi.model.Task
import kotlinx.coroutines.flow.Flow

interface HomeView {
    interface Action {
        fun observableInput(): Flow<CharSequence>
        fun observableCheckBox(): Flow<Boolean>
        fun observableNextPage(): Flow<Boolean>
        fun observableUpdated(): Flow<UpdateTask>?
    }

    class UpdateTask(val position: Int, val task: Task)
}