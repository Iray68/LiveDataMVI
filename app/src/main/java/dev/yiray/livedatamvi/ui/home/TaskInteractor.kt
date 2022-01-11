package dev.yiray.livedatamvi.ui.home

import dev.yiray.livedatamvi.ui.home.HomeViewState.ListModified
import java.util.*

class TaskInteractor {
    fun updateTaskList(
        prevStatus: HomeViewState.ViewState?,
        modified: ListModified
    ): HomeViewState {
        val currentTasks = prevStatus?.taskList
        val position = modified.position
        val task = modified.task
        if (currentTasks == null) {
            return HomeViewState.ViewState(
                taskList = listOf(task),
                lastModifiedPosition = position
            )
        }
        val current = ArrayList(currentTasks)
        if (current.size >= position + 1) {
            current[position] = task
        } else {
            current.add(task)
        }
        return HomeViewState.ViewState(
            taskList = Collections.unmodifiableList(current),
            lastModifiedPosition = position
        )
    }
}