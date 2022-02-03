package ru.geekbrains.machupicture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.geekbrains.machupicture.picture.PictureOfTheDayFragment



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}