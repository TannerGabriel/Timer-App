package com.example.gabriel.timerapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TimerViewModelProvider(context: Context): ViewModelProvider.NewInstanceFactory(){

    val context = context

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TimerViewModel(context) as T
    }
}