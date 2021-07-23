package com.assignment.demo.starwarsapp.api_service

import com.assignment.demo.starwarsapp.datamodel.peoples.PeopleResponseModel
import com.assignment.demo.starwarsapp.datamodel.peoples.Results
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface StarWarsApiRequest {

    @GET("people/?")
    fun search(
        @Query("search") queryText: String,
        @Query("format") format: String
    ): Single<PeopleResponseModel?>?

    @GET("people/{query}/")
    fun details(
        @Path("query") queryText: String,
        @Query("format") format: String
    ): Single<Results?>?

    @GET()
    fun listItemDetails(
        @Url queryText: String,
    ): Single<Results?>?

    @GET("people")
    fun starList(): Single<PeopleResponseModel>

    @GET()
    fun loadMore(@Url url:String?):Single<PeopleResponseModel>

}