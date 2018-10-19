package com.example.gabriel.timerapp.utilities

import android.content.Context
import android.preference.PreferenceManager
import com.example.gabriel.timerapp.contants.TimerState
import com.example.gabriel.timerapp.contants.WorkState

class PrefUtil {

    companion object {

        private const val TIMER_LENGTH_ID = "com.android.example.timer.timer_length"
        fun getTimerLength(context: Context): String{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(TIMER_LENGTH_ID, "25")
        }

        private const val TIMER_BREAK_LENGTH_ID = "com.android.example.timer.timer_break_length"
        fun getTimerBreakLength(context: Context): String{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(TIMER_BREAK_LENGTH_ID, "5")
        }


        private const val PREVIOUS_TIMER_LENGTH_SECONDS_ID = "com.example.android.timer.previous_timer_length_seconds"

        fun getPreviousTimerLengthSeconds(context: Context): Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, 0)
        }

        fun setPreviousTimerLengthSeconds(seconds: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, seconds)
            editor.apply()
        }

        private const val TIMER_STATE_ID = "com.example.android.timer.timer_state"

        fun getTimerState(context: Context): TimerState {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val ord = preferences.getInt(TIMER_STATE_ID,0)
            return TimerState.values()[ord]
        }

        fun setTimerState(state: TimerState, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            val ord = state.ordinal
            editor.putInt(TIMER_STATE_ID, ord)
            editor.apply()
        }

        private const val TIMER_WORK_STATE_ID = "com.example.android.timer.timer_work_state"

        fun getTimerWorkState(context: Context): WorkState {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val ord = preferences.getInt(TIMER_WORK_STATE_ID,0)
            return WorkState.values()[ord]
        }

        fun setTimerWorkState(state: WorkState, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            val ord = state.ordinal
            editor.putInt(TIMER_WORK_STATE_ID, ord)
            editor.apply()
        }


        private const val SECONDS_REMAINING_ID = "com.example.android.timer.seconds_remaining"

        fun getSecondsRemaining(context: Context): Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(SECONDS_REMAINING_ID, 0)
        }

        fun setSecondsRemaining(seconds: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(SECONDS_REMAINING_ID, seconds)
            editor.apply()
        }

        private const val ALARM_SET_TIME_ID = "com.example.android.timer.background_time"

        fun getAlarmSetTime(context: Context): Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(ALARM_SET_TIME_ID, 0)
        }

        fun setAlarmSetTime(time: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(ALARM_SET_TIME_ID, time)
            editor.apply()
        }
    }
}