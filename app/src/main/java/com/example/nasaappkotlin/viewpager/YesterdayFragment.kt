package com.example.nasaappkotlin.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.nasaappkotlin.R
import com.example.nasaappkotlin.databinding.FragmentTodayBinding
import com.example.nasaappkotlin.databinding.FragmentYesterdayBinding
import com.example.nasaappkotlin.ui.main.PictureOfTheDayData
import com.example.nasaappkotlin.ui.main.PictureOfTheDayViewModel
import com.example.nasaappkotlin.util.BeginDelayedTransition
import java.text.SimpleDateFormat
import java.util.*

class YesterdayFragment : Fragment() {
    private var _binding: FragmentYesterdayBinding? = null
    private val binding get() = _binding!!
    private var isExpanded = false
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentYesterdayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData(yesterdayDate()).observe(viewLifecycleOwner, {
            renderData(it)
        })
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageViewYesterday.setOnClickListener {    isExpanded = !isExpanded
            BeginDelayedTransition(binding.yesterdayFragment)
            val params: ViewGroup.LayoutParams =  binding.imageViewYesterday.layoutParams
            params.height =
                if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
            binding.imageViewYesterday.layoutParams = params
            binding.imageViewYesterday.scaleType =
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
                    //Отображение ошибки
                } else {
                    val imageView = view?.findViewById<ImageView>(R.id.image_view_yesterday)
                    imageView?.load(url) {
                        lifecycle(this@YesterdayFragment)
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
                //Загрузка
            }
            is PictureOfTheDayData.Error -> {
                //Ошибка
            }
        }
    }

    private fun yesterdayDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -1)
        return formatter.format(calendar.time)
    }
}