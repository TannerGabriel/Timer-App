package com.example.gabriel.timerapp.ui

import android.os.Bundle
import android.preference.PreferenceActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.gabriel.timerapp.R

class SettingsActivity : PreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference)
    }


}
