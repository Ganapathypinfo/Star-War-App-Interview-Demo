package com.assignment.demo.starwarsapp.details.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.demo.starwarsapp.base.BaseApiResponseModel
import com.assignment.demo.starwarsapp.datamodel.peoples.Results
import com.assignment.demo.starwarsapp.repository.ApiRepository
import com.assignment.demo.starwarsapp.utils.LiveDataUtils
import io.reactivex.rxjava3.core.Single

class DetailsViewModel(private val apiRepository: ApiRepository) : ViewModel() {

    fun getData(arguments: Bundle): String {
        return arguments.getSerializable("selectedId") as String
    }

    fun getScreen(arguments: Bundle): String {
        return arguments.getString("Screen", null) as String
    }

    private val peopleResultsMutableLiveData: MutableLiveData<BaseApiResponseModel<Results>> =
        MutableLiveData()

    fun getPeopleResultsMutableLiveData(): MutableLiveData<BaseApiResponseModel<Results>> {
        return peopleResultsMutableLiveData
    }

    fun fetchPeopleResultsResponseLiveData(searchQuery: String) {
        val apiResponseModelSingle: Single<Results?>? =
            apiRepository.details(searchQuery)
        LiveDataUtils.updateStatus(peopleResultsMutableLiveData, apiResponseModelSingle)
    }

    fun fetchListItemPeopleResultsResponseLiveData(searchQuery: String) {
        val apiResponseModelSingle: Single<Results?>? =
            apiRepository.listItemDetails(searchQuery)
        LiveDataUtils.updateStatus(peopleResultsMutableLiveData, apiResponseModelSingle)
    }
}