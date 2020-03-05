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
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView

class DesertPlaceholder : FrameLayout {
    private var button: TextView? = null
    private var message: TextView? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.placeholder, this, true)
        message = findViewById<View>(R.id.placeholder_message) as TextView
        button = findViewById<View>(R.id.placeholder_button) as TextView
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.DesertPlaceholder)
        try {
            val messageText = attributes.getString(R.styleable.DesertPlaceholder_dp_message)
            val buttonText = attributes.getString(R.styleable.DesertPlaceholder_dp_buttonText)
            setMessage(messageText)
            setButtonText(buttonText)
        } finally {
            attributes.recycle()
        }
        setBackgroundColor(resources.getColor(R.color.background_desert))
    }

    fun setOnButtonClickListener(clickListener: OnClickListener?) {
        button?.setOnClickListener(clickListener)
    }

    fun setMessage(msg: String?) {
        message?.text = msg
    }

    fun setButtonText(action: String?) {
        if (TextUtils.isEmpty(action)) {
            button?.visibility = View.GONE
        } else {
            button?.text = action
            button?.visibility = View.VISIBLE
        }
    }

    companion object {
        @JvmField
        var animationEnabled = true
    }
}