package ru.geekbrains.machupicture.picture

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_before_yesterday.*
import kotlinx.android.synthetic.main.fragment_today.*
import kotlinx.android.synthetic.main.fragment_yesterday.*

import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view_pager
import ru.geekbrains.machupicture.MainActivity
import ru.geekbrains.machupicture.R
import ru.geekbrains.machupicture.api.ViewPagerAdapter

private const val TODAY = 0
private const val YESTERDAY = 1
private const val BEFORE_YESTERDAY = 2
private const val DATE_TODAY = "2022-02-08"
private const val DATE_YESTERDAY = "2022-02-07"
private const val DATE_BEFORE_YESTERDAY = "2022-02-06"

class PictureOfTheDayFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var viewModel: PictureOfTheDayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
        viewModel.getData(DATE_TODAY).observe(this@PictureOfTheDayFragment, Observer<PictureOfTheDayData> { renderData(it, TODAY) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        setBottomAppBar(view)
        view_pager.adapter = ViewPagerAdapter(childFragmentManager)

        tab_layout.setupWithViewPager(view_pager)
        tab_layout.getTabAt(0)?.setIcon(R.drawable.ic_earth)
        tab_layout.getTabAt(1)?.setIcon(R.drawable.ic_mars)
        tab_layout.getTabAt(2)?.setIcon(R.drawable.ic_weather)

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            @SuppressLint("FragmentLiveDataObserve")
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        viewModel.getData(DATE_TODAY).observe(this@PictureOfTheDayFragment, Observer<PictureOfTheDayData> { renderData(it, TODAY) })

                    }
                    1 -> {
                        viewModel.getData(DATE_YESTERDAY).observe(this@PictureOfTheDayFragment, Observer<PictureOfTheDayData> { renderData(it, YESTERDAY) })

                    }
                    2 -> {
                        viewModel.getData(DATE_BEFORE_YESTERDAY).observe(this@PictureOfTheDayFragment, Observer<PictureOfTheDayData> { renderData(it, TODAY) })

                    }
                    else -> {
                        viewModel.getData(DATE_TODAY).observe(this@PictureOfTheDayFragment, Observer<PictureOfTheDayData> { renderData(it,TODAY) })

                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float,  positionOffsetPixels: Int) {}
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> Toast.makeText(context, "message", Toast.LENGTH_SHORT)
            R.id.app_bar_settings -> activity?.supportFragmentManager?.beginTransaction()?.add(R.id.container, ChipsFragment())?.addToBackStack(null)?.commit()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        fab.setOnClickListener {
            if (isMain) {
                isMain = false
                bottom_app_bar.navigationIcon = null
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                bottom_app_bar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_plus_fab))
                bottom_app_bar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }

    private fun renderData(data: PictureOfTheDayData, fragment: Int) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                bottom_sheet_description_header.text = serverResponseData.title
                bottom_sheet_description.text = serverResponseData.explanation
                    if (url.isNullOrEmpty()) {
                    Toast.makeText(context, "Пустая ссылка", Toast.LENGTH_SHORT)
                } else {
                        when (fragment) {
                            0 -> image_view_today.load(url) {
                                lifecycle(this@PictureOfTheDayFragment)
                                error(R.drawable.ic_load_error_vector)
                                placeholder(R.drawable.ic_no_photo)
                            }
                            1 -> image_view_yesterday.load(url) {
                                lifecycle(this@PictureOfTheDayFragment)
                                error(R.drawable.ic_load_error_vector)
                                placeholder(R.drawable.ic_no_photo)
                            }
                            2 -> image_view_before_yesterday.load(url) {
                                lifecycle(this@PictureOfTheDayFragment)
                                error(R.drawable.ic_load_error_vector)
                                placeholder(R.drawable.ic_no_photo)
                            }
                            else -> image_view_today.load(url) {
                                lifecycle(this@PictureOfTheDayFragment)
                                error(R.drawable.ic_load_error_vector)
                                placeholder(R.drawable.ic_no_photo)
                            }
                        }

                }
            }
            is PictureOfTheDayData.Loading -> {

            }
            is PictureOfTheDayData.Error -> {
                Toast.makeText(context, data.error.message, Toast.LENGTH_SHORT)
            }
        }
    }


    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }



    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }
}