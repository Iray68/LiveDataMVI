package dev.yiray.qickmvivm.base;

public interface IBaseView<S> {
    void render(S state);
    void handleSideEffect(S state, S sideEffect);

    void showErrorMsg(Throwable error);
    void displayErrorMsg(String message);
}
