package dev.yiray.qickmvivm.ui.home;

import androidx.annotation.NonNull;

import dev.yiray.qickmvivm.base.BaseState;

public interface HomeViewState extends BaseState<HomeViewState.ViewState> {
    final class ViewState extends BaseState.BaseViewState<HomeViewState.ViewState> implements HomeViewState {
        private String text;
        private Boolean isChecked;

        static class Builder {
            private String text;
            private Boolean isChecked;

            Builder () {
                isChecked = false;
            }

            Builder(ViewState origin){
                text = origin.text;
                isChecked = origin.isChecked;
            }

            public Builder setText(String text) {
                this.text = text;
                return this;
            }

            public Builder setChecked(boolean checked) {
                isChecked = checked;
                return this;
            }

            public ViewState build() {
                return new ViewState(text, isChecked);
            }
        }

        public ViewState(String text, Boolean isChecked) {
            this.text = text;
            this.isChecked = isChecked;
        }

        public String getText() {
            return text;
        }

        public Boolean getChecked() {
            return isChecked;
        }

        @Override
        public String toString() {
            return "ViewState{" +
                    "text='" + text + '\'' +
                    ", isChecked=" + isChecked +
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
}
