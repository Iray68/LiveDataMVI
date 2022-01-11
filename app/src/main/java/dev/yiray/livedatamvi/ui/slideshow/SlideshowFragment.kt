package dev.yiray.livedatamvi.ui.slideshow

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import android.view.View
import androidx.fragment.app.Fragment
import dev.yiray.livedatamvi.databinding.FragmentSlideshowBinding

// TODO: bind viewModel with nested graph on navigation
class SlideshowFragment : Fragment() {
    private var slideshowViewModel: SlideshowViewModel? = null
    private var binding: FragmentSlideshowBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentSlideshowBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        slideshowViewModel = ViewModelProvider(this).get(SlideshowViewModel::class.java)
        slideshowViewModel!!.text.observe(viewLifecycleOwner, { s -> binding?.textSlideshow?.text = s })
    }
}