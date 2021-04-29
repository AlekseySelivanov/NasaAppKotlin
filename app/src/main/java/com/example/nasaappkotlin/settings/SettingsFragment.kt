package com.example.nasaappkotlin.settings

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.content.Intent.getIntentOld
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasaappkotlin.R
import com.example.nasaappkotlin.ui.main.PictureOfTheDayFragment
import com.google.android.material.chip.ChipGroup


class SettingsFragment : Fragment() {

    private val NAME_SHARED_PREFERENCE = "LOGIN"
    private val APP_THEME = "APP_THEME"
    private val MARS_THEME = 0
    private val SPACE_THEME = 1
    private val MOON_THEME = 2

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.setTheme(getAppTheme(R.style.Theme_NasaAppKotlin))
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChipGroup()
    }
    //Получить тему
    private fun getAppTheme(codeStyle: Int): Int {
        return codeStyleToStyleId(getCodeStyle(codeStyle))
    }
    //Ищем тему по номеру
    private fun codeStyleToStyleId(codestyle: Int): Int {
        return when (codestyle) {
            MARS_THEME -> R.style.Mars
            SPACE_THEME -> R.style.Theme_NasaAppKotlin
            MOON_THEME -> R.style.Moon
            else -> R.style.Mars
        }
    }
    //Ищем код
    private fun getCodeStyle(codestyle: Int): Int {
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(NAME_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        return sharedPref?.getInt(APP_THEME, codestyle)!!
    }

    //Инициализируем Chip
    private fun initChipGroup() {
        clickedChip(view?.findViewById(R.id.mars_theme), MARS_THEME)
        clickedChip(view?.findViewById(R.id.space_theme), SPACE_THEME)
        clickedChip(view?.findViewById(R.id.moon_theme), MOON_THEME)
        val chipGroup = view?.findViewById<ChipGroup>(R.id.chip_group)
        chipGroup?.isSelectionRequired = true;
    }
    //Обработка нажатия Chip
    private fun clickedChip(chip: View?, codestyle: Int) {
        chip?.setOnClickListener {
            setAppTheme(codestyle)
            activity?.recreate()

        }
    }
    //Записываем тему
    private fun setAppTheme(codestyle: Int) {
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(NAME_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.putInt(APP_THEME, codestyle)
        editor?.apply()
    }
    }

