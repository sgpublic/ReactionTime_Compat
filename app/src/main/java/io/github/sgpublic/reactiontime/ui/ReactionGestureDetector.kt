package io.github.sgpublic.reactiontime.ui

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class ReactionGestureDetector(
    private val callback: ReactionGesture
): View.OnTouchListener, GestureDetector.SimpleOnGestureListener() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, ev: MotionEvent): Boolean {
        when(ev.action) {
            MotionEvent.ACTION_DOWN -> {
                callback.onDown()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                callback.onUp()
            }
        }
        return false
    }

    interface ReactionGesture {
        fun onDown()
        fun onUp()
    }
}