package io.github.sgpublic.reactiontime.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import io.github.sgpublic.reactiontime.R
import io.github.sgpublic.reactiontime.base.BaseActivity
import io.github.sgpublic.reactiontime.core.util.getDrawable
import io.github.sgpublic.reactiontime.core.util.getString
import io.github.sgpublic.reactiontime.databinding.ActivityMainBinding
import io.github.sgpublic.reactiontime.ui.ReactionGestureDetector
import io.github.sgpublic.reactiontime.viewmodule.MainViewModule

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModule>() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {

    }

    override fun onViewModelSetup() {
        ViewModel.TOUCH_STATUS.observe(this) {
            when (it) {
                MainViewModule.STATUS_WAIT -> {
                    ViewBinding.mainTouch.text = R.string.text_down.getString()
                    ViewBinding.mainTouch.background = R.drawable.btn_select.getDrawable()
                }
                MainViewModule.STATUS_PREPARE -> {
                    ViewBinding.mainTouch.text = R.string.text_prepare.getString()
                    ViewBinding.mainTouch.background = R.drawable.btn_select.getDrawable()
                }
                MainViewModule.STATUS_UP -> {
                    ViewBinding.mainTouch.text = R.string.text_up.getString()
                    ViewBinding.mainTouch.background = R.drawable.btn_set.getDrawable()
                }
                MainViewModule.STATUS_CHEAT -> {
                    ViewBinding.mainTouch.text = R.string.text_down.getString()
                    ViewBinding.mainTouch.background = R.drawable.btn_select.getDrawable()
                }
            }
        }
        ViewModel.BEST_REACTION_NOTE.observe(this) {
            ViewBinding.mainBestNote.text = R.string.text_best_note.getString(
                if (it < 0) {
                    R.string.text_reaction_time_empty.getString()
                } else {
                    R.string.text_reaction_time.getString(it)
                }
            )
        }
        ViewModel.REACTION_COUNT.observe(this) {
            ViewBinding.mainTimesCount.text = R.string.text_times_count.getString(it)
        }
        ViewModel.REACTION_TIME.observe(this) {
            ViewBinding.mainReactionTime.text =
                R.string.title_reaction_time.getString(
                    when {
                        it < 0.0 -> {
                            R.string.text_reaction_time_cheat.getString()
                        }
                        it == 0.0 -> {
                            R.string.text_reaction_time_empty.getString()
                        }
                        else -> {
                            R.string.text_reaction_time.getString(it)
                        }
                    }
                )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewSetup() {
        ViewBinding.mainTouch.setOnTouchListener(
            ReactionGestureDetector(ViewModel)
        )
        ViewBinding.mainReset.setOnClickListener {
            ViewModel.reset()
        }
    }

    override fun onCreateViewBinding() = ActivityMainBinding.inflate(layoutInflater)
    override val ViewModel: MainViewModule by viewModels()
}