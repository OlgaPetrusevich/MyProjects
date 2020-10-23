package com.gmail.petrusevich.volha.fitnessapp.timer

import android.os.Handler


class TimerController(private val handler: Handler) {

    fun getTimerTime() {
        val thread = Thread(Runnable {
            var time = 59
            while (time >= 0) {
                Thread.sleep(1000)
                handler.sendMessage(handler.obtainMessage(1, time))
                time--
            }
        })
        thread.start()
    }

}