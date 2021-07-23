package com.assignment.demo.starwarsapp.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.demo.starwarsapp.base.BaseApiResponseModel
import com.assignment.demo.starwarsapp.datamodel.peoples.PeopleResponseModel
import com.assignment.demo.starwarsapp.datamodel.peoples.Results
import com.assignment.demo.starwarsapp.repository.ApiRepository
import com.assignment.demo.starwarsapp.utils.DataConverter
import com.assignment.demo.starwarsapp.utils.LiveDataUtils
import io.reactivex.rxjava3.core.Single

class HomeViewModel(
    private val apiRepository: ApiRepository
) : ViewModel() {

    private val peopleResponseMutableLiveData: MutableLiveData<BaseApiResponseModel<PeopleResponseModel>> =
        MutableLiveData()

    fun getPeopleResponseMutableLiveData(): MutableLiveData<BaseApiResponseModel<PeopleResponseModel>> {
        return peopleResponseMutableLiveData;
    }

    fun searchPeoplesResponseLiveData(searchQuery: String) {
        val baseSearchApiResponseModelSingle: Single<PeopleResponseModel?>? =
            apiRepository.search(searchQuery);
        LiveDataUtils.updateStatus(peopleResponseMutableLiveData, baseSearchApiResponseModelSingle)
    }

    fun filterData(it: Any, dataConverter: DataConverter): ArrayList<Results> {
        return dataConverter.convertToSearchModelList(it as PeopleResponseModel)
    }

    private val remoteResponsePeopleMutableLiveData: MutableLiveData<BaseApiResponseModel<PeopleResponseModel>> =
        MutableLiveData()

    fun getRemoteResponsePeopleMutableLiveData(): MutableLiveData<BaseApiResponseModel<PeopleResponseModel>> {
        return remoteResponsePeopleMutableLiveData;
    }

    fun getPeoples() {
        val baseSearchApiResponseModelSingle: Single<PeopleResponseModel>? =
            apiRepository.starList();
        LiveDataUtils.updateStatus(remoteResponsePeopleMutableLiveData, baseSearchApiResponseModelSingle)
    }

    private val remoteLoadMoreMutableLiveData: MutableLiveData<BaseApiResponseModel<PeopleResponseModel>> =
        MutableLiveData()

    fun getRemoteLoadMoreMutableLiveData(): MutableLiveData<BaseApiResponseModel<PeopleResponseModel>> {
        return remoteLoadMoreMutableLiveData;
    }
    fun getPeopleLoadMore(url:String?) {
        val baseSearchApiResponseModelSingle: Single<PeopleResponseModel>? =
            apiRepository.loadMore(url);
        LiveDataUtils.updateStatus(remoteLoadMoreMutableLiveData, baseSearchApiResponseModelSingle)
    }

}