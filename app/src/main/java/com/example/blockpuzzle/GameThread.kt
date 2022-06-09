package com.example.blockpuzzle

class GameThread(private val gameSurfaceView: GameSurfaceView): Thread() {
    override fun run() {
        val fps = 25
        val frameTime = (1000 / fps) * 10000000L
        var startTime = System.nanoTime()
        var deltaTime = 0L
        var sleepTime = 0L
        while (true) {
            gameSurfaceView.tryDraw(gameSurfaceView.holder)
            deltaTime = System.nanoTime() - startTime
            sleepTime = (frameTime - deltaTime) / 1000000L
            if (sleepTime > 0) {
                try {
                    sleep(sleepTime)
                } catch (e: Exception) {}
            }
            startTime = System.nanoTime()
        }
    }
}