package com.example.nasaappkotlin


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nasaappkotlin.ui.main.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    private val NAME_SHARED_PREFERENCE = "LOGIN"
    private val APP_THEME = "APP_THEME"
    private val MARS_THEME = 0
    private val MOON_THEME = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        getAppTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                    .commitNow()
        }
    }

    //Тема из SharedPreferences
    private fun getAppTheme() {
        val sharedPreferences = getSharedPreferences(NAME_SHARED_PREFERENCE, MODE_PRIVATE)
        if (sharedPreferences != null) {
            val codeStyle = sharedPreferences.getInt(APP_THEME, 0)
            if (codeStyle == MARS_THEME) {
                setTheme(R.style.Mars)
            }else if(codeStyle == MOON_THEME)
                setTheme(R.style.Moon)
            else {
                setTheme(R.style.Theme_NasaAppKotlin)
            }
        }
    }
}