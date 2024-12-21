package com.example.BlueWF.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.BlueWF.R
import com.example.BlueWF.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentSplashBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_splashFragment_to_registerFragment)
        }
        return binding.root
    }

}