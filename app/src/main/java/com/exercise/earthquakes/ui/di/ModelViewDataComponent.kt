package com.exercise.earthquakes.ui.di

import com.exercise.earthquakes.Repository
import dagger.Component

/**
 * Created by nativlevy on 2/12/18.
 */
@Component(modules = [ModelViewDataModule::class])
interface ModelViewDataComponent {

    fun inject(repository: Repository)
}