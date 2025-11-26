package com.unifor.bibliotech

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ActivityAcessibilidade : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_acessibilidade)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnvoltar: ImageButton = findViewById(R.id.btnVoltar)

        btnvoltar.setOnClickListener {
            finish()
        }

        val reduceAnimationsEnabled = loadBoolean(this,KEY_REDUCE_ANIM)

        if (!reduceAnimationsEnabled) {
            // Animação suave (duração longa, 500ms)
            btnvoltar.animate().translationX(100f).duration = 500
        } else {
            // Pula a animação ou usa uma transição instantânea
            btnvoltar.translationX = 100f
        }

        setupTextSizeSeekBar()

        setupContrastSwitches()

        setupAnimationSwitches()
    }

    private fun applyChangesAndRestart() {
        recreate()
    }

    private fun setupTextSizeSeekBar() {
        val sbTextSize: SeekBar = findViewById(R.id.seekBarTextSize)
        val savedScale = loadFloat(this,KEY_TEXT_SCALE, 1.0f)
        sbTextSize.progress = ((savedScale - 0.8f) / 0.7f * 100).toInt()

        sbTextSize.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val newScale = 0.8f + (progress / 100.0f) * 0.7f
                saveFloat(this@ActivityAcessibilidade, KEY_TEXT_SCALE, newScale)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                applyChangesAndRestart()
            }
        })
    }

    private fun setupContrastSwitches() {
        val sHighContrast: Switch = findViewById(R.id.switchHighContrast)

        sHighContrast.isChecked = loadBoolean(this,KEY_HIGH_CONTRAST)

        sHighContrast.setOnCheckedChangeListener { _, isChecked ->
            saveBoolean(this,KEY_HIGH_CONTRAST, isChecked)
            applyChangesAndRestart()
        }
    }

    private fun setupAnimationSwitches() {
        val sReduceAnimations: Switch = findViewById(R.id.switchReduceAnimations)
        val sVibration: Switch = findViewById(R.id.switchVibration)

        sReduceAnimations.isChecked = loadBoolean(this,KEY_REDUCE_ANIM)
        sVibration.isChecked = loadBoolean(this,KEY_HAPTIC)

        sReduceAnimations.setOnCheckedChangeListener { _, isChecked ->
            saveBoolean(this,KEY_REDUCE_ANIM, isChecked)
            // Nenhuma ação imediata na ActivityAcessibilidade, mas deve ser checado
            // antes de executar animações em todo o app.
        }

        // Listener para Feedback Tátil
        sVibration.setOnCheckedChangeListener { _, isChecked ->
            saveBoolean(this,KEY_HAPTIC, isChecked)

            // Opcional: dê um feedback tátil instantâneo ao ligar/desligar o switch
            if (isChecked) {
                triggerHapticFeedback(this)
            }
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

    override fun onPause() { super.onPause() }
    override fun onResume() { super.onResume() }
    override fun onStop() { super.onStop() }
    override fun onRestart() { super.onRestart() }
}