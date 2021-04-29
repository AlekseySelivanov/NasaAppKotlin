package com.example.nasaappkotlin.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.nasaappkotlin.R
import com.example.nasaappkotlin.ui.main.PictureOfTheDayData
import com.example.nasaappkotlin.ui.main.PictureOfTheDayViewModel


class YesterdayFragment : Fragment() {

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_yesterday, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val currentDate = Date()
        val date = "${currentDate.year}-${currentDate.month}-${currentDate.day-1}"
        viewModel.getData(date).observe(viewLifecycleOwner, Observer<PictureOfTheDayData> { renderData(it) })
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    //Отображение ошибки
                } else {
                    val imageView = view?.findViewById<ImageView>(R.id.image_view_yesterday);
                    imageView?.load(url) {
                        lifecycle(this@YesterdayFragment)

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
}