package dev.yiray.livedatamvi.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import android.view.View
import dev.yiray.livedatamvi.R
import androidx.fragment.app.Fragment
import dev.yiray.livedatamvi.databinding.FragmentSearchBinding
import dev.yiray.livedatamvi.model.User

class SearchFragment : Fragment() {
    private var searchViewModel: SearchViewModel? = null
    private var binding: FragmentSearchBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        searchViewModel!!.user.observe(viewLifecycleOwner, { user: User? ->
            if (user != null) {
                val name = user.name
                binding!!.information.text =
                    String.format("%s, %s \n%s", name, user.login, user.url)
            } else {
                binding!!.information.text = getString(R.string.hint_not_found)
            }
        })

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.buttonSearch.setOnClickListener { v: View? ->
            searchViewModel!!.findUser(
                binding!!.input.text.toString()
            )
        }
    }
}