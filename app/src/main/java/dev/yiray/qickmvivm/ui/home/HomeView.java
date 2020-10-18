package dev.yiray.qickmvivm.ui.home;

import dev.yiray.qickmvivm.model.Task;
import io.reactivex.rxjava3.core.Observable;

public interface HomeView {
    interface Action {
        Observable<CharSequence> observableInput();
        Observable<Boolean> observableCheckBox();
        Observable<Boolean> observableNextPage();
        Observable<UpdateTask> observableUpdated();
    }

    final class UpdateTask {
        private int position;
        private Task task;

        public UpdateTask(int position, Task task) {
            this.position = position;
            this.task = task;
        }

        public int getPosition() {
            return position;
        }

        public Task getTask() {
            return task;
        }
    }
}