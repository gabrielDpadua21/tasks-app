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
import com.example.mytasksapp.databinding.FragmentRecoverAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RecoverAccountFragment : Fragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks() {
        binding.btnSendRecoverMail.setOnClickListener{
            validateData()
        }
    }

    private fun validateData() {
        val email = binding.edtEmailRecover.text.toString().trim()

        if (email.isEmpty()) {
            Toast.makeText(requireContext(), "Preencha o campo de e-mail", Toast.LENGTH_SHORT).show()
            return
        }

        binding.progressBarRecover.isVisible = true
        sendMail(email)
    }

    private fun sendMail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_recoverAccountFragment_to_loginFragment)
                } else {
                    binding.progressBarRecover.isVisible = false
                    Toast.makeText(requireContext(), "Erro ao testar enviar e-mail", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}