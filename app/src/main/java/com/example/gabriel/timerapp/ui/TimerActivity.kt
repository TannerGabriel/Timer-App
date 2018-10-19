package com.example.gabriel.timerapp.ui


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.gabriel.timerapp.R
import com.example.gabriel.timerapp.contants.TimerState
import com.example.gabriel.timerapp.contants.WorkState
import com.example.gabriel.timerapp.utilities.PrefUtil
import kotlinx.android.synthetic.main.activity_timer.*
import android.view.ContextMenu.ContextMenuInfo


class TimerActivity : AppCompatActivity() {




    lateinit var timerState: TimerState
    lateinit var workState: WorkState
    var timeLeft: Long = 0

    lateinit var viewModel: TimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)


        setSupportActionBar(my_toolbar)


        getSupportActionBar()!!.setDisplayShowTitleEnabled(false)



        val factory = TimerViewModelProvider(this)

        viewModel = ViewModelProviders.of(this, factory)
                .get(TimerViewModel::class.java)




        viewModel.getTimer().observe(this, Observer {
            if(it != null){
                var minutes = it / 60
                var seconds = it % 60

                if(seconds < 10){
                    time_left_textview.text = "$minutes : 0$seconds"
                }else{
                    time_left_textview.text = "$minutes : $seconds"
                }
                timeLeft = it!!
                PrefUtil.setSecondsRemaining(timeLeft, this)



                if(timerState == TimerState.Stopped){
                    time_left_textview.text = "$minutes : 0$seconds"
                }
            }




        })

        viewModel.getTimerState().observe(this, Observer {
            timerState = it!!
            updateButton()
        })

        viewModel.getWorkState().observe(this, Observer {
            workState = it!!
            updateTextView()
        })

        start_timer_textview.setOnClickListener {
            viewModel.startTimer()
        }

        stop_timer_textview.setOnClickListener {
            viewModel.stopTimer()
        }

        pause_timer_textview.setOnClickListener {
            viewModel.pauseTimer()
        }

        stop_session_timer_textview.setOnClickListener {
            viewModel.stopSession()
        }



        viewModel.initTimer()


    }

    override fun onStart() {
        super.onStart()

        viewModel.setNewTimerLength()
        initUi()

    }


    private fun initUi() {
        var time = viewModel.getTimerLength()

        var minutes = time / 60
        var seconds = time % 60

        time_left_textview.text = "$minutes : 0$seconds"

    }

    private fun updateTextView() {
        when (workState) {
            WorkState.Working -> {
                information_textview.visibility = View.VISIBLE
                information_textview.text = "Work"
            }
            WorkState.Resting -> {
                information_textview.visibility = View.VISIBLE
                information_textview.text = "Rest"
            }

            WorkState.Stopped ->{
                information_textview.visibility = View.INVISIBLE
            }
        }
    }

    fun updateButton() {
        if (timerState != null) {
            when (timerState) {
                TimerState.Running -> {
                    start_timer_textview.visibility = View.INVISIBLE
                    stop_timer_textview.visibility = View.VISIBLE
                    stop_session_timer_textview.visibility = View.VISIBLE
                    pause_timer_textview.visibility = View.VISIBLE
                    if(workState == WorkState.Working){
                        background.setImageDrawable(getDrawable(R.drawable.start_background))
                    }else if(workState == WorkState.Resting){
                        background.setImageDrawable(getDrawable(R.drawable.rest_background))
                    }
                }
                TimerState.Stopped -> {
                    start_timer_textview.visibility = View.VISIBLE
                    stop_timer_textview.visibility = View.INVISIBLE
                    stop_session_timer_textview.visibility = View.INVISIBLE
                    pause_timer_textview.visibility = View.INVISIBLE

                    if(workState == WorkState.Working){
                        background.setImageDrawable(getDrawable(R.drawable.start_background))
                    }else if(workState == WorkState.Resting){
                        background.setImageDrawable(getDrawable(R.drawable.rest_background))
                    }else if(workState == WorkState.Stopped){
                        background.setImageDrawable(getDrawable(R.drawable.main_background))
                    }
                }
                TimerState.Paused -> {
                    start_timer_textview.visibility = View.VISIBLE
                    stop_timer_textview.visibility = View.VISIBLE
                    stop_session_timer_textview.visibility = View.VISIBLE
                    pause_timer_textview.visibility = View.INVISIBLE

                    if(workState == WorkState.Working){
                        background.setImageDrawable(getDrawable(R.drawable.start_background))
                    }else if(workState == WorkState.Resting){
                        background.setImageDrawable(getDrawable(R.drawable.rest_background))
                    }
                }
            }
        } else {
            start_timer_textview.visibility = View.VISIBLE
            stop_timer_textview.visibility = View.INVISIBLE
            pause_timer_textview.visibility = View.INVISIBLE
        }

    }


    override fun onPause() {
        super.onPause()

        viewModel.onPause()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item!!.itemId) {
        R.id.settings -> {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }

    }
}
