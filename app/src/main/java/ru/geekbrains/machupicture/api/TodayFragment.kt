package geekbarains.material.ui.api

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.*
import coil.load
import kotlinx.android.synthetic.main.fragment_today.*
import ru.geekbrains.machupicture.R
import ru.geekbrains.machupicture.api.TodayFragmentViewModel
import ru.geekbrains.machupicture.picture.*

private const val DATE_TODAY = "2022-02-08"
private const val DATE_YESTERDAY = "2022-02-07"
private const val DATE_BEFORE_YESTERDAY = "2022-02-06"
var TITLE_TODAY = ""
var EXPLANATION_TODAY = ""

class TodayFragment : Fragment() {
    private var isExpanded = false

    private lateinit var viewModel: TodayFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_today, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TodayFragmentViewModel::class.java)
        viewModel.getData(DATE_TODAY).observe(viewLifecycleOwner, Observer<PictureOfTheDayData> { renderData(it) })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image_view_today.setOnClickListener {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(transition_container_today, TransitionSet()
                .addTransition(ChangeBounds())
                .addTransition(ChangeImageTransform())
            )
            val params: ViewGroup.LayoutParams = image_view_today.layoutParams
            params.height =
                if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
            image_view_today.layoutParams = params
            image_view_today.scaleType =
                if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        }
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                TITLE_TODAY = serverResponseData.title.toString()
                EXPLANATION_TODAY = serverResponseData.explanation.toString()
                if (url.isNullOrEmpty()) {
                    Toast.makeText(context, "Пустая ссылка", Toast.LENGTH_SHORT)
                } else {
                    image_view_today.load(url) {
                            lifecycle(viewLifecycleOwner)
                            error(R.drawable.ic_load_error_vector)
                            placeholder(R.drawable.ic_no_photo)
                        }
                    TransitionManager.beginDelayedTransition(transition_container_today, Fade().setDuration(500))
                    image_view_today.visibility = View.VISIBLE
                    image_view_today_start.visibility = View.GONE

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

