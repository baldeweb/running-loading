package com.wallace.running_loading

import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.wallace.running_loading.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.setContentView(binding.root)

        executeAnimation()
    }

    private fun executeAnimation() {
        showLoading()
        Handler(Looper.getMainLooper()).postDelayed({
            dismissLoading()
        }, 7000)
    }

    private fun makeAnimationX(duration: Long, view: View) {
        val animation = TranslateAnimation(
            Animation.ABSOLUTE, 1 - getWidthSizeScreen(),
            Animation.ABSOLUTE, 1 + getWidthSizeScreen(),
            Animation.ABSOLUTE, 0F,
            Animation.ABSOLUTE, 0F
        )
        animation.duration = duration
        animation.fillAfter = true
        animation.startOffset = 250
        animation.repeatCount = Animation.INFINITE
        animation.repeatMode = Animation.INFINITE

        view.startAnimation(animation)
    }

    fun makeAnimationY(view: View, isReverse: Boolean) {
        val animation = TranslateAnimation(
            Animation.ABSOLUTE, 0F,
            Animation.ABSOLUTE, 0F,
            Animation.ABSOLUTE, if (isReverse) 0F else -250F,
            Animation.ABSOLUTE, if (isReverse) -250F else 0F
        )
        animation.duration = 150
        animation.fillAfter = true
        animation.repeatMode = Animation.ABSOLUTE

        view.startAnimation(animation)
    }

    private fun animateLoading() {
        makeAnimationX(800, binding.vewBaseLoadingFirst)
        makeAnimationX(1400, binding.vewBaseLoadingSecond)
        makeAnimationX(1200, binding.vewBaseLoadingThird)
    }

    private fun showLoading() {
        if (binding.lltBaseLoading.visibility == View.GONE) {
            binding.lltBaseLoading.visibility = View.VISIBLE
            animateLoading()
            binding.lltBaseLoading.setOnClickListener(null)
            binding.lltBaseLoading.setOnTouchListener { _, _ -> return@setOnTouchListener false }
        } else {
            return
        }
    }

    private fun dismissLoading() {
        if (binding.lltBaseLoading.visibility == View.VISIBLE)
            binding.lltBaseLoading.visibility = View.GONE
        else
            return
    }

    fun getWidthSizeScreen(): Float {
        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        return size.x.toFloat()
    }
}