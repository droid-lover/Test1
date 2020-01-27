package com.vs.views.activities

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.vs.R
import com.vs.utils.RxBus
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*

class SettingsActivity : AppCompatActivity() {

    val languages = arrayOf("English","Mexican","France")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        showDropDown()
    }

    private fun showDropDown() {
        spChooseLanguage.adapter =  ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)

        spChooseLanguage.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {

                changeLocale(languages[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun changeLocale(language: String) {
        Log.d("COmingHere","InsidechangeLocale $language")
        val locale =  Locale(language)
        Locale.setDefault(locale)
        val config =  Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }
}
