package com.example.BlueWF.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.BlueWF.databinding.FragmentHomeBinding
import com.example.BlueWF.ui.adapters.PredictionAdapter
import com.example.BlueWF.utils.PreferencesHelper

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Load the prediction history from SharedPreferences
        val predictions = PreferencesHelper.getPredictions(requireContext())

        // Set up RecyclerView
        val adapter = PredictionAdapter(predictions)
        binding.recyclerViewResults.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewResults.adapter = adapter

        return binding.root
    }
}
