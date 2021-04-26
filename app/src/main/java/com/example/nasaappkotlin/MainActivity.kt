package com.example.nasaappkotlin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nasaappkotlin.settings.SETTINGS_SHARED_PREFERENCE
import com.example.nasaappkotlin.settings.THEME_RES_ID
import com.example.nasaappkotlin.databinding.MainActivityBinding
import com.example.nasaappkotlin.ui.main.PictureOfTheDayFragment



class MainActivity : AppCompatActivity() {



    private lateinit var binding: MainActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        val resIdTheme = getSharedPreferences(SETTINGS_SHARED_PREFERENCE, Context.MODE_PRIVATE)
            .getInt(THEME_RES_ID, R.style.Theme_NasaAppKotlin)
        setTheme(resIdTheme)
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                    .commitNow()
        }
    }
}