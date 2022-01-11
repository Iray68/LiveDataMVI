package dev.yiray.livedatamvi.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

abstract class BaseMVIVMActivity<I, S, VM : BaseViewModel<S, I>?> : AppCompatActivity(),
    IBaseView<S> {
    private var mViewModel: VM? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = bindViewModel()
        mViewModel!!.states().observe(this, { state: S -> render(state) })
        mViewModel!!.errors().observe(this, { error: Throwable? -> showErrorMsg(error) })
        mViewModel!!.sideEffects().observe(this, { event: SideEffectEvent<S> ->
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
        val message = error.toString()

        displayErrorMsg(message)
        Log.e("Error", message)
    }
}