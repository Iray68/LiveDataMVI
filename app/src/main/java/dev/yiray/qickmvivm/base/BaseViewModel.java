package dev.yiray.qickmvivm.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;

public abstract class BaseViewModel<S, I> extends ViewModel implements IViewModel<S> {

    private Observable<S> stateObservable;
    private MutableLiveData<S> stateLiveData;
    private MutableLiveData<Throwable> errorLiveData;
    private MutableLiveData<SideEffectEvent<S>> sideEffectLiveData;

    private CompositeDisposable compositeDisposable;
    private S dumpState;

    protected BaseViewModel() {
        stateLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
        sideEffectLiveData = new MutableLiveData<>();
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }

    @Override
    public LiveData<S> states() {
        return stateLiveData;
    }

    @Override
    public LiveData<Throwable> errors() {
        return errorLiveData;
    }

    @Override
    public LiveData<SideEffectEvent<S>> sideEffects() {
        return sideEffectLiveData;
    }

    protected abstract void bindIntent(I intent);

    protected void subscribe(@NonNull Observable<S> stateObservable) {
        this.stateObservable = stateObservable;
    }

    protected void subscribe() {
        compositeDisposable.add(
                stateObservable.subscribeWith(new DisposableObserver<S>() {
                    @Override
                    public void onNext(S state) {
                            stateLiveData.postValue(state);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dumpState = stateLiveData.getValue();
                        errorLiveData.postValue(e);
                        subscribe();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void handleSideEffects(S state, S sideEffect) {
        sideEffectLiveData.postValue(new SideEffectEvent<>(state, sideEffect));
    }
}
