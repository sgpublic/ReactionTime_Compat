package io.github.sgpublic.reactiontime.viewmodule

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.sgpublic.reactiontime.ui.ReactionGestureDetector
import kotlin.random.Random

class MainViewModule: ViewModel(), ReactionGestureDetector.ReactionGesture {
    val TOUCH_STATUS: MutableLiveData<Int> = MutableLiveData(STATUS_WAIT)
    val BEST_REACTION_NOTE: MutableLiveData<Double> = MutableLiveData(-1.0)
    val REACTION_COUNT: MutableLiveData<Int> = MutableLiveData(0)
    val REACTION_TIME: MutableLiveData<Double> = MutableLiveData(0.0)

    private val handler: Handler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val random: Random = Random.Default
    private val reaction: Runnable = Runnable {
        Log.d("MainViewModule", "reaction")
        TOUCH_STATUS.postValue(STATUS_UP)
        startTimer()
    }

    override fun onDown() {
        Log.d("MainViewModule", "onDown()")
        TOUCH_STATUS.postValue(STATUS_PREPARE)
        handler.removeCallbacks(reaction)
        handler.postDelayed(reaction, random.nextLong(1000, 4000))
    }

    override fun onUp() {
        Log.d("MainViewModule", "onUp()")
        TOUCH_STATUS.postValue(STATUS_WAIT)
        stopTimer()
    }

    fun reset() {
        BEST_REACTION_NOTE.postValue(REACTION_DEFAULT)
        REACTION_COUNT.postValue(0)
        REACTION_TIME.postValue(0.0)
    }

    private var timer: Long = -1
    fun startTimer() {
        timer = System.currentTimeMillis()
    }
    fun stopTimer() {
        handler.removeCallbacks(reaction)
        if (timer < 0) {
            TOUCH_STATUS.postValue(STATUS_CHEAT)
            REACTION_TIME.postValue(-1.0)
            return
        }
        val time: Double = (System.currentTimeMillis().toDouble() - timer) / 1000
        REACTION_TIME.postValue(time)
        timer = -1
        val currentBest: Double = BEST_REACTION_NOTE.value ?: -1.0
        if (currentBest > time || currentBest == -1.0) {
            BEST_REACTION_NOTE.postValue(time)
        }
        REACTION_COUNT.postValue((REACTION_COUNT.value ?: 0) + 1)
    }

    companion object {
        const val STATUS_WAIT: Int = 0
        const val STATUS_PREPARE: Int = 1
        const val STATUS_UP: Int = 2
        const val STATUS_CHEAT: Int = 3

        const val REACTION_DEFAULT = -1.0
    }
}