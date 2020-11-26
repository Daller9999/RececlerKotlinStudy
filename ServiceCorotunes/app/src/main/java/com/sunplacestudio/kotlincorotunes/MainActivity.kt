package com.sunplacestudio.kotlincorotunes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sunplacestudio.kotlincorotunes.databinding.ActivityMainBinding
import com.sunplacestudio.kotlincorotunes.viewModel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainVM = MainActivityViewModel(applicationContext)
    }
}
