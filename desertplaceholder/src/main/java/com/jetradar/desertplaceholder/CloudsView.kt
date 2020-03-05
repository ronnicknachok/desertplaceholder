/*
 * Copyright (C) 2016 JetRadar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jetradar.desertplaceholder

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*

class CloudsView : View {
    private val clouds: MutableList<Cloud> = ArrayList()
    private var paint: Paint? = null
    private var density = 0f
    private var timeStamp = -1.0

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        paint = Paint()
        density = context.resources.displayMetrics.density
    }

    override fun onDraw(canvas: Canvas) {
        val time = System.currentTimeMillis()
        if (timeStamp != -1.0) {
            drawClouds(canvas, time)
        } else {
            initClouds()
        }
        timeStamp = time.toDouble()
        if (DesertPlaceholder.animationEnabled) {
            invalidate()
        }
    }

    private fun initClouds() {
        val width = width
        val height = height
        val res = context.resources
        val cloudTop = BitmapFactory.decodeResource(res, R.drawable.cloud3)
        val cloudMiddle = BitmapFactory.decodeResource(res, R.drawable.cloud2)
        val cloudBottom = BitmapFactory.decodeResource(res, R.drawable.cloud1)
        clouds.add(Cloud(cloudTop, 0.3f, 0))
        clouds.add(Cloud(cloudMiddle, 0.6f, height / 2 - cloudMiddle.height / 2))
        clouds.add(Cloud(cloudBottom, 0.8f, height - cloudBottom.height))
        var percent = 0.1f
        for (cloud in clouds) {
            cloud.x = width * percent
            percent += 0.25f
        }
    }

    private fun drawClouds(canvas: Canvas, time: Long) {
        for (cloud in clouds) {
            updatePosition(cloud, (time - timeStamp) / 1000.0)
            canvas.drawBitmap(cloud.bitmap, cloud.x, cloud.y.toFloat(), paint)
        }
    }

    private fun updatePosition(cloud: Cloud, timeDelta: Double) {
        cloud.x += density * SPEED_DP_PER_SEC * cloud.speedMultiplier * timeDelta.toFloat()
        val width = width
        if (cloud.x > width) {
            cloud.x = -cloud.bitmap.width.toFloat()
        }
    }

    private class Cloud internal constructor(val bitmap: Bitmap, val speedMultiplier: Float, val y: Int) {
        var x = 0f

    }

    companion object {
        private const val SPEED_DP_PER_SEC = 20f
    }
}