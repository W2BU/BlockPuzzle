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
    private var gameWorld = GameWorld(getContext())
    private var gameThread = GameThread(this)

    init {
        holder.addCallback(this)
        setOnTouchListener(gameWorld)
        gameThread.start()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        gameWorld.render(canvas)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        gameWorld.set(width, height)
    }


    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

}