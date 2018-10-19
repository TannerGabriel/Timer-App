package com.example.gabriel.timerapp.ui

import android.content.Context
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gabriel.timerapp.contants.TimerState
import com.example.gabriel.timerapp.contants.WorkState
import com.example.gabriel.timerapp.utilities.NotificationUtil
import com.example.gabriel.timerapp.utilities.PrefUtil

class TimerViewModel(context: Context): ViewModel(){

    private val context = context

    private var stopSession: Boolean = false
    private var pomodoro: Boolean = true
    private var timerLengthSeconds = 60L
    private var breakLenghtSeconds = 20L
    private var timerState = MutableLiveData<TimerState>()
    private var workState = MutableLiveData<WorkState>()

    private var secondsRemaining = MutableLiveData<Long>()

    lateinit var timer: CountDownTimer




    fun startTimer(){

        if(workState.value == WorkState.Stopped || workState.value == null){
            workState.value = WorkState.Working
        }

        timerState.value = TimerState.Running

        var secondsLeft: Long

        if(secondsRemaining.value != null && secondsRemaining.value!!.toLong() > 0){
            secondsLeft = secondsRemaining.value!!.toLong()
        }else{
            if(workState == WorkState.Working){
                secondsLeft = timerLengthSeconds
            }else if(workState == WorkState.Resting){
                secondsLeft = breakLenghtSeconds
            }else{
                secondsLeft = timerLengthSeconds
            }
        }

        timer = object: CountDownTimer(secondsLeft*1000, 1000){
            override fun onFinish() {
                NotificationUtil.showNotification(context)
                onTimerFinish()
            }

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining.value = millisUntilFinished / 1000
            }

        }.start()
    }


    fun stopTimer(){
        timerState.value = TimerState.Stopped

        onTimerFinish()
    }

    fun pauseTimer(){
        timerState.value = TimerState.Paused

        timer.cancel()
    }

    fun stopSession(){
        timerState.value = TimerState.Stopped

        stopSession = true
        onTimerFinish()
    }

    fun initTimer(){
        val timerLength = PrefUtil.getTimerLength(context)
        val timerBreakLength = PrefUtil.getTimerBreakLength(context)
        timerLengthSeconds = (timerLength.toLong()*60L)
        breakLenghtSeconds = (timerBreakLength.toLong()*60L)
    }

    fun getTimer()= secondsRemaining

    fun getTimerState() = timerState

    fun getWorkState() = workState

    fun getTimerLength() = timerLengthSeconds

    private fun onTimerFinish() {
        setNewTimerLength()

        if(workState.value == WorkState.Working && !stopSession){
            workState.value = WorkState.Resting
            secondsRemaining.value = breakLenghtSeconds
        }else if(workState.value == WorkState.Resting && !stopSession){
            workState.value = WorkState.Working
            secondsRemaining.value = timerLengthSeconds
        }else if(workState.value == WorkState.Working){
            workState.value = WorkState.Stopped
            secondsRemaining.value = timerLengthSeconds
        }else{
            workState.value = WorkState.Stopped
            secondsRemaining.value = timerLengthSeconds
        }

        stopSession = false
        timerState.value = TimerState.Stopped
        timer.cancel()
    }


    fun setNewTimerLength() {
        Toast.makeText(context, "Setting new time", Toast.LENGTH_SHORT).show()
        val timerLength = PrefUtil.getTimerLength(context)
        val timerBreakLength = PrefUtil.getTimerBreakLength(context)
        timerLengthSeconds = (timerLength.toLong()*60L)
        breakLenghtSeconds = (timerBreakLength.toLong()*60L)
    }

    fun onPause(){
        //PrefUtil.setPreviousTimerLengthSeconds(timerLengthSeconds, this)
        //PrefUtil.setSecondsRemaining(secondsRemaining, this)
        //PrefUtil.setTimerState(timerState, this)
        //PrefUtil.setTimerState(workState, this)
    }



}