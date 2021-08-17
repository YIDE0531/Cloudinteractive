package com.nuu.cloudinteractive.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nuu.cloudinteractive.Event

class LoginViewModel: ViewModel() {
    private val _clickEvent = MutableLiveData<Event<String>>()

    val clickEvent : LiveData<Event<String>>
        get() = _clickEvent

    fun userClicksOnButton() {
        _clickEvent.value = Event("login")  // Trigger the event by setting a new Event as a new value
    }
}