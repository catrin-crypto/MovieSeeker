package com.example.movieseeker.framework.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.movieseeker.AppState
import com.example.movieseeker.model.repository.Repository
import java.lang.Thread.sleep

class MainViewModel(private val repository: Repository) : ViewModel(),LifecycleObserver {
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun getLiveData() = liveDataToObserve

    fun getMovie() = getDataFromMovieDB()

    private fun getDataFromMovieDB(){
        liveDataToObserve.value = AppState.Loading
        Thread{
            sleep(1000)
            liveDataToObserve.postValue(AppState.Success(repository.getMovieFromServer()))
        }.start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onViewStart(){
        Log.i("LifecycleEvent","onStart")
    }
}