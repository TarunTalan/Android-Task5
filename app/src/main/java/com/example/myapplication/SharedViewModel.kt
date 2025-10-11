package com.example.myapplication// In SharedViewModel.kt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val username = MutableLiveData<String>()
    val friendName = MutableLiveData<String>()
    var isInitialUserAdded = MutableLiveData(false)
    var issearchingFriend = false
    var isUserMenuActive = false
}
