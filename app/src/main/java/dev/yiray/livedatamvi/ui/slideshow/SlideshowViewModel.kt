package dev.yiray.livedatamvi.ui.slideshow

import androidx.lifecycle.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData

class SlideshowViewModel : ViewModel() {
    private val mText: MutableLiveData<String?> = MutableLiveData()
    val text: LiveData<String?>
        get() = mText

    init {
        mText.value = "This is slideshow fragment"
    }
}