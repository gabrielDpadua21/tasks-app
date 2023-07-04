package com.example.mytasksapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mytasksapp.R
import com.example.mytasksapp.databinding.FragmentHomeBinding
import com.example.mytasksapp.ui.adapter.ViewPageAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        configTabLayout()
        initClicks()
    }

    private fun initClicks() {
        binding.ibtnLogout.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        auth.signOut()
        findNavController().navigate(R.id.action_homeFragment_to_navigation)
    }

    private fun configTabLayout() {
        val adapter = ViewPageAdapter(requireActivity())
        binding.homeViewPage.adapter = adapter

        adapter.addFragment(TodoFragment(), "Todo")
        adapter.addFragment(DoingFragment(), "Doing")
        adapter.addFragment(DoneFragment(), "Done")

        binding.homeViewPage.offscreenPageLimit = adapter.itemCount

        TabLayoutMediator(
            binding.homeTabs, binding.homeViewPage
        ) {
            tab, position ->
            tab.text = adapter.getTitle(
                position
            )
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}