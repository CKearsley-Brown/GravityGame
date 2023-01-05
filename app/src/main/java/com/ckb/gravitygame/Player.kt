package com.ckb.gravitygame

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.media.MediaPlayer

class Player(x: Int, y: Int, width: Int, height: Int, image: Drawable, rect: Rect) : MyGameObject(x, y, width, height, image, rect) {
    var px : Int = 500
    var health : Int = 3

    override fun move(canvas : Canvas) {
        if (px > x)
            x += 50
        else if (px < x)
            x -= 50
        image.setBounds(x, y, x + width, y + height)
        image.draw(canvas)
    }

    fun collision(o : MyGameObject, surfaceView: MySurfaceView) {
        if(rect.intersect(o.rect))
        {
            health-= 1
            //y=500

            //This ultimately deletes the game object as the apple will be unreferenced
            o.y = 2500

            //Sound
            surfaceView.scream()
        }
    }
}