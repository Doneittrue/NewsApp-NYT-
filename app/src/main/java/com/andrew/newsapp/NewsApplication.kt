package com.andrew.newsapp

import android.app.Application
import com.andrew.newsapp.domain.AppContext

class NewsApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        AppContext.init(this)
    }
}