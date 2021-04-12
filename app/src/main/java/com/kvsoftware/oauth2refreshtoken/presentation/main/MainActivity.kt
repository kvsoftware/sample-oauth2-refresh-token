package com.kvsoftware.oauth2refreshtoken.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
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

        viewModel.initialize()
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
        viewModel.isLogged.observe(this, Observer {
            if (it) {
                binding.apply {
                    buttonLogin.isEnabled = false
                    buttonLogout.isEnabled = true
                    buttonGetData.isEnabled = true
                }
            } else {
                binding.apply {
                    buttonLogin.isEnabled = true
                    buttonLogout.isEnabled = false
                    buttonGetData.isEnabled = false
                }
            }
        })
    }

}