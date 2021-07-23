package com.assignment.demo.starwarsapp.di.modules

import com.assignment.demo.starwarsapp.details.DetailsFragment
import com.assignment.demo.starwarsapp.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun homeFragment(): HomeFragment?

    @ContributesAndroidInjector
    fun detailsFragment(): DetailsFragment?
}