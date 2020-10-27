package dev.yiray.qickmvivm.ui.home;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.yiray.qickmvivm.model.Task;

public class TaskInteractor {
    public HomeViewState updateTaskList(HomeViewState.ViewState prevStatus, HomeViewState.ListModified modified) {
        List<Task> currentTasks = prevStatus.getTaskList();

        int position = modified.getPosition();
        Task task = modified.getTask();

        if (currentTasks == null) {
            return new HomeViewState.ViewState.Builder()
                    .setTaskList(Collections.singletonList(task))
                    .build();
        }

        ArrayList<Task> current = new ArrayList<>(currentTasks);

        if (current.size() >= position + 1) {
            current.set(position, task);
        } else {
            current.add(task);
        }

        return new HomeViewState.ViewState.Builder()
                .setTaskList(Collections.unmodifiableList(current))
                .build();
    }
}
