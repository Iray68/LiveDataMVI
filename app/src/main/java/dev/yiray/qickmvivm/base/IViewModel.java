package dev.yiray.qickmvivm.base;

import androidx.lifecycle.LiveData;

public interface IViewModel<S> {
    LiveData<S> states();
    LiveData<Throwable> errors();
    LiveData<SideEffectEvent<S>> sideEffects();
    void handleSideEffects(S state, S sideEffect);
}
