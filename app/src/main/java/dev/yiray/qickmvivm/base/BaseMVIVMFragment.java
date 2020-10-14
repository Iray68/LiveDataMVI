package dev.yiray.qickmvivm.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.reactivex.rxjava3.exceptions.CompositeException;

public abstract class BaseMVIVMFragment<I, S, VM extends BaseViewModel<S, I>> extends Fragment
        implements IBaseView<S> {
    protected VM mViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mViewModel = bindViewModel();
        mViewModel.states().observe(getViewLifecycleOwner(), this::render);
        mViewModel.errors().observe(getViewLifecycleOwner(), this::showErrorMsg);
        mViewModel.sideEffects().observe(getViewLifecycleOwner(), statePair ->
                handleSideEffect(statePair.first, statePair.second));
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.subscribe();
    }

    protected abstract VM bindViewModel();

    @Override
    public void showErrorMsg(Throwable error) {
        String message = error.toString();

        if (error instanceof CompositeException) {
            message = "";

            CompositeException compositeException = (CompositeException) error;
            for (Throwable exception: compositeException.getExceptions()) {

                if (TextUtils.isEmpty(message)) {
                    message = exception.toString();
                }

                message = String.format("%s,\n %s", message, exception.toString());
            }
        }

        displayErrorMsg(message);

        Log.e("Error", message);
    }
}
