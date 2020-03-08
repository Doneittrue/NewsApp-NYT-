package com.andrew.newsapp.presentation.features.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory

import com.andrew.newsapp.R
import com.andrew.newsapp.domain.*

class NewsFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this, NewInstanceFactory())[NewsViewModel::class.java]
    }
    private var index = 0
    private val types = listOf(ARTS, HOME_STORIES, SCIENCE, U_S, WORLD)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_news, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel.topStories.observe(viewLifecycleOwner, Observer {

        //})
    }
}


