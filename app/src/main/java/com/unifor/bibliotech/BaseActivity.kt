package com.unifor.bibliotech

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.appcompat.app.AppCompatActivity

const val KEY_TEXT_SCALE = "pref_text_scale"
const val KEY_HIGH_CONTRAST = "pref_high_contrast"
const val KEY_REDUCE_ANIM = "pref_reduce_animations"
const val KEY_HAPTIC = "pref_haptic_feedback"

fun getPrefs(context: Context) = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
fun saveBoolean(context: Context, key: String, value: Boolean) {
    getPrefs(context).edit().putBoolean(key, value).apply()
}
fun loadBoolean(context: Context, key: String, defaultValue: Boolean = false): Boolean {
    return getPrefs(context).getBoolean(key, defaultValue)
}
fun saveFloat(context: Context, key: String, value: Float) {
    getPrefs(context).edit().putFloat(key, value).apply()
}
fun loadFloat(context: Context, key: String, defaultValue: Float = 1.0f): Float {
    return getPrefs(context).getFloat(key, defaultValue)
}

open class BaseActivity : AppCompatActivity() {
    private var currentFontScale: Float = 1.0f

    override fun attachBaseContext(newBase: Context) {
        val savedScale = loadFloat(newBase, KEY_TEXT_SCALE, 1.0f)
        currentFontScale = savedScale
        val configuration = newBase.resources.configuration
        configuration.fontScale = savedScale
        val context = ContextWrapper(newBase.createConfigurationContext(configuration))
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val isHighContrastEnabled = loadBoolean(this, KEY_HIGH_CONTRAST, false)

        if (isHighContrastEnabled) {
            setTheme(R.style.Theme_App_HighContrast)
        } else {
            // Se não, usa seu tema padrão.
            setTheme(R.style.Theme_BiblioTech) // Substitua pelo seu tema padrão (ex: R.style.Theme_Bibliotech)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        val savedScale = loadFloat(this, KEY_TEXT_SCALE, 1.0f)

        if (savedScale != currentFontScale) {
            recreate()
        }
    }
    fun triggerHapticFeedback(context: Context) {
        val hapticFeedbackEnabled = loadBoolean(this,KEY_HAPTIC)

        if (hapticFeedbackEnabled) {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            val pattern = longArrayOf(0, 50)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val effect = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
                vibrator.vibrate(effect)
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(pattern, -1)
            }
        }
    }
}