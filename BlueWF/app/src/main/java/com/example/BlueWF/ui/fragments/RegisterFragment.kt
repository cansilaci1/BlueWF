package com.example.BlueWF.ui.fragments

import android.content.Context
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
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        // Ülke isimlerini Spinner'a yükle
        viewModel.countryList.observe(viewLifecycleOwner, Observer { countryList ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, countryList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCountry.adapter = adapter
        })

        // Kullanıcı bilgilerini al ve GreenHeaderView'e gönder
        binding.btnLogin.setOnClickListener {
            val name = binding.etName.text.toString()
            val country = binding.spinnerCountry.selectedItem?.toString() ?: ""

            if (name.isNotBlank() && country.isNotBlank()) {
                // Verileri SharedPreferences'e kaydet
                val sharedPref = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("name", name)
                    putString("country", country)
                    apply()
                }

                // Ana sayfaya geçiş yap
                Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_homeFragment)
            } else {
                Toast.makeText(requireContext(), "Tüm alanları doldurun!", Toast.LENGTH_SHORT).show()
            }
        }

        // Hata mesajlarını göster
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            Log.e("RegisterFragment", errorMessage)
        })

        viewModel.fetchCountries()

        return binding.root
    }
}
