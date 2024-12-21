package com.example.BlueWF.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.BlueWF.R
import com.example.BlueWF.databinding.FragmentRegisterBinding
import com.example.BlueWF.ui.viewmodels.RegisterViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels() // ViewModel instance oluşturuldu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        // liveData şeklinde ülke isimlerini alır
        viewModel.countryList.observe(viewLifecycleOwner, Observer { countryList ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, countryList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.countrySpinner.adapter = adapter
        })

        // error mesajı alır
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            Log.e("RegisterFragment", errorMessage)
        })

        // Fetch countries from ViewModel
        viewModel.fetchCountries()

        binding.btnLogin.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_homeFragment)
        }

        return binding.root
    }
}
