package com.example.blockpuzzle

import android.content.Context
import android.graphics.Canvas
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameSurfaceView(
    context: Context,
) : SurfaceView(context),
    SurfaceHolder.Callback
{
    private val gameWorld = GameWorld(getContext())
    private val gameThread = GameThread(this)

    init {
        holder.addCallback(this)
        setOnTouchListener(gameWorld)
        gameThread.start()
    }

    fun tryDraw(holder: SurfaceHolder) {
        val canvas: Canvas? = holder.lockCanvas()
        gameWorld.update()
        if (canvas != null) {
            draw(canvas)
        }
        holder.unlockCanvasAndPost(canvas)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        gameWorld.render(canvas)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        gameWorld.set(width, height)
    }

    fun update() {
        gameWorld.update()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

}