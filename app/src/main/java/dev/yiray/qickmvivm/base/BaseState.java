package dev.yiray.qickmvivm.base;

public interface BaseState<VS extends BaseState.BaseViewState<VS>> {
    VS toViewState();

    abstract class BaseViewState<VS extends BaseViewState<VS>> implements BaseState<VS> {

        abstract class Builder {

            Builder () {

            }

            public abstract VS build();
        }

        public abstract VS copy(VS nextState);
    }

    interface SideEffect {

    }

}
