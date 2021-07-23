package com.assignment.demo.starwarsapp.di.modules

import com.assignment.demo.starwarsapp.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {
    @ContributesAndroidInjector
    fun mainActivity(): MainActivity?
}