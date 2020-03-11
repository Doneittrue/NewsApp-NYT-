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
import com.andrew.newsapp.domain.Error
import com.andrew.newsapp.presentation.checkConnectivity
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment() {

    private val viewModel by lazy {
        NewsViewModelFactory(requireActivity().checkConnectivity())
            .let { ViewModelProvider(this, it)[NewsViewModel::class.java] }
    }

    private val callbacks by lazy { TopStoriesCallbacks { viewModel.callAgain(requireActivity().checkConnectivity()) } }

    private val topStoriesAdapter by lazy { TopStoriesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_news, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        top_stories_recyclerView.adapter = topStoriesAdapter
        observeOnTopStories()
        observeStates()
    }

    private fun observeOnTopStories() = with(viewModel) {
        retrieveTopStories(callbacks, 10).observe(viewLifecycleOwner, Observer {
            this.onNonEmptyResult(!it.isNullOrEmpty())
            topStoriesAdapter.submitList(it)
        })
    }

    private fun observeStates() =
        viewModel.state.observe(viewLifecycleOwner, Observer { changeStates(it) })

    private fun changeStates(state: TopStoriesState) = when (state) {
        is Idle -> onIdle()
        is Loading -> onLoading()
        is Success -> onSuccess()
        is Error -> onError(state.message)
    }

    private fun onIdle() {
        top_stories_recyclerView.visibility = View.VISIBLE
        top_stories_progressBar.visibility = View.GONE
        top_stories_error_textView.visibility = View.GONE
        top_stories_retry_textView.visibility = View.GONE
    }

    private fun onLoading() {
        top_stories_recyclerView.visibility = View.GONE
        top_stories_progressBar.visibility = View.VISIBLE
        top_stories_error_textView.visibility = View.GONE
        top_stories_retry_textView.visibility = View.GONE
    }

    private fun onSuccess() {
        top_stories_recyclerView.visibility = View.VISIBLE
        top_stories_progressBar.visibility = View.GONE
        top_stories_error_textView.visibility = View.GONE
        top_stories_retry_textView.visibility = View.GONE
    }

    private fun onError(message: String) {
        top_stories_recyclerView.visibility = View.GONE
        top_stories_progressBar.visibility = View.GONE
        top_stories_error_textView.visibility = View.VISIBLE
        top_stories_error_textView.text = message
        top_stories_retry_textView.visibility = View.VISIBLE
    }
}
