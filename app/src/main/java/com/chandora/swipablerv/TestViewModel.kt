package com.chandora.swipablerv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * Created by Ankit
 */


class TestViewModel: ViewModel() {

    var message =  MutableLiveData<String>()

    init {

        message.value = "df"
    }



}