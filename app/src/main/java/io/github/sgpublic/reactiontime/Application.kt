package io.github.sgpublic.reactiontime

import android.app.Application
import android.content.res.Resources
import androidx.annotation.StringRes

class Application: Application() {
    override fun onCreate() {
        application = this
        super.onCreate()
    }

    companion object {
        private lateinit var application: Application

        fun getString(@StringRes textId: Int, vararg arg: Any): String {
            return application.getString(textId, *arg)
        }

        fun getResources(): Resources {
            return application.resources
        }
    }
}