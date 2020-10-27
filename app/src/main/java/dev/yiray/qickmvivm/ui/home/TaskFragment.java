package dev.yiray.qickmvivm.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import dev.yiray.qickmvivm.base.BaseMVIVMFragment;
import dev.yiray.qickmvivm.databinding.FragmentTaskBinding;
import dev.yiray.qickmvivm.databinding.ItemTaskBinding;
import dev.yiray.qickmvivm.model.Status;
import dev.yiray.qickmvivm.model.Task;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class TaskFragment extends BaseMVIVMFragment<HomeView.Action, HomeViewState, HomeViewModel>
        implements HomeView.Action
{
    private FragmentTaskBinding binding;
    private List<Task> taskList;
    private PublishSubject<HomeView.UpdateTask> mUpdateTaskSubject;
    private TaskRecyclerViewAdapter adapter;

    public TaskFragment() {
    }

    @SuppressWarnings("unused")
    public static TaskFragment newInstance(int columnCount) {
        return new TaskFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUpdateTaskSubject = PublishSubject.create();
        taskList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        adapter = new TaskRecyclerViewAdapter();

        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        binding.btnAdd.setOnClickListener(this::onAdd);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.bindIntent(this);
    }

    @Override
    protected HomeViewModel bindViewModel() {
        return new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
    }

    @Override
    public void render(HomeViewState state) {
        List<Task> updatedList = state.toViewState().getTaskList();

        if (updatedList != null) {
            taskList = state.toViewState().getTaskList();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void handleSideEffect(HomeViewState state, HomeViewState sideEffect) {

    }

    @Override
    public void displayErrorMsg(String message) {

    }

    @Override
    public Observable<CharSequence> observableInput() {
        return Observable.empty();
    }

    @Override
    public Observable<Boolean> observableCheckBox() {
        return Observable.empty();
    }

    @Override
    public Observable<Boolean> observableNextPage() {
        return Observable.empty();
    }

    @Override
    public Observable<HomeView.UpdateTask> observableUpdated() {
        return mUpdateTaskSubject;
    }

    private void onAdd(View v) {
        EditText editText = binding.etTask;

        if (!TextUtils.isEmpty(editText.getText().toString())) {
            mUpdateTaskSubject.onNext(
                    new HomeView.UpdateTask(taskList.size(), new Task(editText.getText().toString()))
            );
            editText.setText("");
        }
    }

    public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

        @NotNull
        @Override
        public TaskRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
            ItemTaskBinding binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new TaskRecyclerViewAdapter.ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(final TaskRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mTask = taskList.get(position);
            holder.tvName.setText(holder.mTask.getName());

            String status = "";

            switch (holder.mTask.getStatus()) {
                case OPEN:
                    status = "☐";
                    break;
                case COMPLETE:
                    status = "☑";
                    break;
            }

            holder.tvStatus.setText(status);
            holder.itemView.setOnClickListener(v -> mUpdateTaskSubject.onNext(
                    new HomeView.UpdateTask(position, new Task(holder.mTask.getName(), Status.COMPLETE))
            ));
        }

        @Override
        public int getItemCount() {
            return taskList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View itemView;
            public final TextView tvName;
            public final TextView tvStatus;

            public Task mTask;

            public ViewHolder(ItemTaskBinding binding) {
                super(binding.getRoot());
                itemView = binding.getRoot();
                tvName = binding.name;
                tvStatus = binding.status;
            }

            @NotNull
            @Override
            public String toString() {
                return super.toString() + " '" + tvName.getText() + "'";
            }
        }
    }
}