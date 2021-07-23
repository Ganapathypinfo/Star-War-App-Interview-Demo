package com.assignment.demo.starwarsapp.di.components

import com.assignment.demo.starwarsapp.di.AppModule
import com.assignment.demo.starwarsapp.di.StarWarsApplication
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AndroidInjector<StarWarsApplication?> {
    @Component.Factory
    interface Factory : AndroidInjector.Factory<StarWarsApplication?>
}