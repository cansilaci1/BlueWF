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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        viewModel.countryList.observe(viewLifecycleOwner, Observer { countryList ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, countryList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCountry.adapter = adapter
        })

        binding.btnLogin.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val country = binding.spinnerCountry.selectedItem?.toString() ?: ""

            if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && country.isNotBlank()) {
                registerUser(email, password, name, country)
            } else {
                Toast.makeText(requireContext(), "Tüm alanları doldurun!", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            Log.e("RegisterFragment", errorMessage)
        })

        viewModel.fetchCountries()

        return binding.root
    }

    private fun registerUser(email: String, password: String, name: String, country: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    saveToSharedPreferences(name, country) // Kullanıcı bilgilerini kaydet
                    saveUserToFirestore(name, email, country)

                    val navController = Navigation.findNavController(requireView())
                    val navOptions = androidx.navigation.NavOptions.Builder()
                        .setPopUpTo(R.id.registerFragment, true)
                        .build()

                    navController.navigate(R.id.action_registerFragment_to_homeFragment, null, navOptions)

                    Toast.makeText(requireContext(), "Kayıt başarılı!", Toast.LENGTH_SHORT).show()
                } else {
                    val errorMessage = task.exception?.message ?: "Kayıt başarısız!"
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    Log.e("RegisterFragment", errorMessage)
                }
            }
    }

    private fun saveUserToFirestore(name: String, email: String, country: String) {
        val userId = auth.currentUser?.uid ?: return

        val user = hashMapOf(
            "id" to userId,
            "name" to name,
            "email" to email,
            "country" to country
        )

        // Firestore'a kaydet
        db.collection("users")
            .document(userId) // Kullanıcı UID'sini belge ID'si olarak kullan
            .set(user)
            .addOnSuccessListener {
                Log.d("RegisterFragment", "Kullanıcı Firestore'a başarıyla kaydedildi.")
            }
            .addOnFailureListener { e ->
                Log.e("RegisterFragment", "Firestore'a kaydetme hatası: ${e.message}")
            }
    }

    private fun saveToSharedPreferences(name: String, country: String) {
        val sharedPref = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("name", name)
            putString("country", country)
            apply()
        }

        // Kullanıcı bilgilerini kaydettikten sonra GreenHeaderView'i güncelle
        val greenHeaderView = requireActivity().findViewById<com.example.BlueWF.ui.view.GreenHeaderView>(R.id.greenHeader)
        greenHeaderView?.updateUserInfo()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Kullanıcı oturumu açık, HomeFragment'a yönlendir
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_homeFragment)
        }
    }

}