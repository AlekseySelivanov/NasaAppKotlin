package com.example.nasaappkotlin.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.nasaappkotlin.R
import com.example.nasaappkotlin.databinding.FragmentTodayBinding
import com.example.nasaappkotlin.ui.main.PictureOfTheDayData
import com.example.nasaappkotlin.ui.main.PictureOfTheDayViewModel
import com.example.nasaappkotlin.util.BeginDelayedTransition
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_today.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.*

class TodayFragment : Fragment() {
    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!
    private var isExpanded = false
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData(null).observe(viewLifecycleOwner, {
            renderData(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageViewToday.setOnClickListener {    isExpanded = !isExpanded
            BeginDelayedTransition(binding.todayStart)
            val params: ViewGroup.LayoutParams =  binding.imageViewToday.layoutParams
            params.height =
                    if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
            binding.imageViewToday.layoutParams = params
            binding.imageViewToday.scaleType =
                    if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        }
    }





    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                val explanation = serverResponseData.explanation
                val title = serverResponseData.title
                if (url.isNullOrEmpty()) {
                } else {
                    val imageView = view?.findViewById<ImageView>(R.id.image_view_today)
                    imageView?.load(url) {
                        lifecycle(this@TodayFragment)
                    }
                    val textView = view?.findViewById<TextView>(R.id.bottom_sheet_description)
                    if (!explanation.isNullOrEmpty()) {
                        textView?.text = explanation
                    }
                    val textView2 = view?.findViewById<TextView>(R.id.bottom_sheet_description_header)
                    if (!title.isNullOrEmpty()) {
                        textView2?.text = title
                    }
                }
            }
            is PictureOfTheDayData.Loading -> {
                //загрузка
            }
            is PictureOfTheDayData.Error -> {
                //ошибка
            }
        }
    }

}