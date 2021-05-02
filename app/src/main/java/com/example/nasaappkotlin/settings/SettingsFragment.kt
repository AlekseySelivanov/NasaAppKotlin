package com.example.nasaappkotlin.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nasaappkotlin.MainActivity
import com.example.nasaappkotlin.R
import com.example.nasaappkotlin.databinding.FragmentSettingsBinding


const val SETTINGS_SHARED_PREFERENCES = "SettingsSharedPreferences"
const val THEME_RES_ID = "ThemeResID"
private const val THEME_NAME_SHARED_PREFERENCES = "ThemeNameSharedPreferences"
const val MY_DEFAULT_THEME = 0
const val MY_CUSTOM_THEME_GREY = 1
const val My_CUSTOM_THEME_ORANGE = 2
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var currentTheme: Int? = null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    private fun initView() {
        setSharedPreferencesSettings()
        binding.spaceTheme.setOnClickListener {
            if (currentTheme != MY_DEFAULT_THEME) {
                requireActivity().apply {
                    setTheme(R.style.Theme_NasaAppKotlin)
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    saveThemeSettings(MY_DEFAULT_THEME, R.style.Theme_NasaAppKotlin)
                }
            }
        }
        binding.moonTheme.setOnClickListener {
            if (currentTheme != MY_CUSTOM_THEME_GREY) {
                requireActivity().apply {
                    setTheme(R.style.Moon)
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    saveThemeSettings(MY_CUSTOM_THEME_GREY, R.style.Moon)
                }
            }
        }
        binding.marsTheme.setOnClickListener {
            if (currentTheme != My_CUSTOM_THEME_ORANGE) {
                requireActivity().apply {
                    setTheme(R.style.Mars)
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    saveThemeSettings(My_CUSTOM_THEME_ORANGE, R.style.Mars)
                }
            }
        }
    }

    private fun setSharedPreferencesSettings() {
        activity?.let {
            currentTheme =
                    it.getSharedPreferences(SETTINGS_SHARED_PREFERENCES, Context.MODE_PRIVATE)
                            .getInt(THEME_NAME_SHARED_PREFERENCES, MY_DEFAULT_THEME)
            when (currentTheme) {
                MY_CUSTOM_THEME_GREY -> {
                    binding.moonTheme.isChecked = true
                }
                My_CUSTOM_THEME_ORANGE -> {
                    binding.marsTheme.isChecked = true
                }
                else -> {
                    binding.spaceTheme.isChecked = true
                }
            }
        }
    }

    private fun saveThemeSettings(currentTheme: Int, style: Int) {
        this.currentTheme = currentTheme
        activity?.let {
            with(it.getSharedPreferences(SETTINGS_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit()) {
                putInt(THEME_NAME_SHARED_PREFERENCES, currentTheme).apply()
                putInt(THEME_RES_ID, style).apply()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    }

