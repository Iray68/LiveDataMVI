package dev.yiray.qickmvivm.base;

import android.util.Pair;

public class SideEffectEvent<S> {
    private Boolean hasBeenHandled = false;
    private Pair<S, S> sideEffectPair;

    public SideEffectEvent(S state, S sideEffect) {
        sideEffectPair = new Pair<>(state, sideEffect);
    }

    public Pair<S, S> getSideEffectPair() {
        if (hasBeenHandled) {
            return null;
        }

        hasBeenHandled = true;

        return sideEffectPair;
    }
}

