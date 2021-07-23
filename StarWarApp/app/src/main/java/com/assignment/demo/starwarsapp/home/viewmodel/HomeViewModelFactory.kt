package com.assignment.demo.starwarsapp.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.demo.starwarsapp.repository.ApiRepository

class HomeViewModelFactory(
    private val apiRepository: ApiRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(apiRepository) as T
    }
}