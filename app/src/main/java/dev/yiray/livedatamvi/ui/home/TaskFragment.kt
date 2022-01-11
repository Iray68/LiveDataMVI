package dev.yiray.livedatamvi.ui.home

import dev.yiray.livedatamvi.ui.home.HomeView.UpdateTask
import dev.yiray.livedatamvi.base.BaseMVIVMFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import dev.yiray.livedatamvi.databinding.FragmentTaskBinding
import dev.yiray.livedatamvi.databinding.ItemTaskBinding
import dev.yiray.livedatamvi.model.Status
import dev.yiray.livedatamvi.model.Task
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.ArrayList

class TaskFragment : BaseMVIVMFragment<HomeView.Action, HomeViewState, HomeViewModel>(),
    HomeView.Action {
    private var binding: FragmentTaskBinding? = null
    private var taskList: List<Task?>? = null
    private var mUpdateTaskSubject = MutableSharedFlow<UpdateTask>()
    private var adapter: TaskRecyclerViewAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskList = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(inflater, container, false)

        adapter = TaskRecyclerViewAdapter()
        val recyclerView = binding!!.list
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        binding!!.btnAdd.setOnClickListener { v: View -> onAdd() }

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel!!.bindIntent(this)
    }

    override fun bindViewModel(): HomeViewModel {
        return ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    override fun render(state: HomeViewState) {
        val nowSize = taskList!!.size
        val updatedList = state.toViewState()!!.taskList
        if (updatedList != null) {
            taskList = state.toViewState()!!.taskList
        }
//        adapter!!.notifyDataSetChanged()
        state.toViewState()!!.lastModifiedPosition?.let {
//            if (nowSize < taskList!!.size) {
//                adapter!!.notifyItemRangeChanged(0, taskList!!.size)
//                return
//            }
            adapter!!.notifyItemChanged(it)
        }
    }

    override fun handleSideEffect(state: HomeViewState, sideEffect: HomeViewState) {}
    override fun displayErrorMsg(message: String?) {}
    override fun observableInput(): Flow<CharSequence> {
        return flow { }
    }

    override fun observableCheckBox(): Flow<Boolean> {
        return flow { }
    }

    override fun observableNextPage(): Flow<Boolean> {
        return flow { }
    }

    override fun observableUpdated(): Flow<UpdateTask>? {
        return mUpdateTaskSubject
    }

    private fun onAdd() {
        val editText = binding!!.etTask
        if (!TextUtils.isEmpty(editText.text.toString())) {
            lifecycleScope.launch {
                mUpdateTaskSubject.emit(
                    UpdateTask(taskList!!.size, Task(editText.text.toString()))
                )
            }
            editText.setText("")
        }
    }

    inner class TaskRecyclerViewAdapter :
        RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mTask = taskList!![position]
            holder.tvName.text = holder.mTask!!.name
            var status = ""
            status = when (holder.mTask!!.status) {
                Status.OPEN -> "☐"
                Status.COMPLETE -> "☑"
            }
            holder.tvStatus.text = status
            holder.itemView.setOnClickListener {
                lifecycleScope.launch {
                    mUpdateTaskSubject.emit(
                        UpdateTask(position, Task(holder.mTask!!.name, Status.COMPLETE))
                    )
                }
            }
        }

        override fun getItemCount(): Int {
            return taskList!!.size
        }

        inner class ViewHolder(binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
            val tvName: TextView = binding.name
            val tvStatus: TextView = binding.status
            var mTask: Task? = null
            override fun toString(): String {
                return super.toString() + " '" + tvName.text + "'"
            }

        }
    }

    companion object {
        fun newInstance(columnCount: Int): TaskFragment {
            return TaskFragment()
        }
    }
}