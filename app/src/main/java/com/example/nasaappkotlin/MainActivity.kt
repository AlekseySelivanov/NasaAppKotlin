package com.example.nasaappkotlin


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nasaappkotlin.settings.SETTINGS_SHARED_PREFERENCES
import com.example.nasaappkotlin.settings.THEME_RES_ID
import com.example.nasaappkotlin.ui.main.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(
                getSharedPreferences(SETTINGS_SHARED_PREFERENCES, MODE_PRIVATE)
                        .getInt(THEME_RES_ID, R.style.Theme_NasaAppKotlin)
        )
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                    .commitNowAllowingStateLoss()
        }
    }
}