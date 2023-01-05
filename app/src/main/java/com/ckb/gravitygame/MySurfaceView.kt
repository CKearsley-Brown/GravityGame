package com.ckb.gravitygame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class MySurfaceView(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs), Runnable {
    var paint = Paint()
    val surfaceBackground = context!!.resources.getDrawable(R.drawable.gamebackground, null)
    var isRunning = true
    var myThread: Thread
    var myHolder: SurfaceHolder
    var myGameObjects = ArrayList<MyGameObject>()
    val playerPic = context!!.resources.getDrawable(R.drawable.player, null)
    val applePic = context!!.resources.getDrawable(R.drawable.apple, null)
    var healthBar = context!!.resources.getDrawable(R.drawable.healthbarfull, null)
    val player = Player(500,1500, playerPic.intrinsicWidth, playerPic.intrinsicHeight, playerPic, Rect(500, 1500, playerPic.intrinsicWidth, playerPic.intrinsicHeight))
    val listener = MutableLiveData<Int>()

    var spawnTime : Long = 2000
    val appleTimer = object : CountDownTimer(spawnTime, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            //No action
        }

        override fun onFinish() {
            myGameObjects.add(Apple(Random.nextInt(0, 1000), 0, applePic.intrinsicWidth, applePic.intrinsicHeight, applePic, Rect(0,0,0,0)))
            this.cancel()
            this.start()

            //Increasing the difficulty
            if (spawnTime > 1000)
            {
                spawnTime -= 25
            }
        }
    }

    init {
        paint.color = Color.WHITE
        myGameObjects.add(player)
        myThread = Thread(this)
        myThread.start()
        myHolder = holder
        appleTimer.start()
        listener.value = player.health
    }

    override fun run() {
        while (isRunning) {
            if(!myHolder.surface.isValid)
                continue

            val canvas : Canvas = myHolder.lockCanvas()
            // TODO: Make this perform in a smoother way
            // The Background
            surfaceBackground.setBounds(0,0,canvas.width, canvas.height)
            surfaceBackground.draw(canvas)
            //canvas.drawRect(0f,0f,canvas.width.toFloat(),canvas.height.toFloat(),paint)

            //Health Bar
            if(player.health == 2)
            {
                healthBar = context!!.resources.getDrawable(R.drawable.healthbarmedium, null)
                listener.postValue(player.health)
            } else if (player.health == 1)
            {
                healthBar = context!!.resources.getDrawable(R.drawable.healthbarlow, null)
                listener.postValue(player.health)
            }
            else if (player.health == 0) {
                listener.postValue(player.health)
                break
            }
            healthBar.setBounds(canvas.width-healthBar.intrinsicWidth,0,canvas.width, healthBar.intrinsicHeight)
            healthBar.draw(canvas)

            for (gameObject in myGameObjects)
            {
                gameObject.move(canvas)
                gameObject.setRect()
                if (gameObject is Apple)
                {
                    player.collision(gameObject, this)
                }
            }

            val iterator = myGameObjects.iterator()
            while (iterator.hasNext()){
                val item = iterator.next()
                if (item.y >= canvas.height-100)
                    iterator.remove()
            }

            myHolder.unlockCanvasAndPost(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //return super.onTouchEvent(event)
        player.px = roundMultiple(event!!.x.toInt()) - 50
        return true
    }

    fun roundMultiple(num : Int): Int {
        var roundedNum : Int = 50*(Math.round((num / 50).toDouble())).toInt()
        return roundedNum
    }

    fun scream() {
        val screamMP = MediaPlayer.create(context, R.raw.scream_male)
        screamMP.start()
    }

    fun stop() {
        isRunning = false
        while (true) {
            try {
                myThread.join()
                break
            } catch (e : InterruptedException) {
                e.printStackTrace()
            }
            break
        }
    }

    fun start() {
        isRunning = true
        myThread = Thread(this)
        myThread.start()
    }
}