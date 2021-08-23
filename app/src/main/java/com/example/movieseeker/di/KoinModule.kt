package com.example.movieseeker.di

import com.example.movieseeker.framework.ui.details.DetailsViewModel
import com.example.movieseeker.framework.ui.main.MainViewModel
import com.example.movieseeker.model.repository.Repository
import com.example.movieseeker.model.repository.RepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Repository> { RepositoryImpl() }

    //View models
    viewModel { MainViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
}