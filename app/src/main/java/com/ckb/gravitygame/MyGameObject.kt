package com.ckb.gravitygame

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable

open class MyGameObject (var x:Int, var y:Int, var width:Int, var height: Int, var image:Drawable, var rect: Rect) {
    private var dx: Int = 100

    open fun move(canvas: Canvas)
    {
        x+=dx
        if (x>(canvas.width-width) || x<0)
        {
            dx = -dx
        }
        image.setBounds(x, y, x+width, y+height)
        image.draw(canvas)
    }

    open fun setRect()
    {
        rect = Rect(x,y,x+width,y+height)
    }
}