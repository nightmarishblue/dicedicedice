package com.midnight.dicedicedice.ui.home

import android.R
import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "do the dice thing"
    }
    val text: LiveData<String> = _text

}

