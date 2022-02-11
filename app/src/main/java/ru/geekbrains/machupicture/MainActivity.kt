package ru.geekbrains.machupicture

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import ru.geekbrains.machupicture.picture.PictureOfTheDayFragment
import android.content.SharedPreferences





class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        IS_BASE = sharedPreferences.getBoolean("key1", false)
        if (IS_BASE) {
            setTheme(R.style.Theme_MachuPicture)
        } else {
            setTheme(R.style.Theme_MachuPictureAngular)
        }
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
    companion object {
        var IS_BASE = true
    }
}




