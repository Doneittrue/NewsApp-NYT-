package com.andrew.newsapp.presentation

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.andrew.newsapp.R
import com.bumptech.glide.Glide

@Suppress("DEPRECATION")
fun Activity.checkConnectivity(): Boolean =
    (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        .run { activeNetworkInfo != null && activeNetworkInfo.isConnected }

@BindingAdapter("loadImageFromServer")
fun ImageView.loadImageFromServer(url: String?) = url
    .let { Glide.with(context).load(it).placeholder(R.drawable.place_holder).into(this) }