package com.danielChoi.uselessmachine

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.min

//    MainActivity EXTENDS AppCompatActivity
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // the one function that View.OnClickListener has is onClick(v: View)
        // this lambda below is implementing that one function onClick without really mentioning it explicitly
        // the one parameter is referenced by "it". So to access that view, I can use "it"

        // View.OnClickListener
        // lambda -> anonymous function
        // you can use a lambda to implement a one function interface
        // onClick(view: View) is the function being implemented by the lambda
        // when there's one parameter in the function, "it" is used to refer to that parameter

        button_main_lookBusy.setOnClickListener {
            Toast.makeText(
                this,
                "Hello, this is the text on the button ${(it as Button).text.toString()}",
                Toast.LENGTH_SHORT
            ).show()
        }

        // to listen to a switch, you can use the OnCheckChangedListener
        switch_main_useless.setOnCheckedChangeListener { compoundButton, isChecked ->
            // 1. toast the status of the button (checked or not checked)
            val message = if(isChecked) "Switch is ON" else "Switch is OFF"
            Toast.makeText( this, message, Toast.LENGTH_SHORT).show()

            // String template ${isChecked.toString()}
            //          used for concatenation

            // 2. if the button is checked, uncheck it
            if (isChecked) {
                // ideally, we wait a little bit of time, and then have this code execute
                // but Thread.sleep is illegal on the main UI thread
                // janky app -- when it doesn't really respond to your clicks and taps immediately
                // CountDownTimer is effectively making a separate thread to keep track of the time
                // make an anonymous inner class using CountDownTimer
                // not naming the class... just implementing the methods
                //      -> one time use thing used here

                // making anonymous inner class saying this object extends CountDownTimer
                val maxTime = 10000L
                val minTime = 2000L
                val timeUsed = (Math.random() * (maxTime - minTime) + minTime).toLong()

                val uncheckTimer = object : CountDownTimer(timeUsed, 1000) {

                    private var isRed = false
                    private var difference = 1000L // L makes it a Long data type instead of Int

                    override fun onFinish() {
                        switch_main_useless.isChecked = false
                        layout_main.setBackgroundColor(Color.rgb((0..255).random(), (0..255).random(), (0..255).random()))
                    }

                    override fun onTick(millisRemaining: Long) {
                        // can have an empty function if you don't need to use it
                        if(!switch_main_useless.isChecked) {
                            cancel()
                            onFinish()
                        }
                    }

                }
                uncheckTimer.start()
            }



        }

        button_main_selfDestruct.setOnClickListener {
            var countdown = 10
            button_main_selfDestruct.setEnabled(false)
            val timer = object : CountDownTimer(12000, 1000) {
                private var timeToFlash = 10000L
                private var timeUntilFlash = 3000L



                override fun onFinish() {
                    layout_main.setBackgroundColor(Color.rgb(255, 255, 255))
                    button_main_selfDestruct.setEnabled(true)
                    button_main_selfDestruct.setText("Self Destruct")
                    countdown = 10
                }

                override fun onTick(millisRemaining: Long) {
                    var red = Color.rgb((255), (0), (0))
                    var white = Color.rgb(255, 255, 255)

                    var millis = (millisRemaining.toInt() + 99) / 1000

                    if(millisRemaining >= 6000L) {
                        if (millis == timeToFlash.toInt() / 1000) {
                            layout_main.setBackgroundColor(white)
                            timeToFlash -= timeUntilFlash
                        } else {
                            layout_main.setBackgroundColor(red)
                        }
                        /*
                        if(millis == timeToFlash.toInt()/1000) {
                            layout_main.setBackgroundColor(red)
                            timeToFlash -= timeUntilFlash
                        }
                        else if(millis == 11 && millis == 8)
                        {
                            layout_main.setBackgroundColor(red)
                        }

                        else{
                            layout_main.setBackgroundColor(white)
                            }

                         */

                    }

                    else{
                        if(millis %2 == 0) {
                            layout_main.setBackgroundColor(red)
                        }
                        else{
                            layout_main.setBackgroundColor(white)
                        }
                    }
                    button_main_selfDestruct.setText(countdown.toString())
                    countdown--
                }

            }
            timer.start()




        }
    }


        
}
