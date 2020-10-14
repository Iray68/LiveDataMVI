package dev.yiray.qickmvivm.ui.home;

import io.reactivex.rxjava3.core.Observable;

public interface HomeView {
    interface Action {
        Observable<CharSequence> observableInput();
        Observable<Boolean> observableCheckBox();
        Observable<Boolean> observableNextPage();
    }
}
