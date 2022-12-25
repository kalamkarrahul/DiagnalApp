package com.rahul.diagnalapp

import android.app.Application
import com.rahul.diagnalapp.di.ApplicationComponent
import com.rahul.diagnalapp.di.DaggerApplicationComponent
import dagger.Component

class MovieApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()

      applicationComponent = DaggerApplicationComponent.builder().build()
    }
}