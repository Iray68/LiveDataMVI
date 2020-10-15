package dev.yiray.qickmvivm.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.rxjava3.exceptions.CompositeException;


public abstract class BaseMVIVMActivity<I, S, VM extends BaseViewModel<S, I>>
        extends AppCompatActivity implements IBaseView<S> {

    protected VM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mViewModel = bindViewModel();
        mViewModel.states().observe(this, this::render);
        mViewModel.errors().observe(this, this::showErrorMsg);
        mViewModel.sideEffects().observe(this, event -> {
            Pair<S, S> statePair = event.getSideEffectPair();
            if (statePair != null) {
                handleSideEffect(statePair.first, statePair.second);
            }
        });
    }

    @Override
    protected void onResume() {
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
