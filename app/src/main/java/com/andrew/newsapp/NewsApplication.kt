package com.andrew.newsapp

import android.app.Application
import com.andrew.newsapp.domain.AppContext
import timber.log.Timber

class NewsApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        AppContext.init(this)
        Timber.plant(Timber.DebugTree())
    }
}