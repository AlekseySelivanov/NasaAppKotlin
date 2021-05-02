package com.example.nasaappkotlin.viewpager

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.nasaappkotlin.R
import com.example.nasaappkotlin.ui.main.PictureOfTheDayData
import com.example.nasaappkotlin.ui.main.PictureOfTheDayViewModel
import java.text.SimpleDateFormat
import java.util.*

class BeforeYesterdayFragment : Fragment() {


    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_yesterday, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData(beforeYesterdayDate()).observe(viewLifecycleOwner, {
            renderData(it)
        })
    }

    private fun beforeYesterdayDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -2)
        return formatter.format(calendar.time)
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                val explanation = serverResponseData.explanation
                val title = serverResponseData.title
                if (url.isNullOrEmpty()) {
                    toast("Url is empty")
                } else {
                    val imageView = view?.findViewById<ImageView>(R.id.image_view_yesterday);
                    imageView?.load(url) {
                        lifecycle(this@BeforeYesterdayFragment)
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
            }
            is PictureOfTheDayData.Error -> {
                toast(data.error.message)
            }
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }


    companion object {
        fun newInstance() = BeforeYesterdayFragment()
    }
}