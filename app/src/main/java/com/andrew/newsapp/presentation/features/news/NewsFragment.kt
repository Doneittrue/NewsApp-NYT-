package com.andrew.newsapp.presentation.features.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

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

    private val topStoriesAdapter by lazy { TopStoriesAdapter{viewModel.updateStory(it)} }

    private val retryButton by lazy {
        top_stories_error_sheet.findViewById<Button>(R.id.retry_Button)
    }

    private val errorTextView by lazy {
        top_stories_error_sheet.findViewById<TextView>(R.id.error_textView)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_news, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retryButton.setOnClickListener {
            viewModel.refreshTopStories(requireActivity().checkConnectivity(), types[0])
        }
        top_stories_recyclerView.adapter = topStoriesAdapter
        observeOnTopStories()
        observeStates()
    }

    private fun observeOnTopStories() = with(viewModel) {
        retrieveTopStories(callbacks, 10).observe(viewLifecycleOwner, Observer {
            this.onNonEmptyResult(!it.isNullOrEmpty(), requireActivity().checkConnectivity())
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
        is Empty -> onEmpty(state.message)
    }

    private fun onIdle() {
        empty_state_textView.visibility = View.GONE
        top_stories_recyclerView.visibility = View.VISIBLE
        top_stories_progressBar.visibility = View.GONE
        top_stories_error_sheet.visibility = View.GONE
    }

    private fun onLoading() {
        empty_state_textView.visibility = View.GONE
        top_stories_recyclerView.visibility = View.VISIBLE
        top_stories_recyclerView.stopScroll()
        top_stories_progressBar.visibility = View.VISIBLE
        top_stories_error_sheet.visibility = View.GONE
    }

    private fun onSuccess() {
        empty_state_textView.visibility = View.GONE
        top_stories_recyclerView.visibility = View.VISIBLE
        top_stories_progressBar.visibility = View.GONE
        top_stories_error_sheet.visibility = View.GONE
    }

    private fun onError(message: String) {
        empty_state_textView.visibility = View.GONE
        top_stories_recyclerView.visibility = View.VISIBLE
        top_stories_progressBar.visibility = View.GONE
        errorTextView.text = message
        top_stories_error_sheet.visibility = View.VISIBLE
    }

    private fun onEmpty(message: String) {
        empty_state_textView.visibility = View.VISIBLE
        top_stories_recyclerView.visibility = View.GONE
        top_stories_progressBar.visibility = View.GONE
        errorTextView.text = message
        top_stories_error_sheet.visibility = View.VISIBLE
    }
}
