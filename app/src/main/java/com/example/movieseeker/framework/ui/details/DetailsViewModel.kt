package com.example.movieseeker.framework.ui.details

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieseeker.AppState
import com.example.movieseeker.model.repository.Repository

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