package com.unifor.bibliotech

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
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
            triggerHapticFeedback(this)
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
        val MIN_SCALE = 1.0f
        val MAX_SCALE = 2.0f
        val MAX_PROGRESS_INT = 100
        val MAX_PROGRESS = MAX_PROGRESS_INT.toFloat()
        val SCALE_RANGE = MAX_SCALE - MIN_SCALE
        val savedScale = loadFloat(context = this,KEY_TEXT_SCALE, defaultValue = MIN_SCALE)
        val progressCalculado = ((savedScale - MIN_SCALE) / SCALE_RANGE) * MAX_PROGRESS
        sbTextSize.progress = progressCalculado.toInt()

        sbTextSize.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val progressFloat = progress.toFloat()
                val newScale = MIN_SCALE + (progressFloat / MAX_PROGRESS * SCALE_RANGE)
                saveFloat(context = this@ActivityAcessibilidade, KEY_TEXT_SCALE, newScale)
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
        val sVibration: Switch = findViewById(R.id.switchVibration)

        sVibration.isChecked = loadBoolean(this,KEY_HAPTIC)

        // Listener para Feedback Tátil
        sVibration.setOnCheckedChangeListener { _, isChecked ->
            saveBoolean(this,KEY_HAPTIC, isChecked)

            // Opcional: dê um feedback tátil instantâneo ao ligar/desligar o switch
            if (isChecked) {
                triggerHapticFeedback(this)
            }
        }
    }

    override fun onPause() { super.onPause() }
    override fun onResume() { super.onResume() }
    override fun onStop() { super.onStop() }
    override fun onRestart() { super.onRestart() }
}