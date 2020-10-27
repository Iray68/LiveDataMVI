package dev.yiray.qickmvivm.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dev.yiray.qickmvivm.R;
import dev.yiray.qickmvivm.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;

    private FragmentSearchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        searchViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                String name = user.getName();

                if (name == null) {
                    name = "";
                }

                binding.information.setText(
                        String.format("%s, %s \n%s", name, user.getLogin(), user.getUrl()));
            } else {
                binding.information.setText(getString(R.string.hint_not_found));
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSearch.setOnClickListener(v ->
                searchViewModel.findUser(binding.input.getText().toString()));
    }

}