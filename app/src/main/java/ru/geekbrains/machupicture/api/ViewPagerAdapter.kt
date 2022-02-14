package ru.geekbrains.machupicture.api

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import geekbarains.material.ui.api.TodayFragment
import geekbarains.material.ui.api.YesterdayFragment
import geekbarains.material.ui.api.BeforeYesterdayFragment

private const val TODAY_FRAGMENT = 0
private const val YESTERDAY_FRAGMENT = 1
private const val BEFORE_YESTERDAY_FRAGMENT = 2

class ViewPagerAdapter(private val fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(TodayFragment(), YesterdayFragment(), BeforeYesterdayFragment())

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> fragments[TODAY_FRAGMENT]
            1 -> fragments[YESTERDAY_FRAGMENT]
            2 -> fragments[BEFORE_YESTERDAY_FRAGMENT]
            else -> fragments[TODAY_FRAGMENT]
        }
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Сегодня"
            1 -> "Вчера"
            2 -> "Позавчера"
            else -> "Earth"
        }
    }
}