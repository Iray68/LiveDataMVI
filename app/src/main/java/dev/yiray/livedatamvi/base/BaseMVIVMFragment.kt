package dev.yiray.livedatamvi.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseMVIVMFragment<I, S, VM : BaseViewModel<S, I>?> : Fragment(), IBaseView<S> {
    protected var mViewModel: VM? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = bindViewModel()
        mViewModel!!.states().observe(viewLifecycleOwner, { state: S -> render(state) })
        mViewModel!!.errors()
            .observe(viewLifecycleOwner, { error: Throwable? -> showErrorMsg(error) })
        mViewModel!!.sideEffects().observe(viewLifecycleOwner, { event: SideEffectEvent<S> ->
            val statePair = event.getSideEffect()
            if (statePair != null) {
                handleSideEffect(statePair.first, statePair.second)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mViewModel!!.subscribe()
    }

    protected abstract fun bindViewModel(): VM
    override fun showErrorMsg(error: Throwable?) {
        var message = error.toString()
        displayErrorMsg(message)
        Log.e("Error", message)
    }
}