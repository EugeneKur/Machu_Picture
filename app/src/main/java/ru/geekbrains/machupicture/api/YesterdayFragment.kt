package geekbarains.material.ui.api

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionSet
import coil.load
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_before_yesterday.*
import kotlinx.android.synthetic.main.fragment_today.*
import kotlinx.android.synthetic.main.fragment_yesterday.*
import kotlinx.android.synthetic.main.main_fragment.*
import ru.geekbrains.machupicture.R
import ru.geekbrains.machupicture.api.TodayFragmentViewModel
import ru.geekbrains.machupicture.api.YesterdayFragmentViewModel
import ru.geekbrains.machupicture.picture.PictureOfTheDayData

private const val DATE_TODAY = "2022-02-08"
private const val DATE_YESTERDAY = "2022-02-07"
private const val DATE_BEFORE_YESTERDAY = "2022-02-06"

var TITLE_YESTERDAY = ""
var EXPLANATION_YESTERDAY = ""

class YesterdayFragment : Fragment() {
    private var isExpanded = false

    private lateinit var viewModel: YesterdayFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_yesterday, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(YesterdayFragmentViewModel::class.java)
        viewModel.getData(DATE_YESTERDAY).observe(viewLifecycleOwner, Observer<PictureOfTheDayData> { renderData(it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image_view_yesterday.setOnClickListener {
            isExpanded = !isExpanded
            androidx.transition.TransitionManager.beginDelayedTransition(transition_container_yesterday, TransitionSet()
                .addTransition(ChangeBounds())
                .addTransition(ChangeImageTransform())
            )
            val params: ViewGroup.LayoutParams = image_view_yesterday.layoutParams
            params.height =
                if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
            image_view_yesterday.layoutParams = params
            image_view_yesterday.scaleType =
                if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        }
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                TITLE_YESTERDAY = serverResponseData.title.toString()
                EXPLANATION_YESTERDAY = serverResponseData.explanation.toString()
                if (url.isNullOrEmpty()) {
                    Toast.makeText(context, "Пустая ссылка", Toast.LENGTH_SHORT)
                } else {
                    image_view_yesterday.load(url) {
                        lifecycle(viewLifecycleOwner)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo)
                    }
                    TransitionManager.beginDelayedTransition(transition_container_yesterday, Fade().setDuration(500))
                    image_view_yesterday.visibility = View.VISIBLE
                    image_view_yesterday_start.visibility = View.GONE
                }
            }
            is PictureOfTheDayData.Loading -> {

            }
            is PictureOfTheDayData.Error -> {
                Toast.makeText(context, data.error.message, Toast.LENGTH_SHORT)
            }
        }
    }

}


