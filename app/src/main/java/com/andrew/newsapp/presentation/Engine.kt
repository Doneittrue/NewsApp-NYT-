package com.andrew.newsapp.presentation

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager

fun Activity.checkConnectivity(): Boolean =
(getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
.run { activeNetworkInfo != null && activeNetworkInfo.isConnected }