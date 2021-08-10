package com.example.unittest.util

import android.annotation.TargetApi
import android.os.Build
import android.util.Log
import android.view.Choreographer
import kotlin.math.pow

/**
 * @author shouxianli on 2021/7/29.
 */
object FpsUtil {
    private const val TAG = "FpsUtil"

    var isStarted = false

    private var startTime = 0L
    private var lastTime = 0L
    private var maxCostTime = 0L
    private var frameCounts = 0

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private val frameCallback = object : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            frameCounts += 1
            if (startTime == 0L) {
                startTime = frameTimeNanos
            }

            if (lastTime != 0L) {
                val costTime = frameTimeNanos - lastTime
                if (costTime > maxCostTime) {
                    maxCostTime = costTime
                }
            }
            lastTime = frameTimeNanos

            Choreographer.getInstance().postFrameCallback(this)
        }
    }

    private fun reset() {
        startTime = 0
        lastTime = 0
        maxCostTime = 0
        frameCounts = 0
    }

    fun recordFps(start: Boolean) {
        if (start == isStarted) {
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (start) {
                reset()
                Choreographer.getInstance().postFrameCallback(frameCallback)
                Log.d(TAG, "recordFps begin")
            } else {
                Choreographer.getInstance().removeFrameCallback(frameCallback)

                val totalTime = System.nanoTime() - startTime

                val ns2s = 10.toDouble().pow(9)
                val ns2ms = 10.toDouble().pow(6)
                val fps = frameCounts * ns2s / totalTime
                Log.d(TAG, "recordFps end. maxCostTime=${maxCostTime / ns2ms}, fps=$fps")
            }
            isStarted = start
        }
    }
}