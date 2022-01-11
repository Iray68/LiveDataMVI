package dev.yiray.livedatamvi.ui.home

import android.content.Context
import dev.yiray.livedatamvi.ui.home.HomeView.UpdateTask
import dev.yiray.livedatamvi.base.BaseMVIVMFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.text.Editable
import androidx.lifecycle.ViewModelProvider
import dev.yiray.livedatamvi.ui.ViewModelFactory
import android.widget.Toast
import dev.yiray.livedatamvi.ui.home.HomeViewState.NextPage
import android.view.View
import androidx.core.widget.doOnTextChanged
import dev.yiray.livedatamvi.databinding.FragmentHomeBinding
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import java.lang.ClassCastException

class HomeFragment : BaseMVIVMFragment<HomeView.Action, HomeViewState, HomeViewModel>(), HomeView,
    HomeView.Action {
    private var binding: FragmentHomeBinding? = null
    private var coordinator: Coordinator? = null

    interface Coordinator {
        fun toTaskList()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        coordinator = try {
            context as Coordinator
        } catch (e: ClassCastException) {
            throw ClassCastException("MUST implement Coordination: $context")
        }
    }

    override fun onStart() {
        super.onStart()
        mViewModel!!.bindIntent(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun bindViewModel(): HomeViewModel {
        // Bind Activity's Lifecycle or you can use
        // navController.getViewModelStoreOwner(*your_nested_graph_id) to bind with nested graph
        return ViewModelProvider(
            requireActivity(),
            ViewModelFactory()
        ).get(HomeViewModel::class.java)
    }

    override fun displayErrorMsg(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun render(state: HomeViewState) {
        val text = state.toViewState()?.text
        val isChecked = state.toViewState()!!.checked
        binding!!.textHome.text = text

        if (text != null && binding!!.input.text.toString() != text) {
            binding!!.input.text = Editable.Factory.getInstance().newEditable(text)
        }

        binding!!.checkboxPrepared.isChecked = isChecked
        binding!!.buttonNext.isEnabled = isChecked
    }

    override fun handleSideEffect(state: HomeViewState, sideEffect: HomeViewState) {
        if (sideEffect is NextPage) {
            coordinator!!.toTaskList()
        }
    }

    override fun observableInput(): Flow<CharSequence> {
        return callbackFlow {
            binding!!.input.doOnTextChanged { text, _, _, _ ->
                if (text != null) {
                    trySend(text)
                }
            }

            awaitClose { binding!!.input.addTextChangedListener(null) }
        }
    }

    override fun observableCheckBox(): Flow<Boolean> {
        return callbackFlow {
            binding!!.checkboxPrepared.setOnCheckedChangeListener { _, isChecked ->
                trySend(isChecked)
            }

            awaitClose { binding!!.checkboxPrepared.setOnCheckedChangeListener(null) }
        }
    }

    override fun observableNextPage(): Flow<Boolean> {
        return callbackFlow {
            binding!!.buttonNext.setOnClickListener {
                trySend(true)
            }
            awaitClose { binding!!.buttonNext.setOnClickListener(null) }
        }
    }

    override fun observableUpdated(): Flow<UpdateTask> {
        return flow { }
    }
}