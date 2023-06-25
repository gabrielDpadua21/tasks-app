package com.example.mytasksapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.mytasksapp.R
import com.example.mytasksapp.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks() {
        binding.btBackLogin.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.btnCreate.setOnClickListener{
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        val confirmPassword = binding.edtPasswordConfirm.text.toString().trim()

        if (email.isEmpty() || password.isEmpty() || !password.equals(confirmPassword)) {
            showMessage(email, password, confirmPassword)
            Toast.makeText(requireContext(), "Error to create user!!!", Toast.LENGTH_SHORT).show()
            return
        }

        binding.progressBarRegister.isVisible = true
        registerUser(email, password)
    }

    private fun showMessage(email: String, password: String, confirmePassword: String) {
        if(email.isEmpty()) {
            Toast.makeText(requireContext(), "Invalid E-mail!!!", Toast.LENGTH_SHORT).show()
            return
        }
        if (password.isEmpty()) {
            Toast.makeText(requireContext(), "Invalid password!!!", Toast.LENGTH_SHORT).show()
            return
        }
        if (!password.equals(confirmePassword)) {
            Toast.makeText(requireContext(), "The password should be the same!!!", Toast.LENGTH_SHORT).show()
            return
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_global_homeFragment2)
                } else {
                    binding.progressBarRegister.isVisible = false
                    val message = if (task.exception?.message !== null) task.exception?.message else "Error on create user!!!"
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}