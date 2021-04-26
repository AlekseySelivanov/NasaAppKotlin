package com.example.nasaappkotlin.settings

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasaappkotlin.R
import com.example.nasaappkotlin.databinding.FragmentChipsBinding

const val SETTINGS_SHARED_PREFERENCE = "SETTINGS_SHARED_PREFERENCE"
const val THEME_NAME_SHARED_PREFERENCE = "THEME_NAME_SHARED_PREFERENCE"
const val THEME_RES_ID = "THEME_RES_ID"
const val SPACE = "SPACE"
const val MARS = "MARS"
const val MOON = "MOON"

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentChipsBinding
    private lateinit var themeName: String




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setSharedPreferenceSettings()
        binding.marsTheme.apply {
            setOnClickListener {
                if (themeName != MARS) {
                    saveThemeSettings(MARS, R.style.Mars)
                    activity?.recreate()
                }
            }
        }
        binding.spaceTheme.apply {
            setOnClickListener {
                if (themeName != SPACE) {
                    saveThemeSettings(SPACE, R.style.Theme_NasaAppKotlin)
                    activity?.recreate()
                }
            }
        }
        binding.moonTheme.apply {
            setOnClickListener {
                if (themeName != MOON) {
                    saveThemeSettings(MOON, R.style.Moon)
                    activity?.recreate()
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val resIdTheme =
            requireActivity().getSharedPreferences(SETTINGS_SHARED_PREFERENCE, Context.MODE_PRIVATE)
                .getInt(THEME_RES_ID, R.style.Theme_NasaAppKotlin)
        val inflaterNewTheme = LayoutInflater.from(ContextThemeWrapper(context, resIdTheme))
        binding = FragmentChipsBinding.inflate(inflaterNewTheme, container, false)
        return binding.root
    }

    private fun saveThemeSettings(themeName: String, id: Int) {
        this.themeName = themeName
        activity?.let {
            with(it.getSharedPreferences(SETTINGS_SHARED_PREFERENCE, Context.MODE_PRIVATE).edit()) {
                putString(THEME_NAME_SHARED_PREFERENCE, themeName).apply()
                putInt(THEME_RES_ID, id).apply()
            }
        }
    }

    private fun setSharedPreferenceSettings() {
        activity?.let {
            themeName =
                it.getSharedPreferences(SETTINGS_SHARED_PREFERENCE, Context.MODE_PRIVATE)
                    .getString(THEME_NAME_SHARED_PREFERENCE, SPACE).toString()
            when(themeName) {
                MOON -> {
                    binding.moonTheme.isChecked = true
                }
                MARS -> {
                    binding.marsTheme.isChecked = true
                }
                else -> {
                    binding.spaceTheme.isChecked = true
                }
            }
        }
    }



    }

