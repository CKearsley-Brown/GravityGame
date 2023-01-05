package com.ckb.gravitygame

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable

class Apple(x: Int, y: Int, width: Int, height: Int, image: Drawable, rect: Rect) : MyGameObject(x, y, width, height, image, rect) {

    override fun move(canvas: Canvas) {
        y+=10
        image.setBounds(x, y, x + width, y + height)
        image.draw(canvas)
    }
}