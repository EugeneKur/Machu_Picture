package ru.geekbrains.machupicture.picture

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_chips.*
import ru.geekbrains.machupicture.R
import android.content.SharedPreferences

class ChipsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chips, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
        chipGroup.setOnCheckedChangeListener { chipGroup, position ->
            when (position) {
                R.id.base -> {
                    val editor = sharedPreferences?.edit()
                    editor?.putBoolean("key1", true)
                    editor?.apply()
                }
                R.id.angle -> {
                    val editor = sharedPreferences?.edit()
                    editor?.putBoolean("key1", false)
                    editor?.apply()
                }
            }

            chipGroup.findViewById<Chip>(position)?.let {

                Toast.makeText(context, "Выбран ${it.text}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}