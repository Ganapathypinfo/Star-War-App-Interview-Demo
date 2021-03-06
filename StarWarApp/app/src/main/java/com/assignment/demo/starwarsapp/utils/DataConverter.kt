package com.assignment.demo.starwarsapp.utils

import com.assignment.demo.starwarsapp.BuildConfig
import com.assignment.demo.starwarsapp.constants.AppConstants
import com.assignment.demo.starwarsapp.datamodel.peoples.PeopleResponseModel
import com.assignment.demo.starwarsapp.datamodel.peoples.Results
import javax.inject.Inject

class DataConverter @Inject constructor() {
    fun convertToSearchModelList(searchApiResponseData: PeopleResponseModel?): ArrayList<Results> {
        if (searchApiResponseData != null && !searchApiResponseData.results.isNullOrEmpty()) {
            return searchApiResponseData.results as ArrayList
        }
        return ArrayList()
    }

    fun prepareDisplayIdFromResult(result: Results): String {
        return result.name + ", " + getIdFromResult(result)
    }

    fun getIdFromResult(result: Results): String {
        val resultApi = BuildConfig.BASE_URL + AppConstants.PEOPLE_SLASH
        return result.url.replace(resultApi, "").replace("/", "")
    }

}