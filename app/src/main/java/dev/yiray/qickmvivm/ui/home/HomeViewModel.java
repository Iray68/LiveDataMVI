package dev.yiray.qickmvivm.ui.home;

import android.util.Log;

import dev.yiray.qickmvivm.base.BaseState;
import dev.yiray.qickmvivm.base.BaseViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

public class HomeViewModel extends BaseViewModel<HomeViewState, HomeView.Action> {

    public HomeViewModel() {
    }

    @Override
    protected void bindIntent(HomeView.Action intent) {
        Observable<HomeViewState> observableInput = intent.observableInput()
                .switchMap(charSequence -> Observable.just(new HomeViewState.InputState(charSequence.toString())));
        Observable<HomeViewState> observableCheckBox = intent.observableCheckBox()
                .map(HomeViewState.CheckBoxState::new);
        Observable<HomeViewState> observableNext = intent.observableNextPage()
                .map(aBoolean -> new HomeViewState.NextPage());

        Observable<HomeViewState> intents = Observable.merge(
                observableInput,
                observableCheckBox,
                observableNext
        ).observeOn(AndroidSchedulers.mainThread());

        HomeViewState initialState = new HomeViewState.ViewState.Builder().build();

        Observable<HomeViewState> stateObservable =
                intents.scan(initialState, this::reducer);

        subscribe(stateObservable);
    }

    private HomeViewState reducer(HomeViewState previousState, HomeViewState newState) {
            Log.i("reducer", newState.toString());
            Log.i("previousState", previousState.toString());

            if (newState instanceof BaseState.SideEffect) {
                handleSideEffects(previousState, newState);
            }

        return previousState.toViewState().copy(newState.toViewState());
    }
}