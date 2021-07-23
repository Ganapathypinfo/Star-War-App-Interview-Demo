package com.assignment.demo.starwarsapp.repository

import com.assignment.demo.starwarsapp.api_service.StarWarsApiRequest
import com.assignment.demo.starwarsapp.constants.AppConstants
import com.assignment.demo.starwarsapp.datamodel.peoples.PeopleResponseModel
import com.assignment.demo.starwarsapp.datamodel.peoples.Results
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiRequest: StarWarsApiRequest) {

    fun search(query: String): Single<PeopleResponseModel?>? {
        return apiRequest.search(query, AppConstants.API_RESPONSE_FORMAT_JSON)
    }

    fun details(query: String): Single<Results?>? {
        return apiRequest.details(query,AppConstants.API_RESPONSE_FORMAT_JSON)
    }

    fun listitemdetails(query: String): Single<Results?>? {
        return apiRequest.listItemDetails(query)
    }


    fun starList(): Single<PeopleResponseModel>? {
        return apiRequest.starList()
    }

    fun loadMore(url:String?): Single<PeopleResponseModel>? {
        return apiRequest.loadMore(url)
    }
}