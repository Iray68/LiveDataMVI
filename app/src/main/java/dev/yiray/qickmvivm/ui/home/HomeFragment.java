package dev.yiray.qickmvivm.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.jakewharton.rxbinding4.view.RxView;
import com.jakewharton.rxbinding4.widget.RxCompoundButton;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import dev.yiray.qickmvivm.R;
import dev.yiray.qickmvivm.base.BaseMVIVMFragment;
import dev.yiray.qickmvivm.databinding.FragmentHomeBinding;
import dev.yiray.qickmvivm.ui.ViewModelFactory;
import io.reactivex.rxjava3.core.Observable;

public class HomeFragment extends BaseMVIVMFragment<HomeView.Action, HomeViewState, HomeViewModel>
    implements  HomeView, HomeView.Action{

    private final static String TAG = HomeFragment.class.getSimpleName();

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel.bindIntent(this);
    }

    @Override
    protected HomeViewModel bindViewModel() {
        // Bind Activity's Lifecycle or you can use
        // navController.getViewModelStoreOwner(*your_nested_graph_id) to bind with nested graph
        return new ViewModelProvider(requireActivity(), new ViewModelFactory()).get(HomeViewModel.class);
    }

    @Override
    public void displayErrorMsg(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void render(HomeViewState state) {
        binding.textHome.setText(state.toViewState().getText());
        binding.buttonNext.setEnabled(state.toViewState().getChecked());
    }

    @Override
    public void handleSideEffect(HomeViewState state, HomeViewState sideEffect) {
        if (sideEffect instanceof HomeViewState.NextPage) {
            Log.i(TAG, "TO NEXT PAGE");
            // TODO: to next fragment (TO-DO List)
        }
    }

    @Override
    public Observable<CharSequence> observableInput() {
        return RxTextView.textChanges(binding.input);
    }

    @Override
    public Observable<Boolean> observableCheckBox() {
        return RxCompoundButton.checkedChanges(binding.checkboxPrepared);
    }

    @Override
    public Observable<Boolean> observableNextPage() {
        return RxView.clicks(binding.buttonNext)
                .debounce(200, TimeUnit.MILLISECONDS)
                .map(e -> true);
    }
}