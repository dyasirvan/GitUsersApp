package com.android.gitusers.ui.setting

import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.android.gitusers.MainActivity
import com.android.gitusers.R
import com.android.gitusers.notification.DailyNotification
import com.android.gitusers.utils.Constants.Companion.KEY_SETTING_DAILY_REMINDER
import com.android.gitusers.utils.Constants.Companion.KEY_SETTING_LANGUAGE

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var spDaily : SwitchPreference
    private lateinit var dailyNotification: DailyNotification
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val changeLanguage: Preference = findPreference(KEY_SETTING_LANGUAGE)!!
        changeLanguage.setOnPreferenceClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            true
        }

        spDaily = findPreference(KEY_SETTING_DAILY_REMINDER)!!
        val sharedPreferences: SharedPreferences = preferenceManager.sharedPreferences
        spDaily.isChecked = sharedPreferences.getBoolean(KEY_SETTING_DAILY_REMINDER, false)

        dailyNotification = DailyNotification()

//        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        spDaily.setOnPreferenceClickListener {
           if(spDaily.isChecked){
                spDaily.isChecked = sharedPreferences.getBoolean(KEY_SETTING_DAILY_REMINDER, true)
            }else{
                spDaily.isChecked = sharedPreferences.getBoolean(KEY_SETTING_DAILY_REMINDER, false)
            }
            true
        }

    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if(key.equals(KEY_SETTING_DAILY_REMINDER)){
            spDaily.isChecked = sharedPreferences?.getBoolean(KEY_SETTING_DAILY_REMINDER, false)!!
            val setDaily = sharedPreferences.getBoolean(KEY_SETTING_DAILY_REMINDER, false)
            if(setDaily){
                dailyNotification.setDailyReminder(context, "09:00")
                Toast.makeText(context, getString(R.string.set_daily_reminder), Toast.LENGTH_SHORT).show()
                Log.d("SettingsFragment", "onSharedPreferenceChanged: checked")
            }else{
                context?.let { dailyNotification.cancelAlarm(it) }
                Toast.makeText(context, getString(R.string.unset_daily_reminder), Toast.LENGTH_SHORT).show()
                Log.d("SettingsFragment", "onSharedPreferenceChanged: unchecked")
            }
        }

        if(key.equals(KEY_SETTING_LANGUAGE)){
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
    }

}

