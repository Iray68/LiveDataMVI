package dev.yiray.qickmvivm.ui.home;

import android.util.Log;

import dev.yiray.qickmvivm.base.BaseState;
import dev.yiray.qickmvivm.base.BaseViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

public class HomeViewModel extends BaseViewModel<HomeViewState, HomeView.Action> {
    private TaskInteractor interactor;

    private HomeViewState memorized;

    public HomeViewModel() {
        interactor = new TaskInteractor();
    }

    @Override
    protected void bindIntent(HomeView.Action intent) {
        Observable<HomeViewState> observableInput = intent.observableInput()
                .switchMap(charSequence -> Observable.just(new HomeViewState.InputState(charSequence.toString())));
        Observable<HomeViewState> observableCheckBox = intent.observableCheckBox()
                .map(HomeViewState.CheckBoxState::new);
        Observable<HomeViewState> observableNext = intent.observableNextPage()
                .map(aBoolean -> new HomeViewState.NextPage());
        Observable<HomeViewState> observableListUpdated = intent.observableUpdated()
                .map(updatedTask -> new HomeViewState.ListModified(updatedTask.getPosition(), updatedTask.getTask()));

        Observable<HomeViewState> intents = Observable.merge(
                observableInput,
                observableCheckBox,
                observableNext,
                observableListUpdated
        ).observeOn(AndroidSchedulers.mainThread());

        HomeViewState state;

        if (memorized == null) {
            state =  new HomeViewState.ViewState.Builder().build();
        } else {
            state = memorized;
        }

        Observable<HomeViewState> stateObservable =
                intents.scan(state, this::reducer);

        subscribe(stateObservable);
    }

    private HomeViewState reducer(HomeViewState previousState, HomeViewState newState) {
            Log.i("reducer", newState.toString());
            Log.i("previousState", previousState.toString());

            if (newState instanceof BaseState.SideEffect) {
                handleSideEffects(previousState, newState);
            }

            HomeViewState state = newState;

            if (newState instanceof HomeViewState.ListModified) {
                state = interactor.updateTaskList(
                        previousState.toViewState(),
                        (HomeViewState.ListModified) newState
                );
            }

            memorized = previousState.toViewState().copy(state.toViewState());

            return memorized;
    }
}