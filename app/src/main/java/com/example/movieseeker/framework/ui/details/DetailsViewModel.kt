package com.example.movieseeker.framework.ui.details

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieseeker.AppState
import com.example.movieseeker.model.entities.Movie
import com.example.movieseeker.model.repository.Repository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.koinApplication

class DetailsViewModel(private val repository: Repository) : ViewModel(), LifecycleObserver {

    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun loadData(id: Int, language :String) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            val data = repository.getMovieFromServer(id,language)
            liveDataToObserve.postValue(AppState.Success(listOf(data)))
       }.start()
    }
}