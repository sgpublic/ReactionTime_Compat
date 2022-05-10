package io.github.sgpublic.reactiontime.core.util

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import io.github.sgpublic.reactiontime.Application

fun Int.getDrawable(theme: Resources.Theme? = null): Drawable {
    return ResourcesCompat.getDrawable(Application.getResources(), this, theme)
        ?: throw IllegalStateException("resource not found")
}


fun Int.getString(vararg args: Any): String {
    return Application.getString(this, *args)
}