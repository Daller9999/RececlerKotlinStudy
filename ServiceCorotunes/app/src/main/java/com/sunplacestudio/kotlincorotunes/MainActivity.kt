package com.sunplacestudio.kotlincorotunes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sunplacestudio.kotlincorotunes.databinding.ActivityMainBinding
import com.sunplacestudio.kotlincorotunes.viewModel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainActivityViewModel = MainActivityViewModel(applicationContext)
        binding.mainVM = mainActivityViewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivityViewModel.dismiss()
    }
}
