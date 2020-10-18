package dev.yiray.qickmvivm.ui.home;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.yiray.qickmvivm.base.BaseState;
import dev.yiray.qickmvivm.model.Task;

public interface HomeViewState extends BaseState<HomeViewState.ViewState> {
    final class ViewState extends BaseState.BaseViewState<ViewState> implements HomeViewState {
        private String text;
        private Boolean isChecked;
        private List<Task> taskList;

        static class Builder {
            private String text;
            private Boolean isChecked;
            private List<Task> taskList;

            Builder () {
                isChecked = false;
                taskList = new ArrayList<>();
            }

            Builder(ViewState origin){
                text = origin.text;
                isChecked = origin.isChecked;
                taskList = origin.taskList;
            }

            public Builder setText(String text) {
                this.text = text;
                return this;
            }

            public Builder setChecked(boolean checked) {
                isChecked = checked;
                return this;
            }

            public Builder setTaskList(List<Task> taskList) {
                this.taskList = taskList;
                return this;
            }

            public ViewState build() {
                return new ViewState(text, isChecked, taskList);
            }
        }

        public ViewState(String text, Boolean isChecked, List<Task> taskList) {
            this.text = text;
            this.isChecked = isChecked;
            this.taskList = taskList;
        }

        public String getText() {
            return text;
        }

        public Boolean getChecked() {
            return isChecked;
        }

        public List<Task> getTaskList() {
            return taskList;
        }

        @Override
        public String toString() {
            return "ViewState{" +
                    "text='" + text + '\'' +
                    ", isChecked=" + isChecked +
                    ", taskList=" + taskList +
                    '}';
        }

        @Override
        public ViewState toViewState() {
            return this;
        }

        @Override
        public ViewState copy(ViewState nextState) {
            if (nextState == null) {
                return this;
            }

            Builder builder = new Builder(this);

            if (nextState.text != null) {
                builder.setText(nextState.text);
            }

            if (nextState.isChecked != null) {
                builder.setChecked(nextState.isChecked);
            }

            if (nextState.taskList != null) {
                builder.setTaskList(nextState.taskList);
            }

            return builder.build();
        }
    }

    final class InputState implements HomeViewState {

        private String text;

        public InputState(String text) {
            this.text = text;
        }

        @Override
        public ViewState toViewState() {
            return new ViewState.Builder().setText(text).build();
        }

        @Override
        public String toString() {
            return "InputState{" +
                    "text='" + text + '\'' +
                    '}';
        }
    }

    final class CheckBoxState implements HomeViewState {
        private boolean isChecked;

        public CheckBoxState(boolean isChecked) {
            this.isChecked = isChecked;
        }

        @Override
        public ViewState toViewState() {
            return new ViewState.Builder().setChecked(isChecked).build();
        }

        @Override
        public String toString() {
            return "CheckBoxState{" +
                    "isChecked=" + isChecked +
                    '}';
        }
    }

    final class NextPage implements HomeViewState, SideEffect {
        @Override
        public ViewState toViewState() {
            return null;
        }

        @NonNull
        @Override
        public String toString() {
            return "NextPage{}";
        }
    }

    final class ListModified implements HomeViewState {
        private int position;
        private Task task;

        public ListModified(int position, Task task) {
            this.position = position;
            this.task = task;
        }

        public Task getTask() {
            return task;
        }

        public int getPosition() {
            return position;
        }

        @Override
        public ViewState toViewState() {
            return new ViewState.Builder().setTaskList(Collections.singletonList(task)).build();
        }

        @Override
        public String toString() {
            return "ListModified{" +
                    "position=" + position +
                    ", task=" + task +
                    '}';
        }
    }
}
