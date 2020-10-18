package dev.yiray.qickmvivm.ui.home;

import java.util.ArrayList;
import java.util.Collections;

import dev.yiray.qickmvivm.model.Task;

public class TaskInteractor {
    public HomeViewState updateTaskList(HomeViewState.ViewState prevStatus, HomeViewState.ListModified modified) {
        ArrayList<Task> current = new ArrayList<>(prevStatus.getTaskList());

        int position = modified.getPosition();
        Task task = modified.getTask();

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
