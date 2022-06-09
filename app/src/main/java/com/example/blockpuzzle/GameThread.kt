package com.example.blockpuzzle

import android.graphics.Canvas

class GameThread(private val gameSurfaceView: GameSurfaceView): Thread() {
    override fun run() {
        val fps = 30
        val period = (2 * 1000000).toLong()
        val periodDraw = ((1000 / fps) * 1000000).toLong()
        var previousTimeDraw = System.nanoTime()
        var previousTimeUpdate = System.currentTimeMillis()
        var startTime = System.nanoTime()

        while (true) {
            var canvas: Canvas? = null;
            val now = System.nanoTime()
            this.gameSurfaceView.update(System.currentTimeMillis() - previousTimeUpdate)
            previousTimeUpdate = System.currentTimeMillis()
            if (now - previousTimeDraw >= periodDraw) {
                previousTimeDraw = now
                if (gameSurfaceView.holder != null) {
                    try {
                        canvas = gameSurfaceView.holder.lockCanvas()
                        synchronized(canvas) { this.gameSurfaceView.draw(canvas) }
                    } catch (e: java.lang.Exception) {
                    } finally {
                        if (canvas != null) {
                            this.gameSurfaceView.holder.unlockCanvasAndPost(canvas)
                        }
                    }
                }
            }
            var waitTime: Long = period - (now - startTime)
            if (waitTime < period) {
                waitTime = period // Millisecond.
            }
            try {
                sleep(waitTime / 1000000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            startTime = System.nanoTime()
        }
    }
}