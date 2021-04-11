package com.kvsoftware.oauth2refreshtoken.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.kvsoftware.oauth2refreshtoken.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()
        initializeView()
        initializeObserver()
    }

    private fun initializeBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initializeView() {
        binding.apply {
            buttonLogin.setOnClickListener { viewModel.login() }
            buttonLogout.setOnClickListener { viewModel.logout() }
            buttonGetData.setOnClickListener { viewModel.getData() }
        }
    }

    private fun initializeObserver() {
        viewModel.isLoading.observe(this, {
            binding.progressbar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

}