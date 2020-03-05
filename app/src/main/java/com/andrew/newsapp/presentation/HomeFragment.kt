package com.andrew.newsapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.andrew.newsapp.R
import kotlinx.android.synthetic.main.fragmet_home.*

class HomeFragment : Fragment() {

    private val homeNavController by lazy { requireActivity().findNavController(R.id.home_nav_fragment) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragmet_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationView.setupWithNavController(homeNavController)
    }
}