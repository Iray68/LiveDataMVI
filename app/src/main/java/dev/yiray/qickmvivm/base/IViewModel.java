package dev.yiray.qickmvivm.base;


import android.util.Pair;

import androidx.lifecycle.LiveData;

public interface IViewModel<S> {
    LiveData<S> states();
    LiveData<Throwable> errors();
    LiveData<Pair<S, S>> sideEffects();
    void handleSideEffects(S state, S sideEffect);
}
