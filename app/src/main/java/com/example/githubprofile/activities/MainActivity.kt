package com.example.githubprofile.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubprofile.R
import com.example.githubprofile.databinding.ActivityMainBinding
import com.example.githubprofile.models.GithubUser
import com.example.githubprofile.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // Menginisialisasi ViewModel
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        // Mengamati data profil GitHub
        mainViewModel.githubUser.observe(this) { user ->
            if (user != null) {
                setUserData(user)
            }
        }

        // Mengamati status loading
        mainViewModel.isLoading.observe(this) { loading ->
            showLoading(loading == true)
        }

        // Tombol pencarian username
        binding.btnSearchUserLogin.setOnClickListener {
            val userLogin =
                binding.etSearchUserLogin.text.toString().trim()

            if (userLogin.isNotEmpty()) {
                mainViewModel.searchUser(userLogin)
            }
        }
    }

    private fun setUserData(githubUser: GithubUser) {
        binding.tvUser.text = githubUser.toString()

        Glide.with(this)
            .load(githubUser.avatarUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
            )
            .into(binding.imgUser)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }
}
